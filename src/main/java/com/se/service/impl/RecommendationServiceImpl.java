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

import java.util.*;

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
	return null;

    }
}
