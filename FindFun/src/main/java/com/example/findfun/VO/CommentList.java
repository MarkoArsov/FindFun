package com.example.findfun.VO;

import lombok.Data;

import java.util.List;

@Data
public class CommentList {

    List<LocalComment> comments;

    public CommentList(List<LocalComment> comments) {
        this.comments = comments;
    }
}
