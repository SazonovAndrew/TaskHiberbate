package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final static SessionFactory sF = Util.getFactory();
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {

         Session session = sF.openSession();
         session.beginTransaction();
         session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                 "(id INTEGER not NULL auto_increment primary key, " +
                 "name VARCHAR(255) not null , " +
                 "lastName VARCHAR(255) not null , " +
                 "age INTEGER)").executeUpdate();
         session.getTransaction().commit();
        System.out.println("Таблица создана");
         session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sF.openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();
        System.out.println("Таблица не существует.");
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sF.openSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        System.out.println("Пользователь сохранент в БД");
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sF.openSession();
        session.beginTransaction();
        User user =  session.load(User.class, id);

        if (user != null) {
            session.delete(user);
            System.out.println("User удален.");
        }
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
        try{
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица была очищена");
        }catch (Exception e){
            System.out.println("Таблица не очищена");
        }finally {
            session.close();
        }
    }
}
