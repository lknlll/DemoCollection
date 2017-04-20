package com.lkn.a11509.democollection.Bean;

import java.io.Serializable;

/**
 * Created by 11509 on 2017/2/28.
 */

public class DataBean implements Serializable {
    private int id;
    private String title;
    private String content;

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
