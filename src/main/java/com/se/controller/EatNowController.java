package com.se.controller;

import com.se.Model.UserLocation;
import com.se.repository.LocationRepository;
import com.se.service.RecommedationService;
import com.se.vo.RealTimeLocationVO;
import com.se.vo.UserBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/eatNow")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EatNowController {

    @Autowired
    private RecommedationService recommedationService;

    @PostMapping("/recommendation")
    public ResponseEntity<List<UserBriefVO>> getRecommendationList(@RequestBody RealTimeLocationVO vo) {
        List<UserBriefVO> list = recommedationService.getRealTimeRecommendation(vo); // mock empty list
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
