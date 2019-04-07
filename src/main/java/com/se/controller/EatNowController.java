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
        UserBriefVO user = new UserBriefVO();
        user.avatar = "010101010";
        user.college = "morse";
        user.gender = "unknown";
        user.major = "computer science";
        user.username = "Jerry";
        user.uid = 4;
        user.distance = 0.5;
        list.add(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
