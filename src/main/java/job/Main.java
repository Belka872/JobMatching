package job;

import job.service.*;
import job.domain.User;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    static void main() {
        StorageService storageService = new StorageService();
        MatchService matchService = new MatchService(storageService);
        FileService fileService = new FileService("history.txt");
        UserService userService = new UserService(storageService);
        VacancyService vacancyService = new VacancyService(storageService);
        CommandService commandService = new CommandService(matchService, fileService, userService, vacancyService);
        commandService.processCommand();

        FindBestJob findBestJob = new FindBestJob(userService, matchService);
        Scanner scanner = new Scanner(System.in);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(findBestJob, 0, 5, TimeUnit.SECONDS);

        try {
            while (true) {
                String input = scanner.nextLine();
                if (input.equals("exit")) {
                    break;
                }
                commandService.handle(input);
            }
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

}
