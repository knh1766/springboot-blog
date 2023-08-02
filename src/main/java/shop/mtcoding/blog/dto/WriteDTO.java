package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 글쓰기 API
 * 1,URL :http://localhost:8080/board/{id}/update
 * 2,method :POST(만들어서 줘야됨)
 * 3,요청 body :title=값(string)&content=값(string)
 * 4, MINE타입 : x-www-form-unlencoded
 * 5,응답: view(html)를 응답함. 디데일페이지
 * 
 */

@Getter
@Setter
public class WriteDTO {

    private String title;
    private String content;

    public String getPassword() {
        return null;
    }

}
