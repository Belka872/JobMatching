package job.repository.inMemory;

import job.domain.User;
import job.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new LinkedHashMap<>();

    @Override
    public void addUser(User user) {
        users.putIfAbsent(user.getName(), user);
    }

    @Override
    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }

    @Override
    public User getUser(String name) {
        return users.get(name);
    }
}
