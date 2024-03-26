package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    //GET /users - 모드 사용자 조회 요청
    @GetMapping("/users")
    public List<User> retrieveALlUsers(){
        return userDaoService.findALl();
    }

    //GET /users/1
/*    @GetMapping("/users/{id}")
    public User retrieveUsers(@PathVariable Integer id){
        User user = userDaoService.findOne(id);

        if(user==null)
            throw new UserNotFoundException("id: " + id);
        return user;
    }*/

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUsers(@PathVariable Integer id){
        User user = userDaoService.findOne(id);

        if(user==null)
            throw new UserNotFoundException("id: " + id);

        EntityModel<User> entityModel = EntityModel.of(user); //user에 대한 엔티티모델 생성

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveALlUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userDaoService.DeleteById(id);
    }

    //POST /users
    @PostMapping("/users") // <- 현재 요청에 해당하는 URL
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){ //User Bean과 매핑됨
        User savedUser =userDaoService.save(user);
        // /users/4 -> /users/{id} -> /users/{user.getId}
        // ServletUriComponentsBuilder.fromCurrentRequest() -> 현재 요청에 해당하는 URL을 반환 여기서는 /users
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // /users에 /{id}를 추가
                .buildAndExpand(savedUser.getId()) // {id}에 들어갈 값은 방금 저장한 사용자의 id
                .toUri(); //URI로 변환
        return ResponseEntity.created(location).build(); //.build() 안하면 반환타입이 BodyBuild가 됨
    }
}
