package com.pshegde.instagramclient;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Prajakta on 5/9/2015.
 */
public class InstagramComment implements Parcelable{
    private String text;
    private String username;
    private String createdTime;
    private String profilePic;

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(text);
        out.writeString(username);
        out.writeString(createdTime);
        out.writeString(profilePic);
    }

    public static final Parcelable.Creator<InstagramComment> CREATOR
            = new Parcelable.Creator<InstagramComment>() {
        public InstagramComment createFromParcel(Parcel in) {
            return new InstagramComment(in);
        }

        public InstagramComment[] newArray(int size) {
            return new InstagramComment[size];
        }
    };

    public InstagramComment() {
        text = "";
        username = "";
        createdTime = "";
        profilePic = "";
    }

    private InstagramComment(Parcel in) {
        text = in.readString();
        username = in.readString();
        createdTime = in.readString();
        profilePic = in.readString();
    }
}
