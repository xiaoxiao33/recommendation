package com.se.controller;

import com.se.service.InvitationService;
import com.se.service.RecommedationService;
import com.se.vo.IntendVO;
import com.se.vo.InvitationVO;
import com.se.vo.UserBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/eatLater")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EatLaterController {
    @Autowired
    private RecommedationService recommedationService;

    @Autowired
    private InvitationService invitationService;

    /**
     *
     * @param session
     * @param intendInfo    All info submitted in the eat later homepage, one for a slot only
     * @return
     */
    @PostMapping("/recommendation")
    public ResponseEntity<List<UserBriefVO>> getRecommendationList(@RequestBody IntendVO intendInfo, HttpSession session) {
        int uid = (int) session.getAttribute("id");
        List<UserBriefVO> list = recommedationService.getRecommendation(intendInfo, uid);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     *
     * @param session
     * @param invitationVO required params: senderId, receiverId, address, start, end
     * @return
     */
    @PostMapping("/sendInvitation")
    public ResponseEntity<String> sendInvitation(@RequestBody InvitationVO invitationVO, HttpSession session) {
        int uid = (int)session.getAttribute("id");
        System.out.println(uid + ":" + invitationVO.getSenderId() + "," + invitationVO.getReceiverId());
        if (uid != invitationVO.getSenderId()) {
            return new ResponseEntity<>("Warning: sender id not match", HttpStatus.OK);
        }
        if (!invitationService.sendInvitation(invitationVO)) {
            return new ResponseEntity<>("send failed", HttpStatus.OK);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
