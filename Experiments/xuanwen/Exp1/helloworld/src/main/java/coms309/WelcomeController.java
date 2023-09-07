package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name){
        return "Hi, " + name + "! Welcome to helloworld program";
    }
}
