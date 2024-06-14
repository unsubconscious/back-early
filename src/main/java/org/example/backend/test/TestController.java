package org.example.backend.test;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
//@RequiredArgsConstructor
public class TestController {

//    @Autowired
//    TestService testService;


    @GetMapping("/hello")
    public String test(){

        return "hello";
    }
//    @GetMapping("/hello")
//    public List<User> test(){
//        testService.findAllUsers();
//        return testService.findAllUsers();
//    }

//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return testService.findUserById(id);
//    }
}
