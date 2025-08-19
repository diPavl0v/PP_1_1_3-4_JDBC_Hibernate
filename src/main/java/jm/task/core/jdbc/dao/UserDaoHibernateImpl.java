package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sf = Util.getSessionFactory();

    public UserDaoHibernateImpl () { }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ( "  +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45), " +
                "lastName VARCHAR(45), " +
                "age TINYINT)";
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Ошибка при удалении таблицы: " + e.getMessage());

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Ошибка при сохранении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            User u = session.get(User.class, id);
            if (u != null) session.delete(u);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Ошибка при удалении пользователя: "  + e.getMessage());

        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            System.out.println("Ошибка при получении пользователей: "  + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
        }
    }
}
