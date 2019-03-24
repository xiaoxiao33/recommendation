package com.se.repository.impl;

import com.se.exception.DataServiceOperationException;
import com.se.repository.InvitationRepository;
import com.se.util.InvitationStatus;
import com.se.vo.BusyVO;
import com.se.vo.InvitationVO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
public class InvitationRepositoryImpl implements InvitationRepository {

    /**
     *
     * @param invitation
     * @return true if a new invitation entry is added to invitation table. False otherwise.
     */
    public boolean addInvitation(InvitationVO invitation) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(invitation);
            transaction.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return false;
    }


    /**
     *
     * @param invitationId
     * @return invitation object stored in database
     */
    public InvitationVO getInvitationById(int invitationId) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        com.se.vo.InvitationVO invitation = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<com.se.vo.InvitationVO> query = builder.createQuery(com.se.vo.InvitationVO.class);
            Root<com.se.vo.InvitationVO> root = query.from(com.se.vo.InvitationVO.class);
            query.select(root).where(builder.equal(root.get("invitationId"), invitationId));
            Query<com.se.vo.InvitationVO> q = session.createQuery(query);
            //avoid exception, set max results as 1
            List<com.se.vo.InvitationVO> result = q.getResultList();
            if (!result.isEmpty()) {
                 invitation = result.get(0);
            }
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return invitation;
    }

    /**
     * @param uid senderId or receiverId
     * @param status status of invitations
     * @return a list of invitations
     */
    public List<InvitationVO> getInvitationsByStatus(int uid, InvitationStatus status) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        List<com.se.vo.InvitationVO> result = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<com.se.vo.InvitationVO> query = builder.createQuery(com.se.vo.InvitationVO.class);
            Root<com.se.vo.InvitationVO> root = query.from(com.se.vo.InvitationVO.class);

            Predicate orClause = builder.or(builder.equal(root.get("senderId"), uid), builder.equal(root.get("receiverId"),uid));
            query.select(root).where(builder.equal(root.get("status"), status), orClause);

            Query<com.se.vo.InvitationVO> q = session.createQuery(query);

            result = q.getResultList();

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }

    /**
     *
     * @param uid senderId or receiverId
     * @param currentTime For each day, time is divided into 1 hour granularity, from 0 to 23. Input is of the format
     *                    "20190103-2", meaning 2019 Jan 3rd, 2:00.
     * @return a list of accepted invitations with start time >= the currentTime.
     */
    public List<InvitationVO> getAcceptedInvitationsByTime(int uid, String currentTime) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        List<com.se.vo.InvitationVO> result = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<com.se.vo.InvitationVO> query = builder.createQuery(com.se.vo.InvitationVO.class);
            Root<com.se.vo.InvitationVO> root = query.from(com.se.vo.InvitationVO.class);

            Predicate orClause = builder.or(builder.equal(root.get("senderId"), uid), builder.equal(root.get("receiverId"),uid));
            query.select(root).where(builder.greaterThanOrEqualTo(root.get("start"), currentTime), orClause, builder.equal(root.get("status"), InvitationStatus.ACCEPTED));

            Query<com.se.vo.InvitationVO> q = session.createQuery(query);

            result = q.getResultList();

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }


    /**
     *
     * @param uid search for all invitation entries with receiverId or sendId equal to uid
     * @param startTime search for all invitation entries with start field the same as startTime in database
     * @return return if all associated invitation entries are updated to REJECTED, throw exception otherwise.
     */
    public void setInvitationStatusRejected(int uid, String startTime) throws DataServiceOperationException {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        List<com.se.vo.InvitationVO> result = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<InvitationVO> update = builder.createCriteriaUpdate(com.se.vo.InvitationVO.class);
            Root<com.se.vo.InvitationVO> root = update.from(com.se.vo.InvitationVO.class);

            update.set("status", InvitationStatus.REJECTED);
            Predicate orClause = builder.or(builder.equal(root.get("senderId"), uid), builder.equal(root.get("receiverId"),uid));
            update.where(builder.equal(root.get("start"), startTime), orClause);

            session.createQuery(update).executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    /**
     *
     * @param invitationId
     * @return return if invitation status is set to be ACCEPTED successfully, throw exception otherwise.
     */
    public void setInvitationStatusAccepted(int invitationId) throws DataServiceOperationException {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        List<com.se.vo.InvitationVO> result = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<InvitationVO> update = builder.createCriteriaUpdate(com.se.vo.InvitationVO.class);
            Root<com.se.vo.InvitationVO> root = update.from(com.se.vo.InvitationVO.class);

            update.set("status", InvitationStatus.ACCEPTED);
            update.where(builder.equal(root.get("invitationId"), invitationId));

            session.createQuery(update).executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    /**
     *
     * @param invitationId
     * @return the status of the invitation
     */
    public InvitationStatus checkInvitationStatus(int invitationId) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        com.se.vo.InvitationVO invitation = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<com.se.vo.InvitationVO> query = builder.createQuery(com.se.vo.InvitationVO.class);
            Root<com.se.vo.InvitationVO> root = query.from(com.se.vo.InvitationVO.class);
            query.select(root).where(builder.equal(root.get("invitationId"), invitationId));
            Query<com.se.vo.InvitationVO> q = session.createQuery(query);
            //avoid exception, set max results as 1
            List<com.se.vo.InvitationVO> result = q.getResultList();
            if (!result.isEmpty()) {
                invitation = result.get(0);
            }
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return invitation.getStatus();
    }

    public static void main(String[] args){
        System.out.println("******Invitation test******");
        InvitationRepositoryImpl impl = new InvitationRepositoryImpl();
        InvitationVO ivo = InvitationVO.builder().invitationId(4).senderId(1).receiverId(2).start("a").end("b").latitude(1.0).longitude(1.0).status(com.se.util.InvitationStatus.ACCEPTED).build();
        impl.addInvitation(ivo);
    }
}
