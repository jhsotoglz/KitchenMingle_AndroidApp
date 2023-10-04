package backendLogin.roundTrip.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/")
    public String index() {
        return "Greetings from Kitchen Mingle - Users";
    }

}
