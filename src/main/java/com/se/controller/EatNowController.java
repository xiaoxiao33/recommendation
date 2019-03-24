package com.se.controller;

import com.se.vo.UserBriefVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eatNow")
@CrossOrigin("http://localhost:8100")
public class EatNowController {

    @PostMapping("/recommendation")
    public ResponseEntity<List<UserBriefVO>> getRecommendationList(HttpSession session, String realTimeAddress) {
        List<UserBriefVO> list = new ArrayList<>(); // mock empty list
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
