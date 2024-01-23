package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import  jakarta.servlet.http.HttpSession;

import java.util.Collection;
import java.util.Iterator;

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

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
    @PostMapping("/login-process")
    public String idChecking(@RequestParam String userId, @RequestParam String password, Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            if ("ROLE_ADMIN".equals(role)) {
                // ADMIN 권한이 있는 경우 처리
                return "redirect:/admin";
            } else if ("ROLE_USER".equals(role)) {
                // USER 권한이 있는 경우 처리
                return "redirect:/user";
            }
            // 다른 권한이 필요한 경우 추가
        }

        // 권한이 없거나 처리하지 않은 권한이 있는 경우 기본 처리
        return "redirect:/default";
    }

        @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.invalidate();
            return "redirect:/";
        }


}
