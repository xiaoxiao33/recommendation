package com.se.repository;

import com.se.model.UserInfo;
import com.se.model.UserProfile;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.*;

import static java.util.Optional.ofNullable;

@Service
public class UserProfileRepository {

    // Save a new user profile
    public UserProfile saveProfile(UserProfile user) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return user;
    }


    //save an existing user profile
    public UserProfile updateProfile(UserProfile user){

        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<UserProfile> update = builder.createCriteriaUpdate(UserProfile.class);
            Root root = update.from(UserProfile.class);

            update.set("gender", user.getGender());
            update.set("major", user.getMajor());
            update.set("age", user.getAge());
            update.set("year", user.getYear());
            update.where(builder.equal(root.get("id"), user.getId()));
            session.createQuery(update).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return user;
    }

    public Optional<UserProfile > findProfileById(int id) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Optional<UserProfile> opt = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserProfile> query = builder.createQuery(UserProfile.class);
            Root<UserProfile> root = query.from(UserProfile.class);
            query.select(root).where(builder.equal(root.get("id"), id));
            Query<UserProfile> q = session.createQuery(query);
            //avoid exception, set max results as 1
            List<UserProfile> result = q.getResultList();
            UserProfile userProfile = null;
            if (!result.isEmpty()) {
                userProfile = result.get(0);
            }
            //System.out.println(user.getId()+user.getUsername()+": Get by Id");
            opt = Optional.ofNullable(userProfile);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return opt;
    }

    public Optional<UserProfile > findProfileByName(String name) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Optional<UserProfile> opt = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserProfile> query = builder.createQuery(UserProfile.class);
            Root<UserProfile> root = query.from(UserProfile.class);
            query.select(root).where(builder.equal(root.get("username"), name));
            Query<UserProfile> q = session.createQuery(query);
            //avoid exception, set max results as 1
            List<UserProfile> result = q.getResultList();
            UserProfile userProfile = null;
            if (!result.isEmpty()) {
                userProfile = result.get(0);
            }
            //System.out.println(user.getId()+user.getUsername()+": Get by name");
            opt = Optional.ofNullable(userProfile);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return opt;
    }


    public List<UserProfile> findAllProfile() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        List<UserProfile> result = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserProfile> query = builder.createQuery(UserProfile.class);
            Root<UserProfile> root = query.from(UserProfile.class);
            query.select(root);
            Query<UserProfile> q = session.createQuery(query);
            result = q.list();
            /*for(UserProfile u: result){
                System.out.println(u.getId()+" "+u.getUsername());
            }*/
            transaction.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    public static void main(String[] args){
        /*System.out.println("print user profile");
        UserProfileRepository u = new UserProfileRepository();
        UserProfile profile = com.se.model.UserProfile.builder().id(1).major("STAT").age(23).year("2020").build();
        u.updateProfile(profile);*/
    }
}
