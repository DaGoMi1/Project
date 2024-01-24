package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // 현재 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자가 인증되었는지 확인
        if (authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String id = userDetails.getUsername();
            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            model.addAttribute("id", id);
            model.addAttribute("role", role);
        }

        return "home";
    }
}

