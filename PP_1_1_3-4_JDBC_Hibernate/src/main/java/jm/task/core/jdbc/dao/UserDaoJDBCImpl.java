package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        String creat = """
                create table if not exists UsersTable(
                id int AUTO_INCREMENT,
                primary key (id),
                name varchar(45),
                lastname varchar(45),
                age int\s
                );""";
        //Statement statement;
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(creat);
            preparedStatement.executeUpdate();
          //  statement = Util.getConnection().createStatement();
           // statement.execute(creat);
        } catch (SQLException e) {
            System.out.println("не удалось создать таблицу");
            e.printStackTrace();
        }
    }
    public void dropUsersTable() {
        String drop = "drop table if exists UsersTable;";
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(drop);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;
        String saveUser = "insert into UsersTable (name, lastName, age) values (?,?,?);";
        try {
            preparedStatement = connection.prepareStatement(saveUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        String removeUserById = "delete from UsersTable where id =?;";
        Connection connection = null;
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement(removeUserById);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List<User> getAllUsers() {
        String allUsers = "select * from UsersTable;";
        //Statement statement;
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement;
        List<User> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(allUsers);
            preparedStatement.executeQuery();
            //statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = Util.getConnection();
            ResultSet resultSet = preparedStatement.executeQuery(allUsers);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge((byte) resultSet.getInt("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (User u : list) {
            System.out.println(u);
        }
        return list;
    }

    public void cleanUsersTable() {
        String clean = "delete from UsersTable;";
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(clean);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("не удалось очистить таблицу, коннект");
            throw new RuntimeException(e);
        }

        try {
            preparedStatement.execute(clean);
        } catch (SQLException e) {
            System.out.println("не удалось очистить таблицу");
            throw new RuntimeException(e);
        } finally {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }

    }
}
