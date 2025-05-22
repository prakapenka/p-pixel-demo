package localhost.ppixeldemo;

import org.springframework.boot.SpringApplication;

public class TestPpixelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(PpixelDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
