package com.darakay.micro689.sources;

import com.darakay.micro689.dto.LogupRequest;
import com.darakay.micro689.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class LoginResource {

    private final UserService userService;

    public LoginResource(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/logup")
    public ResponseEntity logUp(@RequestBody LogupRequest request){
        int userId = userService.logUp(request);
        return ResponseEntity.ok().build();
    }
}
