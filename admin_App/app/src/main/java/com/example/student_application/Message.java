package com.example.student_application;

public class Message {
    String image,content,time,name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message(String image, String content, String time, String name) {
        this.image = image;
        this.content = content;
        this.time = time;
        this.name = name;
    }



    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
