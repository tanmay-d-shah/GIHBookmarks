package com.tds.gihbookmarks.model;

public class BooksModal {

    private String Name;
    private String ImgUrl;


    public BooksModal() {

        //required constructor

    }
    public BooksModal(String name, String imgUrl){

        Name=name;
        ImgUrl=imgUrl;

    }

    //Getter

    public String getName() {
        return Name;
    }

    public String getImgUrl() {
        return ImgUrl;
    }



    //setter


    public void setName(String name) {
        Name = name;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

}
