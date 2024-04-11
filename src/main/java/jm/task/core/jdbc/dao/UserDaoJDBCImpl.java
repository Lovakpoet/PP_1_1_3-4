package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
        public UserDaoJDBCImpl() {
            // The empty constructor is specified in the task
        }
        private static final Logger log = Logger.getLogger(UserDaoJDBCImpl.class.getName());
        private final Connection connection = Util.getConnection();

        public void createUsersTable() {

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users (
                      id INT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(45) NOT NULL,
                      lastName VARCHAR(45) NOT NULL,
                      age INT NOT NULL,
                      PRIMARY KEY (id))
                    """);
                log.info( "CREATE TABLE OK!");

            } catch (SQLException e) {
                log.warning(e + ": (CREATE TABLE FAIL!)");

            }

        }

        public void dropUsersTable() {

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS users");

                log.info( "DROP TABLE OK!");

            } catch (SQLException e) {
               log.warning(e + ": (DROP TABLE FAIL!)");
            }

        }

        public void saveUser(String name, String lastName, byte age) {

            String regUser = "INSERT INTO users (name, lastName, age) values (?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(regUser)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();

                log.info("SAVE USER OK!");

            } catch (SQLException e) {
                log.warning(e + ": (SAVE USER FAIL!)");
            }

        }


        public void removeUserById(long id) {
            String removeID = "DELETE FROM users where id=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(removeID)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();

                log.info( "DELETE USER OK!");

            } catch (SQLException e) {
                log.warning(e + ": (DELETE USER FAIL!)");
            }

        }

        public List<User> getAllUsers() {
            List<User> userList = new ArrayList<>();
            String getAll = "SELECT * FROM users";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(getAll)) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));

                    userList.add(user);
                    log.info( "GET ALL USERS OK!");
                }


            } catch (SQLException e) {
                log.warning(e + ": (GET ALL USERS FAIL!)");
            }
            return userList;
        }

        public void cleanUsersTable() {

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("TRUNCATE TABLE users");
                log.info( "CLEANING TABLE OK");

            } catch (SQLException e) {
                log.warning(e + ": (CLEANING TABLE FAIL!)");
            }

        }
}