package com.DSYJ.project.controller;

import com.DSYJ.project.domain.Posting;
import com.DSYJ.project.service.PostingService;
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
    public String write() {
        return "write";
    }

    @PostMapping("/submit_notice")
    public String submitNotice(@ModelAttribute PostingForm form) {
        Posting posting = new Posting();
        posting.setAuthor(form.getAuthor());
        posting.setContent(form.getContent());
        posting.setTitle(form.getTitle());
        posting.setPassword(form.getPassword());
        posting.setCreated_At(LocalDateTime.now());

        if (form.getImage() != null && !form.getImage().isEmpty()) {
            // 이미지를 저장하고 그에 대한 URL을 데이터베이스에 저장
            String image = postingService.saveImage(form.getImage());
            posting.setImage(image);
        } else {
            posting.setImage(null);
        }

        if (form.getVideo() != null && !form.getVideo().isEmpty()) {
            // 이미지를 저장하고 그에 대한 URL을 데이터베이스에 저장
            String video = postingService.saveVideo(form.getVideo());
            posting.setVideo(video);
        } else {
            posting.setVideo(null);
        }

        if (form.getFile() != null && !form.getFile().isEmpty()) {
            // 파일을 저장하고 그에 대한 URL을 데이터베이스에 저장
            String file = postingService.saveFile(form.getFile());
            posting.setFile(file);
        } else {
            posting.setFile(null);
        }

        if (form.getLink().isEmpty()) {
            posting.setLink(null);
        } else {
            posting.setLink(form.getLink());
        }

        postingService.postSave(posting);
        return "redirect:/notice";
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

}
