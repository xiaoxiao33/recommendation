package com.se.repository.impl;

import com.se.Model.Invitation;
import com.se.repository.LocationRepository;
import com.se.Model.UserLocation;
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

@Repository
public class LocationRepositoryImpl implements LocationRepository {


    /**
     *
     * @param date "yyyy-mm-dd-hh-mm" already subtract 20 min from current time
     * @return all entries with updated time that is within 20 minutes of date.
     */
    public List<UserLocation> getAllLocation(String date) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        List<UserLocation> result = new ArrayList<>();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserLocation> query = builder.createQuery(UserLocation.class);
            Root<UserLocation> root = query.from(UserLocation.class);

            query.select(root).where(builder.greaterThanOrEqualTo(root.get("update_time"), date));

            Query<UserLocation> q = session.createQuery(query);

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
     * @param latitude
     * @param longitude
     * @param userId
     */
    public void addUserLocation(double latitude, double longitude, int userId, String time) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            UserLocation ul = UserLocation.builder().id(userId).latitude(latitude).longitude(longitude).update_time(time).build();
            session.save(ul);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return;
    }


    /**
     *
     * @param latitude
     * @param longitude
     * @param userId
     */

    public void updateUserLocation(double latitude, double longitude, int userId, String time) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<UserLocation> update = builder.createCriteriaUpdate(UserLocation.class);
            Root root = update.from(UserLocation.class);


            update.set("latitude", latitude);
            update.set("longitude", longitude);
            update.set("update_time", time);
            update.where(builder.equal(root.get("id"), userId));
            session.createQuery(update).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return;
    }

    /**
     *
     * @param uid
     * @return true if userlocation table already contains location record of this user
     */
    public boolean exist(int uid){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        List<UserLocation> result = new ArrayList<>();

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserLocation> query = builder.createQuery(UserLocation.class);
            Root<UserLocation> root = query.from(UserLocation.class);

            query.select(root).where(builder.equal(root.get("id"), uid));

            Query<UserLocation> q = session.createQuery(query);

            result = q.getResultList();

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

        if(result.isEmpty()){
            //System.out.println("No");
            return false;
        }
        else {
            //System.out.println("Yes");
            return true;
        }
    }

    public static void main(String[] args){
        /*LocationRepositoryImpl lri = new LocationRepositoryImpl();
        lri.addUserLocation(41.701, 100.2, 1, "2019-04-05-19-30");
        lri.addUserLocation(41.702, 100.2, 2, "2019-04-05-19-40");
        lri.addUserLocation(41.703, 100.2, 3, "2019-04-05-19-50");
        lri.addUserLocation(41.704, 100.2, 4, "2019-04-05-19-55");

        lri.updateUserLocation(40.701, 100.2, 1, "2019-04-05-19-40");

        List<UserLocation> result = lri.getAllLocation("2019-04-05-19-45");
        System.out.println(result);
        lri.exist(3);
        lri.exist(5);*/
    }

}
