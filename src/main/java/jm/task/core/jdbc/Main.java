package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        // Создание таблицы
        userService.createUsersTable();

        // Добавление пользователей
        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        System.out.println("User с именем – Ivan добавлен в базу данных");

        userService.saveUser("Petr", "Petrov", (byte) 30);
        System.out.println("User с именем – Petr добавлен в базу данных");

        userService.saveUser("Sidor", "Sidorov", (byte) 35);
        System.out.println("User с именем – Sidor добавлен в базу данных");

        userService.saveUser("Anna", "Annova", (byte) 20);
        System.out.println("User с именем – Anna добавлен в базу данных");

        // Получение пользователей
        List<User> users = userService.getAllUsers();
        for (User u : users) {
            System.out.println(u);
        }

        // Очистка
        userService.cleanUsersTable();

        // Удаление
        userService.dropUsersTable();
    }
}
