package coms309;

import coms309.people.PeopleController;
import coms309.people.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * Sample Spring Boot Application.
 * 
 * @author Vivek Bengre
 */

@SpringBootApplication
public class Application {
	
    public static void main(String[] args) throws Exception {
        //create new person
        Person p = new Person();
        p.setFirstName("Michelle");
        p.setLastName("Loo");
        p.setAddress("123 First Rd");
        p.setTelephone("123-456-7890");
        PeopleController pc = new PeopleController();
        pc.createPerson(p);

        Person p2 = new Person("Emma", "Green", "1 Second Ave", "234-567-1098");
        pc.createPerson(p2);

        SpringApplication.run(Application.class, args);
    }

}
