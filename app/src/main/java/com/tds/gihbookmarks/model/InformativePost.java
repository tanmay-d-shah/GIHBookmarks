package com.tds.gihbookmarks.model;

public class InformativePost {

    private String desc,link;
    private int photo;

    public InformativePost() {
    }

    public InformativePost(String desc, String link, int photo) {
        this.desc = desc;
        this.link = link;
        this.photo = photo;
    }

    //getter

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public int getPhoto() {
        return photo;
    }

    //setter


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
