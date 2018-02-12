package com.example.wang.multiplechoicerecycle.EnjoyableAdapter;

/**
 * 描述:
 * <p>
 * Created by wang on 2018/1/30.
 */

public interface ItemClickCallBack<T> {
    void onClickMainMenuItem(T t, int adapterPosition);

    void onClickSubMenuItem(T subMenu2MainMenuBean, T t, int adapterPosition);

    void onExpandListener(T t, boolean isExpand);
}
