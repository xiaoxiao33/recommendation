package com.se.repository;

import com.se.model.UserImage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.sql.SQLFeatureNotSupportedException;

@Repository
public class UserImageRepositoryImpl{

    public void save(UserImage s) {
        SessionFactory factory=null;
        try {
            factory = new Configuration().configure().buildSessionFactory();
            throw new SQLFeatureNotSupportedException();
        }catch (Exception e){
        }

        Transaction transaction = null;
        try (Session session = factory.openSession()) {

            transaction = session.beginTransaction();


            session.save(s);


            transaction.commit();
            session.close();
            factory.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public UserImage updateImage(UserImage userImage){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<UserImage> update = builder.createCriteriaUpdate(UserImage.class);
            Root root = update.from(UserImage.class);


            // update.set("username", user.getUsername());
            update.set("image", userImage.getImage());
            update.where(builder.equal(root.get("id"), userImage.getId()));
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
        return userImage;
    }
}
