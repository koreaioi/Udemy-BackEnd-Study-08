package com.in28minutes.rest.webservices.restfulwebservices.versioning;
// 버전 관리 실습 컨트롤러


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    /* ========= URL ============*/
    @GetMapping("/v1/person")
    public PersonV1 getFirstVersionOfPerson(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersionOfPerson(){
        return new PersonV2("Song", "Wooseok");
    }
    /* ========= Request Param ============*/

    // /person?version=1 가 넘어오는 특정 경우 아래 메서드 호출
    @GetMapping(path="/person", params = "version=1")
    public PersonV1 getFirstVersionOfPersonRequestParameter(){
        return new PersonV1("Song WooSeok");
    }

    @GetMapping(path="/person", params = "version=2")
    public PersonV2 getFirstSecondOfPersonRequestParameter(){
        return new PersonV2("Song", "Wooseok");
    }

    /* ========= Request Header ============*/
    //"X-API-VERSION이 key, 1이 value"
    @GetMapping(path="/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonRequestHeader(){
        return new PersonV1("Song WooSeok");
    }

    //"X-API-VERSION이 key, 1이 value"
    @GetMapping(path="/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonRequestHeader(){
        return new PersonV2("Song", "Wooseok");
    }

    /* ========= Media Type Versioning ============*/
    @GetMapping(path="/person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfPersonAcceptHeader(){
        return new PersonV1("Song WooSeok");
    }

    @GetMapping(path="/person/accept", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getSecondVersionOfPersonAcceptHeader(){
        return new PersonV2("Song", "Wooseok");
    }



}
