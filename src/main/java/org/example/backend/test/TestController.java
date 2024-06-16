package org.example.backend.test;

import lombok.RequiredArgsConstructor;
import org.example.backend.user.dto.CustomUser;
import org.example.backend.user.dto.User;
import org.example.backend.user.security.jwt.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
//@RequiredArgsConstructor
public class TestController {

//    @Autowired
//    TestService testService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/userinfo")
    public User getUserInfo(@RequestHeader("Authorization") String authHeader) {
        Authentication authentication = jwtTokenProvider.getAuthentication(authHeader);

        if (authentication == null) {
            throw new RuntimeException("Invalid JWT token");
        }

        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return customUser.getUser();
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
