package com.gdb.listtest;

import android.util.Log;

/**
 * Created by devdatta on 24/12/15.
 */
public class ListContent {
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        String current = this.getTitle();
        return current.equalsIgnoreCase(((ListContent) o).getTitle());

    }
}
