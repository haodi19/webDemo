package cn.database;

import cn.domain.Page;
import cn.domain.User;
import cn.util.JDBCUtils;
import cn.util.JDBCUtils_Druid;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class UserServiceImpl implements UserService {

    @Override
    public User login(User loginUser) {
        ResultSet rs = null;
        try (Connection con = JDBCUtils_Druid.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("select * from user_inf where username=? and password=?");
        ) {
            preparedStatement.setString(1, loginUser.getUsername());
            preparedStatement.setString(2, loginUser.getPassword());
            rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4).equals("male") ? User.Gender.MALE : User.Gender.FEMALE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public boolean register(User registerUser) {
        try (Connection con = JDBCUtils_Druid.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("insert into user_inf (username,password,gender) values (?,?,?)");
        ) {
            preparedStatement.setString(1, registerUser.getUsername());
            preparedStatement.setString(2, registerUser.getPassword());
            preparedStatement.setString(3, registerUser.getGender().name().toLowerCase(Locale.ROOT));
            int flag = preparedStatement.executeUpdate();
            return flag == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        try (Connection con = JDBCUtils_Druid.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("delete from user_inf where id=?");
        ) {
            preparedStatement.setInt(1, id);
            int flag = preparedStatement.executeUpdate();
            return flag == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateUser(User user) {
        try (Connection con = JDBCUtils_Druid.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("update user_inf set password=?,gender=? where id=?");
        ) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getGender().name().toLowerCase(Locale.ROOT));
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        ResultSet rs = null;
        try (Connection con = JDBCUtils_Druid.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("select * from user_inf");
        ) {
            rs = preparedStatement.executeQuery();
            List<User> allUsers = new ArrayList<>();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), "male".equals(rs.getString(4)) ? User.Gender.MALE : User.Gender.FEMALE);
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public Page<User> getUsersWithPaging(int currentPage, int itemsPerPage, Map<String, String[]> condition) {
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        Page<User> userPage = new Page<>();
        userPage.setCurrentPage(currentPage);
        userPage.setItemsPerPages(itemsPerPage);
        StringBuilder sql1 = new StringBuilder("select count(*) from user_inf where 1=1");
        StringBuilder sql2 = new StringBuilder("select * from user_inf where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (condition != null) {
            Set<Map.Entry<String, String[]>> entries = condition.entrySet();
            for (Map.Entry<String, String[]> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue()[0];
                if (value == null || "".equals(value)) {
                    continue;
                }
                switch (key) {
                    case "currentPage":
                    case "delete_id":
                    case "update_id":
                    case "update_username":
                    case "update_password":
                    case "update_gender":
                    case "r_password":
                    case "itemsPerPage":
                        continue;
                    case "gender":
                        if ("x".equals(value)) {
                            continue;
                        }
                        sql1.append(" and ").append(key).append(" = ? ");
                        sql2.append(" and ").append(key).append(" = ? ");
                        params.add(value);
                        break;
                    case "id":
                        sql1.append(" and ").append(key).append("::varchar ").append(" = ? ");
                        sql2.append(" and ").append(key).append("::varchar ").append(" = ? ");
                        params.add(value);
                        break;
                    default:
                        sql1.append(" and ").append(key).append(" like ?  escape '\\'");
                        sql2.append(" and ").append(key).append(" like ?  escape '\\'");
                        value = value.replace("_", "\\_");
                        params.add("%" + value + "%");
                }
            }
        }
        sql2.append(" order by id limit ? offset ?");
        try (Connection con = JDBCUtils_Druid.getConnection();
             PreparedStatement preparedStatement1 = con.prepareStatement(sql1.toString());
             PreparedStatement preparedStatement2 = con.prepareStatement(sql2.toString());
        ) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement1.setString(i + 1, (String) params.get(i));
                preparedStatement2.setString(i + 1, (String) params.get(i));
            }
            rs1 = preparedStatement1.executeQuery();
            rs1.next();
            userPage.setTotalItems(rs1.getInt(1));
            userPage.setTotalPages((int) Math.ceil((double) userPage.getTotalItems() / itemsPerPage));
            preparedStatement2.setInt(params.size() + 1, itemsPerPage);
            preparedStatement2.setInt(params.size() + 2, (currentPage - 1) * itemsPerPage);
            rs2 = preparedStatement2.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs2.next()) {
                User user = new User(rs2.getInt(1), rs2.getString(2), rs2.getString(3), "male".equals(rs2.getString(4)) ? User.Gender.MALE : User.Gender.FEMALE);
                users.add(user);
            }
            userPage.setItemsInThisPage(users);
            return userPage;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }


}
