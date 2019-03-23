package com.se.service.impl;

import com.se.repository.InvitationRepository;
import com.se.service.InvitationService;
import com.se.vo.InvitationVO;
import org.springframework.beans.factory.annotation.Autowired;

public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;

    @Override
    public boolean sendInvitation(InvitationVO vo) {
        if (invitationRepository.addInvitation(vo)) {
            return true;
        }
        return false;
    }
}
