package com.tds.gihbookmarks.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class StudyMaterial implements Parcelable {
    private String title;

    private String desc;
    private String imgUrl;
    private Timestamp dateAdded;

    public StudyMaterial(){}

    public StudyMaterial(String title, String desc, String imgUrl, Timestamp dateAdded) {
        this.title = title;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public static Creator<StudyMaterial> getCREATOR() {
        return CREATOR;
    }

    protected StudyMaterial(Parcel in) {
        title = in.readString();
        desc = in.readString();
        imgUrl = in.readString();
        dateAdded = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<StudyMaterial> CREATOR = new Creator<StudyMaterial>() {
        @Override
        public StudyMaterial createFromParcel(Parcel in) {
            return new StudyMaterial(in);
        }

        @Override
        public StudyMaterial[] newArray(int size) {
            return new StudyMaterial[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(imgUrl);
        dest.writeParcelable(dateAdded, flags);
    }
}
