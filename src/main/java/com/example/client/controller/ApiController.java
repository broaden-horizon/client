package com.example.client.controller;

import com.example.client.dto.Req;
import com.example.client.dto.UserDto;
import com.example.client.service.RestTemplateService;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@JsonNaming(value= PropertyNamingStrategies.SnakeCaseStrategy.class)
@RestController
@RequestMapping("/api")
public class ApiController {
    private final RestTemplateService restTemplateService;

    public ApiController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping("/client/hello")
    public UserDto hello(UserDto user) {
       return restTemplateService.hello();
    }

    @GetMapping("/client/bye")
    public UserDto bye(UserDto user) {
        System.out.println(user.getPhoneNumber());
        return restTemplateService.bye(user);
    }

    @GetMapping("/client/post")
    public Req<UserDto> post(UserDto user) {
        System.out.println("Mapping complete");
        return restTemplateService.genericExchange(user);
    }


}
