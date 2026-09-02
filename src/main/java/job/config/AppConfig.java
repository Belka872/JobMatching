package job.config;

import job.service.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public FileService fileService() {
        return new FileService("history.txt");
    }
}
