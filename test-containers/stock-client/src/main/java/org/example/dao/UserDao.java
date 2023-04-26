package org.example.dao;

import org.example.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {
    private final Map<Integer, User> usersMap;

    public UserDao(Map<Integer, User> userById) {
        this.usersMap = userById;
    }

    public void create(User user) {
        usersMap.put(user.getUserId(), user);
    }

    public User read(int userId) {
        return usersMap.get(userId);
    }

    public List<User> readAll() {
        return new ArrayList<>(usersMap.values());
    }

    public void delete(int userId) {
        usersMap.remove(userId);
    }
}
