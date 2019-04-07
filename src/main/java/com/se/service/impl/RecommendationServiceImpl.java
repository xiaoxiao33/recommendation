package com.se.service.impl;

import com.alibaba.fastjson.JSON;
import com.se.Model.UserLocation;
import com.se.exception.ResourceNotFoundException;
import com.se.repository.LocationRepository;
import com.se.repository.ScheduleRepository;
import com.se.repository.UserInfoRepository;
import com.se.repository.UserProfileRepository;
import com.se.service.RecommedationService;
import com.se.model.UserInfo;
import com.se.Model.UserProfile;
import com.se.util.ConstValue;
import com.se.util.DistanceHelper;
import com.se.vo.IntendVO;
import com.se.vo.RealTimeLocationVO;
import com.se.vo.UserBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecommendationServiceImpl implements RecommedationService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

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
        // TODO:
        // deduplicate
        Set<Integer> set = new HashSet<>();
        List<Integer> mergedList = new ArrayList<>();
        for (int i: intendMatchList) {
            if (!set.contains(i)) {
                set.add(i);
                mergedList.add(i);
            }
        }

        /* filter by other info ...*/
        // TODO

        //add res
        int counter = 0;
        for (int id: mergedList) {
            if (counter >= ConstValue.RECOM_LIMIT) break;
            if (!cache.containsKey(id)) {

                UserBriefVO userBriefVO = this.makeUserBriefVO(id);
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
        String pattern = "yyyy-MM-dd-HH-mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date currentTime = new Date();
        String time = dateFormat.format(currentTime);
        System.out.println(time);
        locationRepository.updateUserLocation(vo.latitude, vo.longitude, vo.uid, time);

        /* recommend based on recent location report and closest users */
        long currentTimeValue = currentTime.getTime();
        Date queryTime = new Date(currentTimeValue - 20*60*1000);
        String queryTimeStr = dateFormat.format(queryTime);
        System.out.println(queryTimeStr);
        List<UserLocation> list = locationRepository.getAllLocation(queryTimeStr);
        /* sort by distance*/
        PriorityQueue<Distance> pq = new PriorityQueue<>();   // min heap
        for (UserLocation userLocation: list) {
            double dist = DistanceHelper.distance(vo.latitude, vo.latitude, userLocation.getLatitude(), userLocation.getLongitude());
            pq.offer(new Distance(userLocation.getId(), dist));
            if (pq.size() > ConstValue.EATNOW_RECOM_LIMIT) {
                pq.poll();
            }
        }
        List<UserBriefVO> res = new ArrayList<>();
        for (int i = 0; i < ConstValue.EATNOW_RECOM_LIMIT; i++) {
            Distance dobj = pq.poll();
            if (!cache.containsKey(dobj.uid)) {
                UserBriefVO userBriefVO = this.makeUserBriefVO(dobj.uid);
                cache.put(dobj.uid, userBriefVO);
            }
            res.add(cache.get(dobj.uid));
        }
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

    private UserBriefVO makeUserBriefVO(int uid) {
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
        return userBriefVO;
    }
}
