package edu.eci.arsw.magicBrushstrokesApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.magicBrushstrokesApp"})
public class MagicBrushstrokesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicBrushstrokesApplication.class, args);
	}
}
