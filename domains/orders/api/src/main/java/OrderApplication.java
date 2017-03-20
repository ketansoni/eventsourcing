import org.axonframework.contextsupport.spring.AnnotationDriven;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by nikeshshetty on 3/15/17.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.ketan.ecom")
@AnnotationDriven
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(OrderApplication.class);
        springApplication.run(args);
    }
}
