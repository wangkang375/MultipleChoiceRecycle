package com.example.wang.multiplechoicerecycle.EnjoyableAdapter;

import java.util.ArrayList;

/**
 * 描述:
 * <p>
 * Created by wang on 2018/1/30.
 */

public abstract class MenuManger<T extends MenuManger> {
    public boolean mSelect;
    private ArrayList<T> mSubMenu = new ArrayList<>();
    public boolean mColl;
    public boolean mMultipleSelect;

    public ArrayList<T> getSubMenu() {
        return mSubMenu;
    }

    public boolean isSelect() {
        return mSelect;
    }

    public void setSelect(boolean select) {
        mSelect = select;
    }

    public boolean isColl() {
        return mColl;
    }

    public void setColl(boolean coll) {
        mColl = coll;
    }

    public boolean isMultipleSelect() {
        return mMultipleSelect;
    }

    public void setMultipleSelect(boolean multipleSelect) {
        mMultipleSelect = multipleSelect;
    }
}
