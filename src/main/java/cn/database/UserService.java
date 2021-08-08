package cn.database;

import cn.domain.Page;
import cn.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(User loginUser);

    boolean register(User registerUser);

    boolean deleteUser(int id);

    void updateUser(User user);

    List<User> getAllUsers();

    Page<User> getUsersWithPaging(int currentPage, int itemsPerPage, Map<String, String[]> condition);
}
