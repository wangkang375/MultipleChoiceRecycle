package com.example.wang.multiplechoicerecycle.EnjoyableAdapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.wang.multiplechoicerecycle.R;

/**
 * 描述:
 * <p>
 * Created by wang on 2018/1/30.
 */

public class SubMenuVH extends RecyclerView.ViewHolder {
    public TextView mSubMenuText;
    public AppCompatTextView mTv1;

    public SubMenuVH(View itemView) {
        super(itemView);
        mSubMenuText = (TextView) itemView.findViewById(R.id.sub_menu_text);
        mTv1 = (AppCompatTextView) itemView.findViewById(R.id.tv1);
    }
}
