package com.openclassrooms.mdd.responses;

import java.util.List;

import com.openclassrooms.mdd.model.Comment;

public class CommentsResponse {
    private List<Comment> comments;

    public CommentsResponse(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

