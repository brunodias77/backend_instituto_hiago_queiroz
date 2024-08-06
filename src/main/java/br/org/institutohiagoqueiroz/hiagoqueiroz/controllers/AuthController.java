package br.org.institutohiagoqueiroz.hiagoqueiroz.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @GetMapping("/register")
    public String register()
    {
        return "Hello World";
    }

}
