package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EditController {

    private final MemberService memberService;

    @Autowired
    public EditController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/edit")
    public String myHome(HttpSession session, Model model) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        model.addAttribute("name", loggedInMember.getName());
        model.addAttribute("email", loggedInMember.getEmail());

        return "myPage";
    }

    @PostMapping("/updateUserInformation")
    public String editUser(@ModelAttribute MemberForm form,
                           @RequestParam String password2,
                           @RequestParam String password3,
                           HttpSession session) {
        // 세션에서 로그인된 사용자 정보 가져오기
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        loggedInMember.setName(updateField(loggedInMember.getName(), form.getName()));
        loggedInMember.setEmail(updateField(loggedInMember.getEmail(), form.getEmail()));

        if (!form.getPassword().isEmpty() && !password2.isEmpty() && !password3.isEmpty()) {
            if (form.getPassword().equals(loggedInMember.getPassword())
                    && password2.equals(password3)) {
                loggedInMember.setPassword(password2);
            }
        }
        // DB에 반영
        memberService.updateMember(loggedInMember);
        // 리다이렉트
        return "redirect:/";
    }

    private String updateField(String current, String newValue) {
        return (!newValue.isEmpty()) ? newValue : current;
    }
}
