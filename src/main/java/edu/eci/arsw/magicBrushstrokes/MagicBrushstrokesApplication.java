package edu.eci.arsw.magicBrushstrokes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import edu.eci.arsw.magicBrushstrokes.model.*;

@SpringBootApplication
public class MagicBrushstrokesApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(MagicBrushstrokesApplication.class, args);
	}
}
