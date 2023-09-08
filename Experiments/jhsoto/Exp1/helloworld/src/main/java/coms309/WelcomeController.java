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

    @GetMapping("/goodbye")
    public String goodbye() {
        return "Goodbye!";
    }

    @GetMapping("/goodbye/{name}")
    public String goodbye(@PathVariable String name){
        return "Goodbye " + name + ", thanks for visiting my helloworld project!";
    }
}
