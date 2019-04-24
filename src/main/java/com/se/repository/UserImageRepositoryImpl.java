package com.se.repository;

import com.se.model.UserImage;
import com.se.model.UserProfile;
import com.se.repository.UserImageRepository;
import com.se.service.PasswordSecurityService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

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
}
