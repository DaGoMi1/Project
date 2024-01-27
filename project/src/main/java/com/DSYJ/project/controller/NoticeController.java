package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Member;
import com.DSYJ.project.domain.Posting;
import com.DSYJ.project.dto.CustomUserDetails;
import com.DSYJ.project.service.PostingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final PostingService postingService;

    public NoticeController(PostingService postingService) {
        this.postingService = postingService;
    }

    @GetMapping("/notice")
    public String notice(Model model) {
        List<Posting> notice = postingService.findAll();

        model.addAttribute("notice", notice);
        return "notice";
    }

    @GetMapping("/vote")
    public String vote(Model model) {
        return "vote";
    }

    @GetMapping("/write")
    public String write(Model model) {
        // 빈 폼을 렌더링하기 위해 빈 Posting 객체를 전달
        model.addAttribute("posting", new Posting());
        model.addAttribute("editable", false);  // 수정 가능한 상태로 설정
        return "write";
    }

    @PostMapping("/submit_notice")
    public String submitNotice(@ModelAttribute("postForm") Posting posting) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        posting.setUserId(userDetails.getUsername());
        posting.setCreated_At(LocalDateTime.now());

        postingService.postSave(posting);
        return "redirect:/notice/notice";
    }

    @PostMapping("/save_edit")
    public String saveEdit(@ModelAttribute PostingForm form) {
        Posting updatedPosting = new Posting();

        updatedPosting.setId(form.getId());
        updatedPosting.setAuthor(form.getAuthor());
        updatedPosting.setTitle(form.getTitle());
        updatedPosting.setContent(form.getContent());
        updatedPosting.setImage(form.getImage());
        updatedPosting.setVideo(form.getVideo());
        updatedPosting.setFile(form.getFile());
        updatedPosting.setLink(form.getLink());
        updatedPosting.setCreated_At(LocalDateTime.now());

        // 저장된 게시글 업데이트
        postingService.postUpdate(updatedPosting);

        return "redirect:/notice/notice";
    }

    @GetMapping("/notice/{id}")
    public String viewNotice(@PathVariable Long id, Model model) {
        Optional<Posting> postingOptional = postingService.postId(id);

        // Optional이 값이 있는 경우에만 모델에 추가
        Posting posting = postingOptional.orElse(null);
        model.addAttribute("posting", posting);

        // 상세 보기 페이지로 이동
        return "noticeDetail";
    }

    @GetMapping("/edit")
    public String editNotice(@RequestParam("postId") Long postId, Model model) {
        // postId를 사용하여 게시글 정보를 가져오는 코드
        Posting existingPosting = postingService.postId(postId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 게시글을 찾을 수 없습니다: " + postId));
        ; // 이 메서드는 게시글 ID로 게시글을 조회하는 메서드로 가정

        model.addAttribute("posting", existingPosting);
        model.addAttribute("editable", true);  // 수정 가능한 상태로 설정
        return "write";
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam("postId") Long postId){
        postingService.deletePost(postId);
        return "redirect:/notice/notice";
    }
}
