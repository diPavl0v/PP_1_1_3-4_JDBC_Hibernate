package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/kata?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root0013";
    //JDBC
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Hibernate
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties props = new Properties();
            props.put("hibernate.connection.url", URL);
            props.put("hibernate.connection.username", USER);
            props.put("hibernate.connection.password", PASS);
            props.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            props.put("hibernate.show_sql", "false");
            props.put("hibernate.format_sql", "true");
            props.put("hibernate.hbm2ddl.auto", "none"); // таблицы создаём/удаляем сами

            Configuration cfg = new Configuration();
            cfg.setProperties(props);
            cfg.addAnnotatedClass(User.class);

            ServiceRegistry sr = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();
            return cfg.buildSessionFactory(sr);
        } catch (Exception e) {
            System.out.println("Ошибка инициализации SessionFactory: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}