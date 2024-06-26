package com.ohgiraffers.springeagles.kkhBlog.comment.service;

import com.ohgiraffers.springeagles.global.error.ResourceNotFoundException;
import com.ohgiraffers.springeagles.kkhBlog.comment.dto.KHCommentDTO;
import com.ohgiraffers.springeagles.kkhBlog.comment.repository.KHCommentEntity;
import com.ohgiraffers.springeagles.kkhBlog.comment.repository.KHCommentRepository;
import com.ohgiraffers.springeagles.kkhBlog.posts.repository.KHPostsEntity;
import com.ohgiraffers.springeagles.kkhBlog.posts.repository.KHPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KHCommentService {

    private final KHCommentRepository KHCommentRepository;
    private final KHPostsRepository KHPostsRepository;

    // CommentService 생성자
    @Autowired
    public KHCommentService(KHCommentRepository KHCommentRepository, KHPostsRepository KHPostsRepository) {
        this.KHCommentRepository = KHCommentRepository;
        this.KHPostsRepository = KHPostsRepository;
    }

    // 모든 댓글 조회 메서드
    public List<KHCommentDTO> getAllComments() {
        // 모든 댓글을 가져와서 CommentDTO로 변환하여 리스트로 반환
        return KHCommentRepository.findAll().stream()
                .map(KHCommentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // ID로 댓글 조회 메서드
    public Optional<KHCommentDTO> getCommentById(Long id) {
        // ID로 댓글을 조회하여 CommentDTO로 변환하여 Optional로 반환
        return KHCommentRepository.findById(id)
                .map(KHCommentDTO::fromEntity);
    }

    // 댓글 생성 메서드
    public KHCommentDTO createComment(KHCommentDTO KHCommentDTO) {
        // CommentDTO를 CommentEntity로 변환 후 저장하고, 저장된 엔티티를 다시 CommentDTO로 변환하여 반환
        KHCommentEntity entity = KHCommentDTO.toEntity();
        return KHCommentDTO.fromEntity(KHCommentRepository.save(entity));
    }

    // 댓글 수정 메서드
    public KHCommentDTO updateComment(Long id, KHCommentDTO commentDetails) {
        // ID로 댓글을 조회하고 없으면 예외 처리
        KHCommentEntity comment = KHCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + id));

        // 댓글 내용, 작성일, 수정일, 작성자, 포스트 ID를 업데이트
        comment.setCommentContent(commentDetails.getCommentContent());
        comment.setCreatedDate(commentDetails.getCommentDate());
        comment.setModifiedDate(commentDetails.getCommentModifyTime());
        comment.setCommentAuthor(commentDetails.getCommentAuthor());

        // 업데이트된 댓글을 저장하고 CommentDTO로 변환하여 반환
        return KHCommentDTO.fromEntity(KHCommentRepository.save(comment));
    }

    // 댓글 삭제 메서드
    public void deleteComment(Long id) {
        // ID로 댓글을 조회하고 없으면 예외 처리
        KHCommentEntity comment = KHCommentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found for this id :: " + id));

        // 댓글 삭제
        KHCommentRepository.delete(comment);
    }

    // 포스트 ID에 해당하는 모든 댓글 조회 메서드
    public List<KHCommentDTO> getCommentsByPostId(Long postId) {
        // 해당 포스트 ID에 해당하는 모든 댓글을 가져옴
        KHPostsEntity post = KHPostsRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));

        List<KHCommentEntity> comments = post.getComments();

        // CommentDTO 리스트로 변환하여 반환
        return comments.stream()
                .map(KHCommentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 댓글 목록을 뷰에 전달하는 메서드
    @GetMapping
    public String viewComments(Model model) {
        // 모든 댓글을 가져와서 CommentDTO 리스트로 변환
        List<KHCommentDTO> comments = getAllComments();

        // 모델에 댓글 목록과 빈 댓글 객체 추가
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new KHCommentDTO()); // 빈 댓글 객체를 모델에 추가

        // 뷰 페이지의 이름을 반환
        return "comments";
    }
}
