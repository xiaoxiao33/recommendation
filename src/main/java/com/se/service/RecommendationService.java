package com.se.service;

import com.se.vo.IntendVO;
import com.se.vo.RealTimeLocationVO;
import com.se.vo.UserBriefVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RecommendationService {
    // match by intend slots
    // match by not busy slots
    public List<UserBriefVO> getRecommendation(IntendVO intendVO, int uid);

    public List<UserBriefVO> getRealTimeRecommendation(RealTimeLocationVO vo);

}
