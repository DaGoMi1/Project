package com.DSYJ.project.controller;

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
@RequestMapping("/board")
public class BoardController {

    private final PostingService postingService;

    public BoardController(PostingService postingService) {
        this.postingService = postingService;
    }

    @GetMapping("/{boardType}")
    public String boardList(@PathVariable String boardType, Model model) {
        List<Posting> postings = postingService.findByBoardType(boardType);
        model.addAttribute("postings", postings);
        model.addAttribute("boardType", boardType);
        return "boardList";  // "boardList"는 공통된 뷰 페이지 이름으로 가정합니다.
    }


    @GetMapping("/write")
    public String write(@RequestParam(name = "boardType") String boardType, Model model) {
        // 빈 폼을 렌더링하기 위해 빈 Posting 객체를 전달
        model.addAttribute("posting", new Posting());
        model.addAttribute("editable", false);  // 수정 가능한 상태로 설정
        model.addAttribute("boardType", boardType);
        return "write";
    }

    @PostMapping("/submit_post")
    public String submitPost(@ModelAttribute("postForm") Posting posting,
                             @RequestParam("boardType") String boardType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        posting.setUserId(userDetails.getUsername());
        posting.setCreated_At(LocalDateTime.now());
        posting.setBoardType(boardType);

        postingService.postSave(posting);
        return "redirect:/board/" + boardType;
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

        String boardType = form.getBoardType();

        // 저장된 게시글 업데이트
        postingService.postUpdate(updatedPosting);

        return "redirect:/board/" + boardType;
    }

    @GetMapping("/board/{id}")
    public String viewFree(@PathVariable Long id, Model model) {
        Optional<Posting> postingOptional = postingService.postId(id);

        // Optional이 값이 있는 경우에만 모델에 추가
        Posting posting = postingOptional.orElse(null);
        model.addAttribute("posting", posting);

        // 상세 보기 페이지로 이동
        return "boardDetail";
    }

    @GetMapping("/edit")
    public String editPost(@RequestParam("postId") Long postId, Model model) {
        // postId를 사용하여 게시글 정보를 가져오는 코드
        Posting existingPosting = postingService.postId(postId)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 게시글을 찾을 수 없습니다: " + postId));
        ; // 이 메서드는 게시글 ID로 게시글을 조회하는 메서드로 가정

        model.addAttribute("posting", existingPosting);
        model.addAttribute("editable", true);  // 수정 가능한 상태로 설정
        return "write";
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam("postId") Long postId) {
        Optional<Posting> optionalPosting = postingService.postId(postId);

        String boardType = optionalPosting.map(Posting::getBoardType).orElse("defaultBoardType");
        postingService.deletePost(postId);
        return "redirect:/board/" + boardType;
    }
}
