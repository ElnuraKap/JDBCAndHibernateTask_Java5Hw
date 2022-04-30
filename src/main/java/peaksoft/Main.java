package peaksoft;

import peaksoft.model.User;
import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        User user = new User("Aisanat", "Kolbaieva", (byte) 23);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());

        User user1 = new User("Begimai", "Kaibalieva", (byte) 22);
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());

        User user2 = new User("Alymkul", "Jolomaniv", (byte) 25);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        User user3 = new User("Elnura", "Kaparova", (byte) 22);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        List<User> userList = userService.getAllUsers();
        for (User users : userList) {
            System.out.println(users);
        }
        userService.removeUserById(2);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        userService.existsByFirstName(user1.getName());
    }
}

