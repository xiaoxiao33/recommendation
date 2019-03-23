package com.se.service;

import com.se.vo.InvitationVO;
import org.springframework.stereotype.Service;

@Service
public interface InvitationService {

    public boolean sendInvitation(InvitationVO vo);

}
