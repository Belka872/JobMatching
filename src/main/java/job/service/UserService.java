package job.service;

import job.domain.User;
import job.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String name, List<String> skills, int experience) {
        List<String> normalizedSkills = skills.stream()
                .distinct()
                .sorted()
                .toList();
        userRepository.addUser(new User(name, normalizedSkills, experience));
    }

    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

}
