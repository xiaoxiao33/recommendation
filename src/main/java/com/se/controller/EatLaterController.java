package com.se.controller;

import com.se.repository.IntendSlotRepository;
import com.se.service.RecommedationService;
import com.se.vo.IntendVO;
import com.se.vo.InvitationVO;
import com.se.vo.UserBriefVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/eatLater")
@CrossOrigin("http://localhost:8100")
public class EatLaterController {
    @Autowired
    private RecommedationService recommedationService;

    /**
     *
     * @param session
     * @param intendInfo    All info submitted in the eat later homepage
     * @return
     */
    @PostMapping("/recommendation")
    public ResponseEntity<List<UserBriefVO>> getRecommendationList(@RequestBody IntendVO intendInfo, HttpSession session) {

        return null;
    }

    /**
     *
     * @param session
     * @param invitationVO required params: senderId, receiverId, address, start, end
     * @return
     */
    @PostMapping("/sendInvitation")
    public ResponseEntity<String> sendInvitation(@RequestBody InvitationVO invitationVO, HttpSession session) {
        // add invitation
        return null;
    }

}
