package com.skillmirror.backend.controller;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
public class HelloController
{
    @GetMapping("/hello")
    public String hello()
    {
        return "Hello from Java backend!";
    }
}
