package com.in28minutes.rest.webservices.restfulwebservices.helloworld;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

//REST API를 노출한다.
@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

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

    /*
    * en - English
    * nl - Dutch
    * fr - French
    * de - Deutsch
    * */
    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        //locale은 Request 헤더에서 얻을 수 있다.
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
                //두번 째는 메시지를 대체할 변수(미정시 null) 세번쨰는 Default Message
        //return "Hello World V2";
    }

}
