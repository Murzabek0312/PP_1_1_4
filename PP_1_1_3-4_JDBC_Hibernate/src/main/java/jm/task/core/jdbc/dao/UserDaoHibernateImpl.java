package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;
        String create = "create table if not exists UsersTable(\n" +
                "                id int AUTO_INCREMENT,\n" +
                "                primary key (id),\n" +
                "                name varchar(45),\n" +
                "                lastname varchar(45),\n" +
                "                age ints\n" +
                "                );";
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(create);
            session.getTransaction().commit();
        } catch (HibernateException ex){
            ex.printStackTrace();
        }
        finally {
            if(session != null){
            session.close();}
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("drop table if exists UsersTable;").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        finally {
            if(session != null){
            session.close();}
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException ex){
            ex.printStackTrace();}
        finally {
            if(session != null){
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User where id=" + id).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex){
            ex.printStackTrace();}
        finally {
            if(session != null){
            session.close();}
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List <User> users = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            for (User u : users) {
                System.out.println(u);
            }
            session.getTransaction().commit();
            return users;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex){
            ex.printStackTrace();}
        finally {
            if(session != null){
            session.close();}
        }
    }
}
