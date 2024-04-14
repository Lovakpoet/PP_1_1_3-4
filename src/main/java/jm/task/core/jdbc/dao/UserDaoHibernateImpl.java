package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
        //was already in the template
    }

    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public void createUsersTable() {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            String sqlQuery = """
                    CREATE TABLE IF NOT EXISTS users (
                      id INT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(45) NOT NULL,
                      lastName VARCHAR(45) NOT NULL,
                      age INT NOT NULL,
                      PRIMARY KEY (id))
                    """;
            session.createSQLQuery(sqlQuery).executeUpdate();
            session.getTransaction().commit();
            log.info( "CREATE TABLE OK!");
        } catch (Exception e) {
            log.warning(e + ": (CREATE TABLE FAIL!)");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
            log.info( "DROP TABLE OK!");
        } catch (Exception e){
            log.warning(e + ": (DROP TABLE FAIL!)");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            log.info("SAVE USER OK!");
        } catch (Exception e){
            log.warning(e + ": (SAVE USER FAIL!)");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM users where id = " + id).executeUpdate();
            session.getTransaction().commit();
            log.info( "DELETE USER OK!");
        } catch (Exception e){
            log.warning(e + ": (DELETE USER FAIL!)");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            log.info( "GET ALL USERS OK!");
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            session.getTransaction().commit();
            log.info( "CLEANING TABLE OK");
        } catch (Exception e){
            log.warning(e + ": (CLEANING TABLE FAIL!)");

        }
    }
}