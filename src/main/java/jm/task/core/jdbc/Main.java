package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Johnnie", "Walker", (byte) 40);
        userService.saveUser("Jack", "Daniel", (byte) 30);
        userService.saveUser("John", "Jameson", (byte) 50);
        userService.saveUser("Jim", "Beam", (byte) 60);
        System.out.println(userService.getAllUsers());
        userService.removeUserById(3);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();



    }
}
