package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetEchoController {
    @GetMapping("/echo")
    public String echo(){
        return "You are in a testing page for GET controller";
    }
}
