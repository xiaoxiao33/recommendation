package com.se.repository.impl;

import com.se.exception.DataServiceOperationException;
import com.se.repository.InvitationRepository;
import com.se.util.InvitationStatus;
import com.se.vo.InvitationVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvitationRepositoryImpl implements InvitationRepository {

    /**
     *
     * @param invitation
     * @return true if a new invitation entry is added to invitation table. False otherwise.
     */
    public boolean addInvitation(InvitationVO invitation) {
        return true;
    }


    /**
     *
     * @param invitationId
     * @return invitation object stored in database
     */
    public InvitationVO getInvitationById(int invitationId) {
        return null;
    }

    /**
     * @param uid senderId or receiverId
     * @param status status of invitations
     * @return a list of invitations
     */
    public List<InvitationVO> getInvitationsByStatus(int uid, InvitationStatus status) {
        return null;
    }

    /**
     *
     * @param uid senderId or receiverId
     * @param currentTime For each day, time is divided into 1 hour granularity, from 0 to 23. Input is of the format
     *                    "20190103-2", meaning 2019 Jan 3rd, 2:00.
     * @return a list of accepted invitations with start time >= the currentTime.
     */
    public List<InvitationVO> getAcceptedInvitationsByTime(int uid, String currentTime) {
        return null;
    }


    /**
     *
     * @param uid search for all invitation entries with recieverId or sendId equal to uid
     * @param startTime search for all invitation entries with start field the same as startTime in database
     * @return return if all associated invitation entries are updated to REJECTED, throw exception otherwise.
     */
    public void setInvitationStatusRejected(int uid, String startTime) throws DataServiceOperationException {
        return;
    }


    /**
     *
     * @param invitationId
     * @return return if invitation status is set to be ACCEPTED successfully, throw exception otherwise.
     */
    public void setInvitationStatusAccepted(int invitationId) throws DataServiceOperationException {
        return;
    }


    /**
     *
     * @param invitationId
     * @return the status of the invitation
     */
    public InvitationStatus checkInvitationStatus(int invitationId) {
        return null;
    }


}
