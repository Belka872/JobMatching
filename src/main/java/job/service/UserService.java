package job.service;

import job.domain.User;

import java.util.List;

public class UserService {
    private final StorageService storageService;

    public UserService(StorageService storageService) {
        this.storageService = storageService;
    }

    public void createUser(String name, List<String> skills, int experience) {
        storageService.addUser(new User(name, skills, experience));
    }

    public List<User> getUsers() {
        return storageService.getUsers();
    }

}
