package com.huaylupo.spmia.ch01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping(value = "hello")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping(value = "/{firstName}")
    public String helloGET(
            @PathVariable("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        return String.format("{\"message\":\"Hello %s %s\"}", firstName, lastName);
    }

    @PostMapping
    public String helloPOST(@RequestBody HelloRequest request) {
        return String.format("{\"message\":\"Hello %s %s\"}", request.firstName(), request.lastName());
    }
}

record HelloRequest(
        String firstName,
        String lastName
) {
}