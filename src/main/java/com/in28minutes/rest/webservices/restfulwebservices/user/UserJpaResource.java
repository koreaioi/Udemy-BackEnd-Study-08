package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJpaResource(UserRepository repository, PostRepository postRepository) {
        this.userRepository = repository;
        this.postRepository = postRepository;
    }

    //GET /users - 모드 사용자 조회 요청
    @GetMapping("/jpa/users")
    public List<User> retrieveALlUsers(){

        return  userRepository.findAll();
    }

    //GET /users/1
/*    @GetMapping("/jpa/users/{id}")
    public User retrieveUsers(@PathVariable Integer id){
        User user = userDaoService.findOne(id);

        if(user==null)
            throw new UserNotFoundException("id: " + id);
        return user;
    }*/

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUsers(@PathVariable Integer id){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id: " + id);

        EntityModel<User> entityModel = EntityModel.of(user.get()); //user에 대한 엔티티모델 생성

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveALlUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("해당 아이디는 없습니다. id: " + id);

        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty())
            throw new UserNotFoundException("id : " + id);

        post.setUser(user.get());
        // descripiotn은 Talend API Tester에서 보낸다. ex "description" : "I want to Learn Spring"
        Post savedPost = postRepository.save(post);

        // 게시물을 저장했으니 게시물에 대한 URL 만들기
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // /users에 /{id}를 추가
                .buildAndExpand(savedPost.getId()) // {id}에 들어갈 값은 방금 저장한 사용자의 id
                .toUri(); //URI로 변환

        return ResponseEntity.created(location).build();
    }

    //POST /users
    @PostMapping("/jpa/users") // <- 현재 요청에 해당하는 URL
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){ //User Bean과 매핑됨
        User savedUser = userRepository.save(user);

        // /users/4 -> /users/{id} -> /users/{user.getId}
        // ServletUriComponentsBuilder.fromCurrentRequest() -> 현재 요청에 해당하는 URL을 반환 여기서는 /users
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // /users에 /{id}를 추가
                .buildAndExpand(savedUser.getId()) // {id}에 들어갈 값은 방금 저장한 사용자의 id
                .toUri(); //URI로 변환
        return ResponseEntity.created(location).build(); //.build() 안하면 반환타입이 BodyBuild가 됨
    }
}
