package com.se.repository.impl;

import com.se.Model.Invitation;
import com.se.repository.InvitationRepository;
import com.se.util.InvitationStatus;
import com.se.vo.InvitationVO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

// import com.alibaba.fastjson.JSON;

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
            /*InvitationVO to Invitation entity*/
            Invitation entity = Invitation.builder()
                    .senderId(invitation.senderId)
                    .receiverId(invitation.receiverId)
                    .start(invitation.start)
                    .end(invitation.end)
                    .latitude(invitation.latitude)
                    .longitude(invitation.longitude)
                    .status(invitation.status)
                    .build();
            /*end convert*/
            session.save(entity);
            transaction.commit();
            session.close();
            factory.close();
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
            CriteriaQuery<Invitation> query = builder.createQuery(com.se.Model.Invitation.class);
            Root<Invitation> root = query.from(com.se.Model.Invitation.class);
            query.select(root).where(builder.equal(root.get("invitationId"), invitationId));
            Query<Invitation> q = session.createQuery(query);
            //avoid exception, set max results as 1
            List<Invitation> result = q.getResultList();
            if (!result.isEmpty()) {
                 Invitation entity = result.get(0);
                 invitation = new InvitationVO(entity);
            }
            transaction.commit();
            session.close();
            factory.close();
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
        List<com.se.vo.InvitationVO> result = new ArrayList<>();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Invitation> query = builder.createQuery(Invitation.class);
            Root<Invitation> root = query.from(Invitation.class);

            Predicate orClause = builder.or(builder.equal(root.get("senderId"), uid), builder.equal(root.get("receiverId"),uid));
            query.select(root).where(builder.equal(root.get("status"), status), orClause);

            Query<Invitation> q = session.createQuery(query);

            List<Invitation> res = q.getResultList();
//            System.out.println("res size: " + res.size());
            for (Invitation entity: res) {
                result.add(new InvitationVO(entity));
            }
            transaction.commit();
            session.close();
            factory.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
//        System.out.println("db query: "+JSON.toJSONString(result));
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
        List<com.se.vo.InvitationVO> result = new ArrayList<>();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Invitation> query = builder.createQuery(Invitation.class);
            Root<Invitation> root = query.from(Invitation.class);

            Predicate orClause = builder.or(builder.equal(root.get("senderId"), uid), builder.equal(root.get("receiverId"),uid));
            query.select(root).where(builder.greaterThanOrEqualTo(root.get("start"), currentTime), orClause, builder.equal(root.get("status"), InvitationStatus.ACCEPTED));

            Query<Invitation> q = session.createQuery(query);

            List<Invitation> res = q.getResultList();
            for (Invitation entity: res) {
                result.add(new InvitationVO(entity));
            }
            transaction.commit();
            session.close();
            factory.close();
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
     * @param startTime search for all active invitation entries with start field the same as startTime in database
     * @return return if all associated invitation entries are updated to REJECTED, throw exception otherwise.
     */
    public void setInvitationStatusRejected(int uid, String startTime)  {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Invitation> update = builder.createCriteriaUpdate(Invitation.class);
            Root<Invitation> root = update.from(Invitation.class);

            update.set("status", InvitationStatus.REJECTED);
            Predicate orClause = builder.or(builder.equal(root.get("senderId"), uid), builder.equal(root.get("receiverId"),uid));
            update.where(builder.equal(root.get("start"), startTime), orClause, builder.equal(root.get("status"), InvitationStatus.ACTIVE));

            session.createQuery(update).executeUpdate();

            transaction.commit();
            session.close();
            factory.close();
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
    public void setInvitationStatusAccepted(int invitationId) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Invitation> update = builder.createCriteriaUpdate(Invitation.class);
            Root<Invitation> root = update.from(Invitation.class);

            update.set("status", InvitationStatus.ACCEPTED);
            update.where(builder.equal(root.get("invitationId"), invitationId));

            session.createQuery(update).executeUpdate();

            transaction.commit();
            session.close();
            factory.close();
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
        Invitation invitation = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Invitation> query = builder.createQuery(Invitation.class);
            Root<Invitation> root = query.from(Invitation.class);
            query.select(root).where(builder.equal(root.get("invitationId"), invitationId));
            Query<Invitation> q = session.createQuery(query);
            //avoid exception, set max results as 1
            List<Invitation> result = q.getResultList();
            if (!result.isEmpty()) {
                invitation = result.get(0);
            }
            transaction.commit();
            session.close();
            factory.close();
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
        Invitation ivo = Invitation.builder().invitationId(4).senderId(1).receiverId(2).start("a").end("b").latitude(1.0).longitude(1.0).status(com.se.util.InvitationStatus.ACCEPTED).build();
        impl.addInvitation(new InvitationVO(ivo));
    }
}
