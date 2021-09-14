package com.example.hunminjungum;

public class SingleComment {
    String writer, comment;
    public SingleComment(String writer, String comment){
        this.writer = writer;
        this.comment = comment;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
