package com.tds.gihbookmarks.model;

public class LinkPost {

    private String description;
    private String link;

    public LinkPost(){

    }
    public LinkPost(String description,String link){

        this.description=description;
        this.link=link;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
