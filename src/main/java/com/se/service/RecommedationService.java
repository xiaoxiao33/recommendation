package com.se.service;

import com.se.repository.IntendSlotRepository;
import com.se.vo.IntendVO;
import com.se.vo.UserBriefVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecommedationService {
    // match by intend slots
    // match by not busy slots
    public List<UserBriefVO> getRecommendation(IntendVO intendVO, int uid);



}
