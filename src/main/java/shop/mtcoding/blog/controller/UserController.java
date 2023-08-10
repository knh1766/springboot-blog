package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.UserUpdateDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session; // request는 가방, session은 서랍

    // localhost:8080/check?username=ssar
    // ResponseEntity 데이터를 응답함 이거쓰면 리스펀스바디안써도됨
    @GetMapping("/check")
    public ResponseEntity<String> check(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<String>("유저네임이 중복되었습니다", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("유저네임을 사용할 수 있습니다", HttpStatus.OK);

    }

    @PostMapping("/login")
    public String login(LoginDTO loginDTO) {

        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return "redirect:/40x";

        }
        // 핵심기능

        try {
            User user = userRepository.findByUsername(loginDTO.getUsername());// LoginDTO로 받은 유저네임과 비밀번호중에 유저네임으로 User
                                                                              // 찾기
            System.out.println("loginDTO.getPassword() : " + loginDTO.getPassword());
            System.out.println("user.getPassword() : " + user.getPassword());

            // user.getPassword() == loginDTO.getPassword()
            if (BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
                session.setAttribute("sessionUser", user);
                return "redirect:/";
            }

        } catch (Exception e) {
            return "redirect:/exLogin";
        }
        return "redirect:/exLogin";
    }

    // 실무
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        // validation check (유효성 검사)
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return "redirect:/40x";
        }
        // DB에 해당 username이 있는지 체크해보기
        User user = userRepository.findByUsername(joinDTO.getUsername());
        if (user != null) {
            return "redirect:/50x";
        }
        userRepository.save(joinDTO); // 핵심 기능 회원가입됨
        return "redirect:/loginForm";
    }

    // 비정상
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) throws IOException {
    // // username=ssar&password=1234&email=ssar@nate.com
    // BufferedReader br = request.getReader();
    // // 버퍼가 소비됨
    // String body = br.readLine();
    // // 버퍼에 값이 없어서, 못꺼냄.
    // String username = request.getParameter("username");
    // System.out.println("body : " + body);
    // System.out.println("username : " + username);
    // return "redirect:/loginForm";
    // }
    // 약간 정상
    // DS(컨트롤러 메서드 찾기, 바디데이터 파싱)
    // DS가 바디데이터를 파싱안하고 컨트롤러 메서드만 찾은 상황
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) {
    // String username = request.getParameter("username");
    // String password = request.getParameter("password");
    // String email = request.getParameter("email");
    // System.out.println("username : " + username);
    // System.out.println("password : " + password);
    // System.out.println("email : " + email);
    // return "redirect:/loginForm";
    // }
    // ip주소 부여 : 10.5.9.200:8080 -> mtcoding.com:8080
    // localhost, 127.0.0.1
    // a태그 form태그 method=get
    @GetMapping("/joinForm")
    public String joinForm() {
        // templates/
        // .mustache
        // templates//user/joinForm.mustache
        return "user/joinForm"; // ViewResolver
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();// (서랍을 비우는 것)
        return "redirect:/";
    }

    // 회원정보수정
    @PostMapping("/user/{id}/update")
    public String update(@PathVariable Integer id, UserUpdateDTO userUpdateDTO) {
        // 1. 인증 검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            System.out.println("인증 실패");
            return "redirect:/loginForm";
        }
        System.out.println("인증검사 통과");
        // // 2. 권한 체크
        User user = userRepository.findById(id);
        if (user.getId() != sessionUser.getId()) {
            System.out.println("권한 없음");
            return "redirect:/loginForm";
        }
        System.out.println("권한체크 통과");
        // 3. 유효성 검사(부가 로직)
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return "redirect:/40x";
        }
        System.out.println("유효성 검사 통과");
        // 4. 핵심 기능
        System.out.println("update 전");
        userRepository.update(userUpdateDTO, id);
        System.out.println("update 후");

        return "redirect:/";
    }
}