package com.example.wang.multiplechoicerecycle;

import android.content.Context;

import com.example.wang.multiplechoicerecycle.EnjoyableAdapter.AbsEnjoyableAdapter;
import com.example.wang.multiplechoicerecycle.EnjoyableAdapter.MainMenuVH;
import com.example.wang.multiplechoicerecycle.EnjoyableAdapter.SubMenuVH;

/**
 * 描述:
 * <p>
 * Created by wang on 2018/1/30.
 */

public class CustomAdapter extends AbsEnjoyableAdapter<Menu> {


    public CustomAdapter(Context context) {
        super(context);
    }

    @Override
    protected int mainMenuLayoutId() {
        return R.layout.item_main_menu;
    }

    @Override
    protected int subMenuLayoutId() {
        return R.layout.item_sub_menu;
    }

    @Override
    protected void bindSubMenuData(SubMenuVH subMenuVH, Menu subMenuManger) {
        super.bindSubMenuData(subMenuVH, subMenuManger);
        subMenuVH.mSubMenuText.setText(subMenuManger.getTitle());
    }

    @Override
    protected int getNorColor() {
        return R.color.menu_nor;
    }

    @Override
    protected int getSelectColor() {
        return R.color.menu_select;
    }

    @Override
    protected void bindMainMenuData(MainMenuVH mainMenuVH, Menu menuManger) {
        mainMenuVH.mTvMainMenu.setText(menuManger.getTitle());
    }

    @Override
    protected boolean isAllSelectNor(Menu menu) {
        return menu.getTitle().equals("不限");
    }

    @Override
    protected int expandAnimation() {
        return super.expandAnimation();
    }

    @Override
    protected int collAnimation() {
        return super.collAnimation();
    }
}
