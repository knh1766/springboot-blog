package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 API
 * 1,URL :http://localhost:8080/join
 * 2,method :POST(만들어서 줘야됨)
 * 3,요청 body :username=값(string)&password=값(string)&email=값(string)
 * 4, MINE타입 : x-www-form-unlencoded
 * 5,응답: view(html)를 응답함.
 * 
 */

@Getter
@Setter
public class JoinDTO {
       private String username;
    private String password;
    private String email;

}

    