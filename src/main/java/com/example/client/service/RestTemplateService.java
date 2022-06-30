package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.lang.reflect.ParameterizedType;
import java.net.URI;

@Service
public class RestTemplateService {

    public UserDto hello() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
//                .queryParam("id", "steve")
                .queryParam("email", "aa@naver.com")
                .queryParam("phoneNumber", "010-1111-1111")
                .encode()
                .build()
                .toUri();
        System.out.println(uri.toString());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> result = restTemplate.getForEntity(uri, UserDto.class);
        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());
        return result.getBody();
    }

    public UserDto bye(UserDto user) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/bye/name/{name}")
                .encode()
                .build()
                .expand("anan")
                .toUri();


        System.out.println(uri);
        System.out.println(user);
        System.out.println(user.getPhoneNumber());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserDto> result = restTemplate.postForEntity(uri, user, UserDto.class);
        System.out.println(result.getStatusCode());
        System.out.println(result.getHeaders());
        System.out.println(result.getBody());

        return result.getBody();
    }

    //http://localhost:9090/api/server/post/exchange/{name}/{userId}

    public UserDto postExchange(UserDto user) {
        //Create URI object
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/post/exchange/{name}/{userId}")
                .encode()
                .build()
                .expand("steve", "st119")
                .toUri();
        //Create Body as a object


        //Create Request Obeject including Header part of HTTP
        RequestEntity<UserDto> req = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "aaaa")
                .header("custom-header", "bbb")
                .body(user);

        //Create restTemplate Object
        RestTemplate restTemplate = new RestTemplate();

        //Communicate with restTemplate object, and restore the response in a ResponseEntity
        ResponseEntity<UserDto> response = restTemplate.exchange(req, UserDto.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        return response.getBody();
    }

    public Req<UserDto> genericExchange(UserDto userDto){
        //Create URI object
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/post/exchange/{name}/{userId}")
                .encode()
                .build()
                .expand("steve", "st119")
                .toUri();
        //Create Body as a object
        Req<UserDto> req = new Req<UserDto>();
        req.setHeader(new Req.Header());
        req.setBody(userDto);

        //Create Request Obeject including Header part of HTTP
        RequestEntity<Req<UserDto>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "aaaa")
                .header("custom-header", "bbb")
                .body(req);


        //Create restTemplate Object
        RestTemplate restTemplate = new RestTemplate();



        //Communicate with restTemplate object, and restore the response in a ResponseEntity
        ResponseEntity<Req<UserDto>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<UserDto>>(){});

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        return response.getBody();

    }
}
