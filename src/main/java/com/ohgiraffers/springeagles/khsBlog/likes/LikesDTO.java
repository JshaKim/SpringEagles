package com.ohgiraffers.springeagles.khsBlog.likes;

public class LikesDTO {
    private int likesId;

    public LikesDTO() {
    }

    public LikesDTO(int likesId) {
        this.likesId = likesId;
    }

    public int getLikesId() {
        return likesId;
    }

    public void setLikesId(int likesId) {
        this.likesId = likesId;
    }

}
