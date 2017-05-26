package com.lkn.a11509.democollection.Bean;

import java.io.Serializable;

/**
 * Created by 11509 on 2017/2/28.
 */

public class DataBean implements Serializable {
    private int bannedCount;
    private int leftCount;
    private int id;
    private String title;
    private String content;
    private boolean isSelected;
    private boolean isBanned;

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public int getBannedCount() {
        return bannedCount;
    }

    public void setBannedCount(int bannedCount) {
        this.bannedCount = bannedCount;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
