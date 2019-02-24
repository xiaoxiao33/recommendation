package com.se.repository;

import com.se.model.UserInfo;
import com.se.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.*;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.*;

import static java.util.Optional.ofNullable;

@Service
public class UserProfileRepository {
    Map<Integer, UserProfile> userProfileMap = new HashMap<>();

    public UserProfile save(UserProfile user) {
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

    public Optional<UserProfile > findUserById(int id) {
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
            UserProfile user = q.setMaxResults(1).getSingleResult();
            System.out.println(user.getId()+user.getUsername()+": Get by Id");
            opt = Optional.of(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return opt;
    }

    public Optional<UserProfile > findUserByName(String name) {
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
            UserProfile user = q.setMaxResults(1).getSingleResult();
            System.out.println(user.getId()+user.getUsername()+": Get by name");
            opt = Optional.of(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return opt;
    }

    public boolean addNewProfile(UserInfo userInfo) {
        userProfileMap.put(userInfo.getId(), new UserProfile(userInfo));
        UserProfile userProfile = new UserProfile(userInfo);
        save(userProfile);
        return true;
    }

    public List<UserProfile> findAll() {
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
            for(UserProfile u: result){
                System.out.println(u.getId()+" "+u.getUsername());
            }
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

    public List<UserProfile> allUsers() {
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
            for(UserProfile u: result){
                System.out.println(u.getId()+" "+u.getUsername());
            }
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println("print user profile");
        UserProfileRepository u = new UserProfileRepository();
        UserProfile user = UserProfile.builder().gender(1).major("CS").age(23).year("1996").username("Jingkuan").build();
        u.save(user);
        Optional<UserProfile> opt = u.findUserByName("Jingkuan");
        opt = u.findUserById(1);
        u.allUsers();
    }
}
