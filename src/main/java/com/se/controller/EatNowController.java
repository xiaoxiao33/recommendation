package com.se.controller;

import com.se.vo.RealTimeLocationVO;
import com.se.vo.UserBriefVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eatNow")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EatNowController {

    @PostMapping("/recommendation")
    public ResponseEntity<List<UserBriefVO>> getRecommendationList(@RequestBody RealTimeLocationVO vo) {
        List<UserBriefVO> list = new ArrayList<>(); // mock empty list
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
