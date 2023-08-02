package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session; // request는 가방, session은 서랍

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
            User user = userRepository.findByUsrnameAndPassword(loginDTO);
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/exLogin";
        }

    }

    // 실무
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        // validation check 유효성검사
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }

        try {
            userRepository.save(joinDTO); // 핵심기능

        } catch (Exception e) {
            return "redirect:/50x";
        }

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
        session.invalidate(); // 세션무효화(내 서랍을 비우는 것)
        return "redirect:/";
    }

}