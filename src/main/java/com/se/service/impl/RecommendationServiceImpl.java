package com.se.service.impl;

import com.se.exception.ResourceNotFoundException;
import com.se.repository.ScheduleRepository;
import com.se.repository.UserInfoRepository;
import com.se.repository.UserProfileRepository;
import com.se.service.RecommedationService;
import com.se.model.UserInfo;
import com.se.model.UserProfile;
import com.se.util.ConstValue;
import com.se.vo.IntendVO;
import com.se.vo.UserBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationServiceImpl implements RecommedationService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    // in-mem cache
    private Map<Integer, UserBriefVO> cache;


    public RecommendationServiceImpl() {
        this.cache = new HashMap<>();
    }

    @Override
    public List<UserBriefVO> getRecommendation(IntendVO intendVO, int uid) {
        List<UserBriefVO> res = new ArrayList<>();
        /* match by slots */
        List<Integer> intendMatchList = scheduleRepository.findByMatchedSlot(uid, intendVO.getStartTime(), intendVO.getEndTime());
        List<Integer> busyMatchList = scheduleRepository.findByNonConlictSlot(uid, intendVO.getStartTime(), intendVO.getEndTime());
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
                // query user info
                Optional<UserInfo> optionalUserInfo = userInfoRepository.findInfoById(id);
                Optional<UserProfile> optionalUserProfile = userProfileRepository.findProfileById(id);
                if (!optionalUserInfo.isPresent() || !optionalUserProfile.isPresent()) {
                    throw new ResourceNotFoundException("user not found");
                }
                UserInfo userInfo = optionalUserInfo.get();
                UserProfile userProfile = optionalUserProfile.get();
                UserBriefVO userBriefVO = new UserBriefVO();
                userBriefVO.username = userInfo.getUsername();
                userBriefVO.avatar = "default";
                userBriefVO.gender = userProfile.getGender();
                userBriefVO.college = "College";
                userBriefVO.major = userProfile.getMajor();
                // add into cached
                cache.put(id, userBriefVO);
            }
            res.add(cache.get(id));
            counter++;
        }


        return res;
    }
}
