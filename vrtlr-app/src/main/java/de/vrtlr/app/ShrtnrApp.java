package de.vrtlr.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages={"de.vrtlr"})
@EnableScheduling
public class ShrtnrApp {

	public static void main(String[] args) throws Exception {
	    SpringApplication.run(ShrtnrApp.class, args);
	}

}
