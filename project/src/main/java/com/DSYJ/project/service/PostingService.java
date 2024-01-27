package com.DSYJ.project.service;

import com.DSYJ.project.domain.Posting;
import com.DSYJ.project.repository.SpringDataJpaPostingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Transactional
public class PostingService {

    private final SpringDataJpaPostingRepository postingRepository;

    @Autowired
    public PostingService(SpringDataJpaPostingRepository postingRepository) {
        this.postingRepository = postingRepository;
    }

    public void postSave(Posting posting) {
        postingRepository.save(posting);
    }

    public void postUpdate(Posting updatePosting) {
        Posting existingPosting = postingRepository.findById(updatePosting.getId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        existingPosting.setTitle(updatePosting.getTitle());
        existingPosting.setAuthor(updatePosting.getAuthor());
        existingPosting.setContent(updatePosting.getContent());
        existingPosting.setImage(updatePosting.getImage());
        existingPosting.setVideo(updatePosting.getVideo());
        existingPosting.setFile(updatePosting.getFile());
        existingPosting.setLink(updatePosting.getLink());
        existingPosting.setCreated_At(LocalDateTime.now());

        postingRepository.save(existingPosting);
    }

    public Optional<Posting> postId(Long id) {
        return postingRepository.findById(id);
    }

    public String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID().toString() + ".png";
        String filePath = "src/main/resources/static/IMG/" + fileName;

        // 이미지를 서버에 저장
        Path destination = Paths.get(filePath);
        FileCopyUtils.copy(image.getBytes(), destination.toFile());

        return "/IMG/" + fileName;
    }

    public String saveVideo(MultipartFile video) throws IOException {
        String fileName = "video_" + System.currentTimeMillis() + ".avi";
        String filePath = "/path/to/file/directory/" + fileName;

        // 비디오를 서버에 저장
        Path destination = Paths.get(filePath);
        FileCopyUtils.copy(video.getBytes(), destination.toFile());

        return "/file/" + fileName;
    }

    public String saveFile(MultipartFile file) throws IOException {
        String fileName = "file_" + System.currentTimeMillis() + ".pdf";
        String filePath = "/path/to/file/directory/" + fileName;

        // 파일을 서버에 저장
        Path destination = Paths.get(filePath);
        FileCopyUtils.copy(file.getBytes(), destination.toFile());

        return "/file/" + fileName;
    }

    public List<Posting> findAll() {
        return postingRepository.findAll();
    }

    public void deletePost(Long id){
        postingRepository.deleteById(id);
    }
}