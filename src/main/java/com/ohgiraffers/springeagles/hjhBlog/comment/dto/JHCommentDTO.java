package com.ohgiraffers.springeagles.hjhBlog.comment.dto;

import com.ohgiraffers.springeagles.hjhBlog.comment.repository.JHCommentEntity;

import java.time.LocalDateTime;

public class JHCommentDTO {

    private Long commentID;
    private String commentContent;
    private LocalDateTime commentDate;
    private LocalDateTime commentModifyTime;
    private String commentAuthor;

    // 기본 생성자
    public JHCommentDTO() {
    }

    // 매개변수를 받는 생성자
    public JHCommentDTO(Long commentID, String commentContent, LocalDateTime commentDate,
                        LocalDateTime commentModifyTime, String commentAuthor) {
        this.commentID = commentID;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
        this.commentModifyTime = commentModifyTime;
        this.commentAuthor = commentAuthor;
    }

    public static JHCommentDTO fromEntity(JHCommentEntity entity) {
        return new JHCommentDTO(
                entity.getCommentID(),
                entity.getCommentContent(),
                entity.getCreatedDate(),
                entity.getModifiedDate(),
                entity.getCommentAuthor()
        );
    }

    // DTO to Entity conversion method
    public JHCommentEntity toEntity() {
        JHCommentEntity entity = new JHCommentEntity();
        entity.setCommentID(this.commentID);
        entity.setCommentContent(this.commentContent);
        entity.setCreatedDate(this.commentDate);
        entity.setModifiedDate(this.commentModifyTime);
        entity.setCommentAuthor(this.commentAuthor);
        return entity;
    }

    // Getter 및 Setter 메서드
    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public LocalDateTime getCommentModifyTime() {
        return commentModifyTime;
    }

    public void setCommentModifyTime(LocalDateTime commentModifyTime) {
        this.commentModifyTime = commentModifyTime;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "commentID=" + commentID +
                ", commentContent='" + commentContent + '\'' +
                ", commentDate=" + commentDate +
                ", commentModifyTime=" + commentModifyTime +
                ", commentAuthor='" + commentAuthor + '\'' +
                '}';
    }
}
