package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EchoController {
    @GetMapping("/echo/{message}")
    public String echo(@PathVariable String message){
        return "You are in a testing page for GET controller " + message;
    }

    @PostMapping("/echo")
    public String echo(){
        return null; //TODO
    }
}
