package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.dto.CustomUserDetails;
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
        // ���� ������ ����� ������ ������
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // ����ڰ� �����Ǿ����� Ȯ��
        if (authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            Member loggedInMember = userDetails.getMember();
            String name = loggedInMember.getName();

            model.addAttribute("name", name);
        }

        return "home";
    }
}

