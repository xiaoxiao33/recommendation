package com.se.repository.impl;

import com.se.repository.ScheduleRepository;
import com.se.vo.IntendVO;
import com.se.vo.InvitationVO;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.se.vo.BusyVO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Date;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {
    /**
     * Intended slots table
     * @param uid
     * @param start
     * @param end
     * @return uid
     */
    public List<Integer> findByMatchedSlot (int uid, String start, String end) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root<IntendVO> root = query.from(IntendVO.class);

            // result in the right side
            Predicate andA = builder.and(builder.greaterThan(root.get("startTime"),start), builder.lessThan(root.get("startTime"), end));

            // result in the left side
            Predicate andB = builder.and(builder.greaterThan(root.get("endTime"),start), builder.lessThan(root.get("endTime"), end));

            // result include input
            Predicate andC = builder.and(builder.lessThanOrEqualTo(root.get("startTime"),start), builder.greaterThanOrEqualTo(root.get("endTime"), end));

            // input include result
            Predicate andD = builder.and(builder.greaterThanOrEqualTo(root.get("startTime"),start), builder.lessThanOrEqualTo(root.get("endTime"), end));

            Predicate orClause = builder.or(andA, andB, andC, andD);
            query.select(root.get("userId")).where(orClause, builder.notEqual(root.get("userId"), uid)).distinct(true);

            Query<Integer> q = session.createQuery(query);
            transaction.commit();

            if (q.getResultList().size()==0){
                System.out.println("There is no matching slot");
            }else{
                for(Integer i: q.getResultList()) {
                    System.out.println("Matching ID: " + i);
                }
            }
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    /**
     * busy slots table
     * @param uid
     * @param start
     * @param end
     * @return
     */
    public List<Integer> findByNonConlictSlot (int uid, String start, String end) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root<BusyVO> root = query.from(BusyVO.class);

            Predicate orClause = builder.or(builder.greaterThanOrEqualTo(root.get("startTime"), end), builder.lessThanOrEqualTo(root.get("endTime"), start));
            query.select(root.get("userId")).where(orClause, builder.notEqual(root.get("userId"), uid)).distinct(true);

            Query<Integer> q = session.createQuery(query);
            transaction.commit();

            if (q.getResultList().size()==0){
                System.out.println("There is no non-conflict slot");
            }else{
                for(Integer i: q.getResultList()) {
                    System.out.println("Non-conflict ID: " + i);
                }
            }
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }


    /**
     * delete all rows previous to given time
     * @param pivot
     * @return
     */
    public boolean deleteExpiredBusySlots (String pivot) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<BusyVO> delete = builder.createCriteriaDelete(BusyVO.class);

            Root e = delete.from(BusyVO.class);

            //String currentTime = new SimpleDateFormat("yyyymmdd-hh").format(new Date());
            //delete.where(builder.lessThanOrEqualTo(e.get("end_time"), currentTime));

            delete.where(builder.lessThanOrEqualTo(e.get("endTime"), pivot));

            session.createQuery(delete).executeUpdate();

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return true;
    }

    /**
     *
     * @param uid userId
     * @param start
     * @param end
     * @return add a entry to busy slots table with the three fields.
     */
    public boolean addSlot(int uid, String start, String end) {
        SessionFactory factory = new Configuration().configure().addAnnotatedClass(BusyVO.class).buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            BusyVO busyVO = BusyVO.builder().userId(uid).startTime(start).endTime(end).build();
            session.save(busyVO);
            transaction.commit();
        } catch (Exception e) {




            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return true;
    }

    /**
     *
     * @param uid userId
     * @param start
     * @param end
     * @return add a entry to intend slots table with the three fields.
     */
    public boolean addIntendSlot(int uid, String start, String end) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            IntendVO intendVO = IntendVO.builder().userId(uid).startTime(start).endTime(end).build();
            session.save(intendVO);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return true;
    }

    public static void main(String[] args){
        System.out.println("Scheduler test");
        ScheduleRepositoryImpl srImpl = new ScheduleRepositoryImpl();

        // test addSlot
        // srImpl.addSlot(3,"20190325-01", "20190325-06");

        // test addIntendSlot
        srImpl.addIntendSlot(1,"20190304-01", "20190304-06");
        srImpl.addIntendSlot(1,"20190327-01", "20190327-06");
        srImpl.addIntendSlot(2,"20190326-01", "20190326-06");
        srImpl.addIntendSlot(3,"20190325-01", "20190325-06");

        // test deleteExpiredBusySlots
        srImpl.deleteExpiredBusySlots("20190302-07");

        // test findByNonConlictSlot
        srImpl.findByNonConlictSlot(4, "20190225-08", "20190328-08");
        srImpl.findByNonConlictSlot(4, "20190325-08", "20190328-08");

        // test findByMatchedSlot
        srImpl.findByMatchedSlot(4, "20190327-01", "20190328-08"); // input include result
        srImpl.findByMatchedSlot(4, "20190326-02", "20190326-04"); // result include input
        srImpl.findByMatchedSlot(4, "20190303-02", "20190304-04"); // result in the right
        srImpl.findByMatchedSlot(4, "20190304-04", "20190304-09"); // result in the left
        srImpl.findByMatchedSlot(4, "20190325-01", "20190325-06"); // exactly the same interval
        srImpl.findByMatchedSlot(4, "20190329-01", "20190329-06"); // No matching interval



        System.out.println(" Done with addin" + "g slot");
    }
}
