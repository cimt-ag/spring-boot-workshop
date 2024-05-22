package de.cimtag.rateyourbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Rate Your Books application.
 * <p>
 * This class contains the main method which starts the Spring Boot application using SpringApplication.run.
 * </p>
 *
 * @author Niklas Witzel
 */
@SpringBootApplication
public class RateYourBooksApplication {

  public static void main(String[] args) {
    SpringApplication.run(RateYourBooksApplication.class, args);
  }

}
