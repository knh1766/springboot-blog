package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.UserUpdateDTO;
import shop.mtcoding.blog.model.User;

//BoardController,usercontroller,userrepository
//entitymaneger,httplsession
@Repository

public class UserRepository {

    @Autowired
    private EntityManager em;

    public User findByUsrnameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",
                User.class);
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    public User findByUsername(String username) {
        try {
            Query query = em.createNativeQuery("select * from user_tb where username=:username",
                    User.class);
            query.setParameter("username", username);

            return (User) query.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    // User id를 통한 상세보기
    public User findById(Integer id) {
        // 1. 쿼리문 작성 및 Board 클래스 매핑
        Query query = em.createNativeQuery("select * from user_tb where id = :id", User.class);
        // 2. 변수 바인딩
        query.setParameter("id", id);
        // 3. 리턴
        return (User) query.getSingleResult();
    }

    @Transactional // 일을최소단위 일 마다 다다름 하나라고 실패하면 롤백 다됫음면커밋
    public void save(JoinDTO joinDTO) {
        String Password = BCrypt.hashpw(joinDTO.getPassword(), BCrypt.gensalt());
        System.out.println("테스트:" + 1);
        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email) values(:username, :password, :email)");
        System.out.println("테스트:" + 2);
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", Password);
        query.setParameter("email", joinDTO.getEmail());

        System.out.println("테스트:" + 3);

        query.executeUpdate(); // 쿼리전송(DBMS)
        System.out.println("테스트:" + 4);
    }

    // update() 회원정보수정 메서드
    @Transactional
    public void update(UserUpdateDTO userUpdateDTO, Integer id) {
        // 1. 쿼리문 작성
        Query query = em.createNativeQuery(
                "update user_tb set username = :username, password = :password, email = :email where id = :id",
                User.class);
        // 2. 변수 바인딩
        query.setParameter("username", userUpdateDTO.getUsername());
        query.setParameter("password", BCrypt.hashpw(userUpdateDTO.getPassword(), BCrypt.gensalt()));
        query.setParameter("email", userUpdateDTO.getEmail());
        query.setParameter("id", id);
        // 3. 전송
        query.executeUpdate();
    }
}
