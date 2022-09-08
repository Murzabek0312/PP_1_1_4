package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {
    // реализуйте настройку соеденения с БД
    private final static String URL = "jdbc:mysql://localhost:3306/katapp";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private static Connection connection;


       public static Connection getConnection(){
            try {
                Driver driver = new com.mysql.cj.jdbc.Driver();
                DriverManager.registerDriver(driver);
                connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
               // System.out.println("connection ok");
            } catch (SQLException e) {
               // System.out.println("connection NOT ok");
                throw new RuntimeException(e);

            }
       return connection;}

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/katapp?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}


