package com.example.hunminjungum;

public class SinglePost {
    String title, writer, time;
    int views, postnum;
    public SinglePost(String title, String writer, String time, int views, int postnum){
        this.title=title;
        this.writer=writer;
        this.time=time;
        this.views=views;
        this.postnum = postnum;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getPostnum() { return postnum; }

    public void setPostnum(int postnum) { this.postnum = postnum; }
}
