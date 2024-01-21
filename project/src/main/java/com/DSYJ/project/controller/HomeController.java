package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {

        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if (loggedInMember != null) {
            model.addAttribute("userName", loggedInMember.getName());
        }

        return "home";
    }
}

