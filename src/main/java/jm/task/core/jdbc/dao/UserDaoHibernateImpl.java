package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sun.security.smartcardio.SunPCSC;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sF = Util.getFactory();
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {

         Session session = sF.openSession();
         session.beginTransaction();
         session.createSQLQuery("CREATE TABLE users " +
                 "(id INTEGER not NULL auto_increment primary key, " +
                 "name VARCHAR(255) not null , " +
                 "lastName VARCHAR(255) not null , " +
                 "age INTEGER)");
         session.getTransaction().commit();
         session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sF.openSession();
        session.beginTransaction();
        session.createSQLQuery("drop table User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = sF.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sF.openSession();
        session.beginTransaction();
        session.createQuery("delete from User where id =" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public List<User> getAllUsers() {
        Session session = sF.openSession();
        session.beginTransaction();
        List<User> userList = session.createQuery("from User", User.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sF.openSession();
        session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
