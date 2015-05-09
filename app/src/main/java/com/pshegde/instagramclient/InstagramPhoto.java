/**
 * Created by Prajakta on 5/7/2015.
 */
package com.pshegde.instagramclient;

import java.util.ArrayList;
import java.util.List;

public class InstagramPhoto {
    private String username;
    private String caption;
    private String imageUrl;
    private int imageHeight;
    private int likeCount;
    private String relativePostingTime;
    private String userProfilePic;
    private List<InstagramComment> comments;

    public InstagramPhoto() {
        super();
        comments = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public String getRelativePostingTime() {
        return relativePostingTime;
    }

    public void setRelativePostingTime(String relativePostingTime) {
        this.relativePostingTime = relativePostingTime;
    }

    public List<InstagramComment> getComments() {
        return comments;
    }

    public void addComment(InstagramComment comment) {
        comments.add(comment);
    }
}
