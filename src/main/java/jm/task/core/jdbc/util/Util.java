package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static SessionFactory sessionFactory;

    private  Util() {
    }


    public Connection getConnection() {
        Connection connection = null;
        try {
            return  connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty(Environment.HBM2DDL_AUTO,"update");
        properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.setProperty(Environment.USER, "root");
        properties.setProperty(Environment.PASS, "root");
        properties.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/mydbtest");
        return properties;
    }


    public static SessionFactory getFactory(){

        if(sessionFactory == null){
           return sessionFactory = new Configuration()
                    .setProperties(getProperties())
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return  sessionFactory;
    }



//    public SessionFactory getFactory() {
//        SessionFactory factory;
//        return   factory = new Configuration()
//                .configure()
//                .addAnnotatedClass(User.class)
//                .buildSessionFactory();
//    }
}
