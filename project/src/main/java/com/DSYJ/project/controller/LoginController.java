package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import  jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final MemberService memberService;

    @Autowired
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-process")
    public String idChecking(@RequestParam String userId, @RequestParam String password, Model model, HttpSession session) {
        // 아이디와 비밀번호로 회원 조회
        Member foundMember = memberService.findByUserIdAndPassword(userId, password);

        if (foundMember != null) {
            // 로그인 성공
            session.setAttribute("loggedInMember", foundMember); // 세션에 로그인 정보 저장
            return "redirect:/";
        } else {
            // 로그인 실패
            model.addAttribute("message", "로그인 실패. 아이디 또는 비밀번호를 확인하세요.");
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInMember"); // 세션에서 로그인 정보 삭제
        return "redirect:/";
    }
    

}
