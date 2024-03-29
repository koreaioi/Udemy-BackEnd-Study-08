package com.in28minutes.rest.webservices.restfulwebservices.jpa;

import com.in28minutes.rest.webservices.restfulwebservices.user.Post;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Post 를 생성하고 싶으면 이 리포지토리를 사용한다.
public interface PostRepository extends JpaRepository<Post, Integer> {


}
