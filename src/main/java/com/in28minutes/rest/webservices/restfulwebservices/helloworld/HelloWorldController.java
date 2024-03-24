package com.in28minutes.rest.webservices.restfulwebservices.helloworld;


import org.springframework.web.bind.annotation.*;

//REST API를 노출한다.
@RestController
public class HelloWorldController {

    // /hello-world
    // return "Hello World"
    @GetMapping(path="/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    /*
    * /hello-world/path-variable/{name} name이 Path Parameters
    * */

    @GetMapping(path="/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){ //Path 매개변수와 이름 맞춰야한다.
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
