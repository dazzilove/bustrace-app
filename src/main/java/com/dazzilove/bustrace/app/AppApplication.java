package com.dazzilove.bustrace.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
