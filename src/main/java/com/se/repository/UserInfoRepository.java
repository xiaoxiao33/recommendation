package com.se.repository;

import com.se.model.UserInfo;
import com.se.Model.UserProfile;
import com.se.service.PasswordSecurityService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserInfoRepository {

    // Save a user's info

    public UserInfo saveInfo(UserInfo user) {
        SessionFactory factory=null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
            throw new SQLFeatureNotSupportedException();
        }catch (Exception e){
        }

        Transaction transaction = null;
        try (Session session = factory.openSession()) {

            transaction = session.beginTransaction();

            user.setPassword(PasswordSecurityService.hashPassword(user.getPassword()));

            if (findInfoByEmail(user.getEmail()).orElse(null)!=null){
                System.out.println("The duplicate exists");
                return user;
            }
            session.save(user);

            UserProfile profile = new UserProfile(user);
            session.save(profile);

            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return user;
    }

    public List<UserInfo> findAllInfo() {
        SessionFactory factory=null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
            throw new SQLFeatureNotSupportedException();
        }catch (SQLFeatureNotSupportedException e){

        }
        List<UserInfo> result = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserInfo> query = builder.createQuery(UserInfo.class);
            Root<UserInfo> root = query.from(UserInfo.class);
            query.select(root);
            Query<UserInfo> q = session.createQuery(query);
            result = q.list();
            for(UserInfo u: result){
                System.out.println(u.getId()+" "+u.getEmail()+" "+u.getPassword());
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


    // deprecated as username is moved to userprofile
    /*
    public Optional<UserInfo> findInfoByUsername(final String username) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Optional<UserInfo> opt = null;

        Transaction transaction = null;
        try {
            Session session = factory.openSession();
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserInfo> query = builder.createQuery(UserInfo.class);
            Root<UserInfo> root = query.from(UserInfo.class);
            query.select(root).where(builder.equal(root.get("username"), username));
            Query<UserInfo> q = session.createQuery(query);
            //avoid exception, set max results as 1

            List<UserInfo> result = q.getResultList();
            UserInfo userInfo = null;
            if (!result.isEmpty()) {
                userInfo = result.get(0);
            }

            //System.out.println(user.getId()+user.getUsername()+user.getPassword());
            opt = Optional.ofNullable(userInfo);
            transaction.commit();

        }catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return opt;
    }*/

    public Optional<UserInfo> findInfoByEmail(final String email) {
        SessionFactory factory=null;
        factory = new Configuration().configure().buildSessionFactory();

        Optional<UserInfo> opt = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserInfo> query = builder.createQuery(UserInfo.class);
            Root<UserInfo> root = query.from(UserInfo.class);
            query.select(root).where(builder.equal(root.get("email"), email));
            Query<UserInfo> q = session.createQuery(query);
            //avoid exception, set max results as 1

            List<UserInfo> result = q.getResultList();
            UserInfo userInfo = null;
            if (!result.isEmpty()) {
                userInfo = result.get(0);
            }
            // System.out.println(user.getId()+user.getUsername()+user.getPassword());
            opt = Optional.ofNullable(userInfo);
            transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return opt;
    }

    public Optional<UserInfo > findInfoById(int id) {
        SessionFactory factory=null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
            throw new SQLFeatureNotSupportedException();
        }catch (SQLFeatureNotSupportedException e){

        }
        Optional<UserInfo> opt = null;

        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserInfo> query = builder.createQuery(UserInfo.class);
            Root<UserInfo> root = query.from(UserInfo.class);
            query.select(root).where(builder.equal(root.get("id"), id));
            Query<UserInfo> q = session.createQuery(query);
            //avoid exception, set max results as 1
            List<UserInfo> result = q.getResultList();
            UserInfo userInfo = null;
            if (!result.isEmpty()) {
                userInfo = result.get(0);
            }
            //System.out.println(user.getId()+user.getUsername()+": Get by Id");
            opt = Optional.ofNullable(userInfo);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return opt;
    }

    public UserInfo updateInfo(UserInfo user){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<UserInfo> update = builder.createCriteriaUpdate(UserInfo.class);
            Root root = update.from(UserInfo.class);


            // update.set("username", user.getUsername());
            update.set("email", user.getEmail());
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

    public static void main(String[] args){
        System.out.println("print user info");
        UserInfoRepository u = new UserInfoRepository();
        // UserInfo user = UserInfo.builder().username("hahh").password("woshidalao").email("haaa@yale.edu").build();
        UserInfo user = UserInfo.builder().password("woshidalao").email("haaaaa@yale.edu").build();

        u.saveInfo(user);
        user = UserInfo.builder().password("wobushidalao").email("fsaaae@yale.edu").build();
        u.saveInfo(user);
        // Optional<UserInfo> opt = u.findInfoByUsername("lala");
        // u.findAllInfo();
    }

}
