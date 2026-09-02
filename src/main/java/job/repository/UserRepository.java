package job.repository;

import job.domain.User;

import java.util.List;

public interface UserRepository {
    void addUser(User user);

    List<User> getAllUsers();

    User getUser(String name);
}
