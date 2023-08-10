package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "user_tb") // 테이블 이름을 붙이기 위해 사용, DB는 대소문자를 구분함.
@Entity // ddl-auto: create일때 테이블 제작
public class User {

    // id : PK, auto_increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // username : not null, unique, length <= 20
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    // password : not null, length <= 100
    @Column(nullable = false, length = 100)
    private String password;

    // email : not null, unique, length <= 20
    @Column(nullable = false, unique = true, length = 20)
    private String email;

}