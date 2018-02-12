package com.example.wang.multiplechoicerecycle;

import com.example.wang.multiplechoicerecycle.EnjoyableAdapter.MenuManger;

/**
 * 描述:
 * <p>
 * Created by wang on 2018/1/30.
 */

public class Menu extends MenuManger<Menu> {

    private String title;

    public Menu(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
