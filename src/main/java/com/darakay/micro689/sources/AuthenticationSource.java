package com.darakay.micro689.sources;

import com.darakay.micro689.dto.LogupRequest;
import com.darakay.micro689.services.UserService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/v1/")
@Api("Аутентификация")
public class AuthenticationSource {

    private final UserService userService;

    public AuthenticationSource(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Регистрация нового пользователя")
    @PostMapping("/logup")
    @CrossOrigin(value = "*", methods = {OPTIONS, POST})
    public ResponseEntity logUp(@ApiParam(value = "Запрос на регистрацию") @RequestBody LogupRequest request){
        int userId = userService.logUp(request);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Метод для подтверждения логина и пароля")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Пользователь залогинен"),
            @ApiResponse(code = 401, message = "Не верный логин или пароль"),
    })
    @GetMapping("/login")
    @CrossOrigin(value = "*", methods = {OPTIONS, GET}, allowedHeaders = {"Authorization"})
    public ResponseEntity logIn(){
        return ResponseEntity.ok().build();
    }
}
