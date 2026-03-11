package ru.movie.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.movie.console.CommandProcessor;

import java.util.Scanner;

@Configuration
public class ConsoleConfig {

    @Autowired
    private CommandProcessor commandProcessor;

    @Bean
    public CommandLineRunner commandScanner() {
        return args -> {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Enter command. 'exit' to quit.");
                System.out.println("Commands: create <id> <title> <genre> <year>");
                System.out.println("          find <id>");
                System.out.println("          update <id> <title> <genre> <year>");
                System.out.println("          delete <id>");
                System.out.println("          list");
                while (true) {
                    System.out.print("> ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input.trim())) {
                        System.out.println("Exiting...");
                        break;
                    }
                    commandProcessor.processCommand(input);
                }
            }
        };
    }
}
