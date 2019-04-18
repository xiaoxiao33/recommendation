package com.se.controller;

import com.alibaba.fastjson.JSON;
import com.se.service.InvitationService;
import com.se.service.RecommendationService;
import com.se.vo.IntendVO;
import com.se.vo.InvitationVO;
import com.se.vo.UserBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eatLater")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EatLaterController {
    @Autowired
    private RecommendationService recommedationService;

    @Autowired
    private InvitationService invitationService;

    /**
     *
     * @param // session
     * @param intendInfo    All info submitted in the eat later homepage, one for a slot only
     * @return
     */
    @PostMapping("/recommendation")
    @ResponseBody
    public ResponseEntity<List<UserBriefVO>> getRecommendationList(@RequestBody IntendVO intendInfo) {
        System.out.println("IntendVO: "+JSON.toJSONString(intendInfo));
        List<UserBriefVO> list = recommedationService.getRecommendation(intendInfo, intendInfo.userId);
//        List<UserBriefVO> list = new ArrayList<>();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     *
     * @param // session
     * @param invitationVO required params: senderId, receiverId, address, start, end
     * @return
     */
    @PostMapping("/sendInvitation")
    @ResponseBody
    public ResponseEntity<String> sendInvitation(@RequestBody InvitationVO invitationVO) {
//        System.out.println(uid + ":" + invitationVO.getSenderId() + "," + invitationVO.getReceiverId());
//        if (uid != invitationVO.getSenderId()) {
//            return new ResponseEntity<>("Warning: sender id not match", HttpStatus.OK);
//        }
        if (!invitationService.sendInvitation(invitationVO)) {
            return new ResponseEntity<>("send failed", HttpStatus.OK);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
