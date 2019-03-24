package com.se.service;

import com.se.vo.InvitationVO;
import org.springframework.stereotype.Service;


public interface InvitationService {

    public boolean sendInvitation(InvitationVO vo);

}
