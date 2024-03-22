package co.edu.eam.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PrestamoApp {

    public static void main(String[] args) {
        SpringApplication.run(PrestamoApp.class, args);
    }

}
