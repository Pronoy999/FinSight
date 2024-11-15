import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SuppressWarnings("SpringBootApplicationSetup")
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.fin.sight.database", "com.fin.sight.api.controller"})
public class FinSightApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinSightApplication.class, args);
        log.info("FinSight Application Started");
    }
}
