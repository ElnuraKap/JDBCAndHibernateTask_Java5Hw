package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
   private Connection connection;


    public UserDaoJdbcImpl() {

        try {
            connection = new Util().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        String query = """
                create table if not exists users(
                id serial primary key ,
                name varchar (150) not null,
                last_name varchar (150) not null,
                age integer );
                """;
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            System.out.println("Create table!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String query = "drop table if exists users;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("Drop table!");
            } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void saveUser(String name, String lastName, byte age) {
        String query = """
                insert into users (name,last_name,age) 
                values (?,?,?);
                """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3,age);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
            System.out.println("Delete from users! ");


    } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public List<User> getAllUsers() {
        String query = "select * from users;";
        List<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge((byte) resultSet.getInt("age"));
                userList.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

        public void cleanUsersTable() {
        String query = "truncate users ;";
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("You have successfully delete all users in table user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean existsByFirstName(String firstName) {
        String query = """
                select count(*) as quantity from users 
                where name = ?
                """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            int quantity = resultSet.getInt(1);

            if (quantity > 0) {
                return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // eger databasede parametrine kelgen firstnamege okshosh adam bar bolso
        // anda true kaitarsyn
        // jok bolso anda false kaitarsyn.
        return false;
    }
}