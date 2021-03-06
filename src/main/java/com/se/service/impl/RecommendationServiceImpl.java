package com.se.service.impl;

import com.alibaba.fastjson.JSON;
import com.se.model.UserLocation;
import com.se.exception.ResourceNotFoundException;
import com.se.repository.LocationRepository;
import com.se.repository.ScheduleRepository;
import com.se.repository.UserProfileRepository;
import com.se.service.RecommendationService;
import com.se.model.UserProfile;
import com.se.util.ConstValue;
import com.se.util.DistanceHelper;
import com.se.util.TimeStrHelper;
import com.se.vo.IntendVO;
import com.se.vo.RealTimeLocationVO;
import com.se.vo.UserBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private LocationRepository locationRepository;

    // in-mem cache
    private Map<Integer, UserBriefVO> cache;


    public RecommendationServiceImpl() {
        this.cache = new HashMap<>();
    }

    @Override
    public List<UserBriefVO> getRecommendation(IntendVO intendVO, int uid) {
        System.out.println("EatLater request: " + JSON.toJSONString(intendVO));
        /* add current intend slot to intendTable */
        scheduleRepository.addIntendSlot(uid, intendVO.startTime, intendVO.endTime);
        List<UserBriefVO> res = new ArrayList<>();
        /* match by slots */
        List<Integer> intendMatchList = scheduleRepository.findByMatchedSlot(uid, intendVO.startTime, intendVO.endTime);
        List<Integer> busyMatchList = scheduleRepository.findByNonConlictSlot(uid, intendVO.startTime, intendVO.endTime);
        Set<Integer> set = new HashSet<>();
        List<Integer> mergedList = new ArrayList<>();
        for (int i: intendMatchList) {
            if (!set.contains(i)) {
                set.add(i);
                mergedList.add(i);
            }
        }

        /* filter by other info: college, major, gender and so on*/
        // TODO

        //add res
        int counter = 0;
        for (int id: mergedList) {
            if (counter >= ConstValue.RECOM_LIMIT) break;
            if (!cache.containsKey(id)) {
                UserBriefVO userBriefVO = this.makeUserBriefVO(id, -1);
                // add into cached
                cache.put(id, userBriefVO);
            }
            res.add(cache.get(id));
            counter++;
        }


        return res;
    }

    @Override
    public List<UserBriefVO> getRealTimeRecommendation(RealTimeLocationVO vo) {
        // update current location to userlocation table
        String time = TimeStrHelper.getCurrentTime();

        if (!locationRepository.exist(vo.uid)) {
            locationRepository.addUserLocation(vo.latitude, vo.longitude, vo.uid, time);
        } else {
            locationRepository.updateUserLocation(vo.latitude, vo.longitude, vo.uid, time);
        }

        /* recommend based on recent location report and closest users */
        String queryTimeStr = TimeStrHelper.getTimeBefore(ConstValue.NOW_BEFORE);
        System.out.println(queryTimeStr);
        List<UserLocation> querylist = locationRepository.getAllLocation(queryTimeStr);
        System.out.println("all location:" +JSON.toJSONString(querylist));
        /* filter busy slot conflict */
        List<UserLocation> list = new ArrayList<>();

        Set<Integer> set = new HashSet<>();
        List<Integer> busyMatchList = scheduleRepository.findByConflictSlot(vo.uid, TimeStrHelper.getCurrentTime(), TimeStrHelper.getTimeBefore(-30));
        System.out.println("conflict slot:" + JSON.toJSONString(busyMatchList));
        for (Integer i: busyMatchList) {
            set.add(i);
        }
        set.add(vo.uid);
        System.out.println(set);
        for (UserLocation uloc: querylist) {
            System.out.println("uloc:" + JSON.toJSONString(uloc));
            if (!set.contains(uloc.getId())) {
                list.add(uloc);
            }
        }
        System.out.println("filtered list:" + JSON.toJSONString(list));
        /* sort by distance*/
        PriorityQueue<Distance> pq = new PriorityQueue<>();   // min heap
        for (UserLocation userLocation: list) {
            double dist = DistanceHelper.distance(vo.latitude, vo.longitude, userLocation.getLatitude(), userLocation.getLongitude());
            pq.offer(new Distance(userLocation.getId(), dist));
            if (pq.size() > ConstValue.EATNOW_RECOM_LIMIT) {
                pq.poll();
            }
        }
        List<UserBriefVO> res = new ArrayList<>();
        int limit = Math.min(ConstValue.EATNOW_RECOM_LIMIT, pq.size());
        System.out.println("limit:" + limit);
        for (int i = 0; i < limit; i++) {
            Distance dobj = pq.poll();
            if (!cache.containsKey(dobj.uid)) {
                UserBriefVO userBriefVO = this.makeUserBriefVO(dobj.uid, dobj.dist);
                cache.put(dobj.uid, userBriefVO);
            }
            res.add(cache.get(dobj.uid));
        }
        System.out.println("res:" + JSON.toJSONString(res));
        return res;
    }

    private class Distance implements Comparable {
        public int uid;
        public double dist;
        public Distance(int id, double d) {
            this.uid = id;
            this.dist = d;
        }

        @Override
        public int compareTo(Object o) {
            Distance dobj = (Distance) o;
            if (this.dist == dobj.dist) return 0;
            return this.dist > dobj.dist ? 1 : -1;
        }
    }

    private UserBriefVO makeUserBriefVO(int uid, double distance) {
        // query user info
        Optional<UserProfile> optionalUserProfile = userProfileRepository.findProfileById(uid);
        if (!optionalUserProfile.isPresent()) {
            throw new ResourceNotFoundException("user not found");
        }
        UserProfile userProfile = optionalUserProfile.get();
        UserBriefVO userBriefVO = new UserBriefVO();
        userBriefVO.username = userProfile.getUsername();
        userBriefVO.avatar = "default";
        userBriefVO.gender = userProfile.getGender();
        userBriefVO.college = userProfile.getCollege();
        userBriefVO.major = userProfile.getMajor();
        userBriefVO.distance = distance;
        userBriefVO.uid = userProfile.getId();
        return userBriefVO;
    }
}
