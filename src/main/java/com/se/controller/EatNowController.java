package com.se.controller;

import com.alibaba.fastjson.JSON;
import com.se.repository.LocationRepository;
import com.se.service.RecommendationService;
import com.se.util.TimeStrHelper;
import com.se.vo.RealTimeLocationVO;
import com.se.vo.UserBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eatNow")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EatNowController {

    @Autowired
    private RecommendationService recommedationService;

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/recommendation")
    public ResponseEntity<List<UserBriefVO>> getRecommendationList(@RequestBody RealTimeLocationVO vo) {
        List<UserBriefVO> list = recommedationService.getRealTimeRecommendation(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/uploadLocation")
    public ResponseEntity<String> uploadLoctaion(@RequestBody RealTimeLocationVO vo) {
        System.out.println("RealTimeLocationVO: "+JSON.toJSONString(vo));
        if (locationRepository.exist(vo.uid)) {
            locationRepository.updateUserLocation(vo.latitude, vo.longitude, vo.uid, TimeStrHelper.getCurrentTime());
        } else {
            locationRepository.addUserLocation(vo.latitude, vo.longitude, vo.uid, TimeStrHelper.getCurrentTime());
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
