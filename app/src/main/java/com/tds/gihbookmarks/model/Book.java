package com.tds.gihbookmarks.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class Book implements Parcelable {
    private String bookId;
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

    public Book(Parcel in){
        bookId=in.readString();
        isbn=in.readString();
        title=in.readString();
        author=in.readString();
        publication=in.readString();
        edition=in.readString();
        expectedPrice=in.readString();
        city=in.readString();
        userId=in.readString();
        imageUrl1=in.readString();
//        dateAdded=in.readParcelable(Timestamp.class.getClassLoader());



    }

    public Book(String bookId, String isbn, String title, String author, String publication, String edition, String expectedPrice, String city, String userId, String imageUrl1, Timestamp dateAdded) {
        this.bookId = bookId;
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

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

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


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookId);
        dest.writeString(isbn);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publication);
        dest.writeString(edition);
        dest.writeString(expectedPrice);
        dest.writeString(city);
        dest.writeString(userId);
        dest.writeString(imageUrl1);
//        dest.writeParcelable(dateAdded, flags);
    }
}

