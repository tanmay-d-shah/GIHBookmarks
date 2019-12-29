package com.tds.gihbookmarks.model;

import com.google.firebase.Timestamp;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String publication;
    private String edition;
    private String expectedPrice;
    private String city;
    private String userId;
    private String imageUrl1;

    private Timestamp dateAdded;

    public Book(){}

    public Book(String isbn, String title, String author, String publication, String edition, String expectedPrice, String city, String userId, String imageUrl1, String imageUrl2, Timestamp dateAdded) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publication = publication;
        this.edition = edition;
        this.expectedPrice = expectedPrice;
        this.city = city;
        this.userId = userId;
        this.imageUrl1 = imageUrl1;

        this.dateAdded = dateAdded;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(String expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }



    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }


}

