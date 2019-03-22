package com.se.repository;

import com.se.exception.DataServiceOperationException;
import com.se.util.InvitationStatus;
import com.se.vo.InvitationVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InvitationRepository {

    /**
     *
     * @param invitation
     * @return true if a new invitation entry is added to invitation table. False otherwise.
     */
    public boolean addInvitation(InvitationVO invitation);


    /**
     *
     * @param invitationId
     * @return invitation object stored in database
     */
    public InvitationVO getInvitationById(int invitationId);

    /**
     * @param uid senderId or receiverId
     * @param status status of invitations
     * @return a list of invitations
     */
    public List<InvitationVO> getInvitationsByStatus(int uid, InvitationStatus status);

    /**
     *
     * @param uid senderId or receiverId
     * @param currentTime For each day, time is divided into 1 hour granularity, from 0 to 23. Input is of the format
     *                    "20190103-2", meaning 2019 Jan 3rd, 2:00.
     * @return a list of accepted invitations with start time >= the currentTime.
     */
    public List<InvitationVO> getAcceptedInvitationsByTime(int uid, String currentTime);


    /**
     *
     * @param uid search gor all invitation entries with recieverId or sendId equal to uid
     * @param startTime search for all invitation entries with start field the same as startTime in database
     * @return true if all associated invitation entries are updated to REJECTED, false otherwise.
     */
    public void setInvitationStatusRejected(int uid, String startTime) throws DataServiceOperationException;


    /**
     *
     * @param invitationId
     * @return true if invitation status is set to be ACCEPTED successfully, false otherwise.
     */
    public void setInvitationStatusAccepted(int invitationId) throws DataServiceOperationException;


    /**
     *
     * @param invitationId
     * @return the status of the invitation
     */
    public InvitationStatus checkInvitationStatus(int invitationId);

}
