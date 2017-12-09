package pl.kohutmariusz.configurationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableConfigServer
@SpringBootApplication
@EnableEurekaClient
public class ConfigurationServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConfigurationServiceApplication.class, args);
    }

}
