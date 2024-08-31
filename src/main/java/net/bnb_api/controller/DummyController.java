package net.bnb_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {
    
    @GetMapping
    public String getMessage(){
        return "Namaste Duniya!!";
    }
}
