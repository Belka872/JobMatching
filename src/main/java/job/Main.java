package job;

import job.service.*;
import job.domain.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() {
        StorageService storageService = new StorageService();
        MatchService matchService = new MatchService(storageService);
        FileService fileService = new FileService("history.txt");
        UserService userService = new UserService(storageService);
        VacancyService vacancyService = new VacancyService(storageService);
        CommandService commandService = new CommandService(matchService, fileService, userService, vacancyService);
        commandService.processCommand();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            commandService.handle(input);
        }
    }

}
