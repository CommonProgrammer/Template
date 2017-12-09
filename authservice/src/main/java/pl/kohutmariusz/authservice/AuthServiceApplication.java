package pl.kohutmariusz.authservice;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EnableAsync
@EnableEurekaClient
public class AuthServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
    }
}
