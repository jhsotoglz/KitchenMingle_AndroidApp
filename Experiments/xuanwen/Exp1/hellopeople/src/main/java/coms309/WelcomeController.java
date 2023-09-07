package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309 from hellopeople_WelcomeController";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello " + name + " and welcome to COMS 309 from hellopeople_WelcomeController";
    }
}
