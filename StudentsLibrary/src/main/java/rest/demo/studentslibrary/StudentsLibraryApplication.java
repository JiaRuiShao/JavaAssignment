package rest.demo.studentslibrary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StudentsLibraryApplication implements CommandLineRunner {

    @Autowired
    ApplicationContext ac;

    public static void main(String[] args) {
        SpringApplication.run(StudentsLibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // test service here
    }
}
