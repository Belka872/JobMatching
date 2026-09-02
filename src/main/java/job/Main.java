package job;

import job.repository.InMemoryJobRepository;
import job.repository.InMemoryUserRepository;
import job.repository.JobRepository;
import job.repository.UserRepository;
import job.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class Main implements CommandLineRunner {
    private final CommandService commandService;

    @Override
    public void run(String... args) throws Exception {
        commandService.processCommand();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if ("exit".equals(input)) {
                break;
            }

            commandService.handle(input);
        }
    }
}
