package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    //일단 정적 List 만들어서 DB 대안으로 사용
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 0;

    static{
        users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Eve", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "Jim", LocalDate.now().minusYears(20)));
    }

    public List<User> findALl(){
        return users;
    }

    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

/*
    //내가 짠 findOne()
    public User findOne(Integer id){
        for (User u : users) if(u.getId() == id) return u;
        return null;
    }
*/

    public User findOne(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);

        //orElse - 값이 존재하면 해당 값 반환, 없으면 사용자 정의 값 반환
    }
}
