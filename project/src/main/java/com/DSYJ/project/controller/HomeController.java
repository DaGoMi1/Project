package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.domain.Posting;
import com.DSYJ.project.dto.CustomUserDetails;
import com.DSYJ.project.service.PostingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {

    private final PostingService postingService;

    public HomeController(PostingService postingService) {
        this.postingService = postingService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Posting> freeBoardList = postingService.findByBoardType("free"); // 자유게시판 목록을 가져온다
        model.addAttribute("freeBoardList", freeBoardList);
        return "home";
    }
}

