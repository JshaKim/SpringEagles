package com.ohgiraffers.springeagles.hjhBlog.comment.controller;

import com.ohgiraffers.springeagles.global.error.ResourceNotFoundException;
import com.ohgiraffers.springeagles.hjhBlog.comment.dto.JHCommentDTO;
import com.ohgiraffers.springeagles.hjhBlog.comment.service.JHCommentService;
import com.ohgiraffers.springeagles.hjhBlog.posts.service.JHPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class JHCommentController {

    private final JHPostsService postsService;
    private final JHCommentService commentService;

    @Autowired
    public JHCommentController(JHCommentService commentService, JHPostsService postsService) {
        this.postsService = postsService;
        this.commentService = commentService;
    }

    // 모든 댓글 조회 API
    @GetMapping
    public List<JHCommentDTO> getAllComments() {
        return commentService.getAllComments();
    }

    // ID로 댓글 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<JHCommentDTO> getCommentById(@PathVariable Long id) {
        JHCommentDTO comment = commentService.getCommentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + id));
        return ResponseEntity.ok(comment);
    }

    // 댓글 생성 API
    @PostMapping
    public JHCommentDTO createComment(@RequestBody JHCommentDTO comment) {
        return commentService.createComment(comment);
    }

    // 댓글 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<JHCommentDTO> updateComment(@PathVariable Long id, @RequestBody JHCommentDTO commentDetails) {
        JHCommentDTO updatedComment = commentService.updateComment(id, commentDetails);
        return ResponseEntity.ok(updatedComment);
    }

    // 특정 포스트에 대한 댓글 목록 조회 메서드
    @GetMapping("/{postId}/comments")
    public String viewCommentsByPostId(@PathVariable Long postId, Model model) {
        // postId를 기반으로 해당 포스트에 대한 모든 댓글을 가져옴
        List<JHCommentDTO> comments = commentService.getCommentsByPostId(postId);

        // 모델에 댓글 목록과 빈 댓글 객체 추가
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new JHCommentDTO()); // 빈 댓글 객체를 모델에 추가

        // 현재 포스트 ID도 모델에 추가 (템플릿에서 사용될 수 있음)
        model.addAttribute("postId", postId);

        // 뷰 페이지의 이름을 반환
        return "post-comments";
    }
}
