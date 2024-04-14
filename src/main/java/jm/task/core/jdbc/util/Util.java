package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    private Util() {}
    // Настройка соеденения с БД
    static Logger log = Logger.getLogger(Util.class.getName());

    private static final String DB_URL = "jdbc:mysql://localhost:3306/PP";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "dazvazkamaz1";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            if (!connection.isClosed()) {
                log.info("CONNECTED OK!");
            }
        } catch (SQLException e) {
            log.warning(e + ": (CONNECTED FAIL! ");
        }
        return connection;
    }

    public static final SessionFactory sessionFactory = new Configuration()
            .addProperties(getPropertiesSessionFactory())
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    private static Properties getPropertiesSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", DB_URL);
        properties.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.connection.username", DB_USERNAME);
        properties.setProperty("hibernate.connection.password", DB_PASSWORD);
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        return properties;
    }


}
