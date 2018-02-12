package com.example.wang.multiplechoicerecycle.EnjoyableAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wang.multiplechoicerecycle.R;

/**
 * 描述:
 * <p>
 * Created by wang on 2018/1/30.
 */

public class MainMenuVH extends RecyclerView.ViewHolder {
    public TextView mTvMainMenu;

    public MainMenuVH(View itemView) {
        super(itemView);


        mTvMainMenu = itemView.findViewById(R.id.tv_main_menu);

    }
}
