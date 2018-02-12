package com.example.wang.multiplechoicerecycle;

import android.os.Bundle;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wang.multiplechoicerecycle.EnjoyableAdapter.ItemClickCallBack;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ItemClickCallBack<Menu> {
    private RecyclerView mRecycleView;
    private ArrayList<Menu> mMainList;
    private CustomAdapter mCustomAdapter;
    private PrintTextView mTvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mTvItem = (PrintTextView) findViewById(R.id.tv_item);
        mCustomAdapter = new CustomAdapter(this);
        mCustomAdapter.setItemClickCallBack(this);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 4));
        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        decor.setDrawable(getResources().getDrawable(R.drawable.ic_div_h_10));
        mRecycleView.addItemDecoration(decor);
        mRecycleView.setAdapter(mCustomAdapter);

        initData();
        mCustomAdapter.setData(mMainList);
        mTvItem.startPrint(3000, 100);

    }

    private void initData() {
        mMainList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Menu menu;
            if (i % 2 == 0) {
                menu = new Menu("主数据(单选)" + i);
            } else {
                menu = new Menu("主菜单(多选)" + i);
                menu.setMultipleSelect(true);
            }
            mMainList.add(menu);
        }

        for (Menu mainMenu : mMainList) {
            initSubData(mainMenu);
        }
    }

    private void initSubData(Menu mainMenu) {
        for (int i = 0; i < 8; i++) {
            if (mainMenu.isMultipleSelect() && i == 0) {
                mainMenu.getSubMenu().add(new Menu("不限"));
            } else {
                mainMenu.getSubMenu().add(new Menu("子菜单" + i));
            }

        }

    }

    @Override
    public void onClickMainMenuItem(Menu menu, int adapterPosition) {
        String format = String.format(Locale.CHINA, "点击的MainMenu为：%s", menu.getTitle());
        mTvItem.setText(format);
    }

    @Override
    public void onClickSubMenuItem(Menu subMenu2MainMenuBean, Menu menu, int adapterPosition) {
        String format = String.format(Locale.CHINA, "点击的SubMenu为:%s,对于的mainMenu为：%s", menu.getTitle(), subMenu2MainMenuBean.getTitle());
        mTvItem.setText(format);
    }

    @Override
    public void onExpandListener(Menu menu, boolean isExpand) {

    }
}
