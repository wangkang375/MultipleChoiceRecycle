package com.example.wang.multiplechoicerecycle.EnjoyableAdapter;

import android.content.Context;
import android.os.Trace;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

/**
 * 描述:
 * <p>  adapter
 * Created by wang on 2018/1/30.
 */

public abstract class AbsEnjoyableAdapter<T extends MenuManger> extends RecyclerView.Adapter {
    private ArrayList<T> mTotalMenus = new ArrayList<>();//总共的菜单
    private ArrayList<T> mMainMenus = new ArrayList<>();//主菜单
    private ArrayList<T> mSubMenus = new ArrayList<>(); //子菜单
    private Context mContext;
    private SparseArray<T> mMainMap = new SparseArray<>();//管理主菜单
    private final static int ITEM_MAIN_TYPE = 0;
    private final static int ITEM_SUB_TYPE = 1;
    private GridLayoutManager mGridLayoutManager;
    private ItemClickCallBack<T> mItemClickCallBack;

    protected AbsEnjoyableAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_MAIN_TYPE:
                viewHolder = createMainMenuVH(inflater, parent);
                break;
            case ITEM_SUB_TYPE:
                viewHolder = createSubMenuVH(inflater, parent);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    /**
     * 创建子vh
     */
    private RecyclerView.ViewHolder createSubMenuVH(LayoutInflater inflater, ViewGroup parent) {
        View inflate = inflater.inflate(subMenuLayoutId(), parent, false);
        return new SubMenuVH(inflate);
    }

    /**
     * 主菜单的数据
     *
     * @return 主菜单的布局
     */
    protected abstract int mainMenuLayoutId();

    /**
     * 创建主VH
     */
    private RecyclerView.ViewHolder createMainMenuVH(LayoutInflater inflater, ViewGroup parent) {
        View inflate = inflater.inflate(mainMenuLayoutId(), parent, false);
        return new MainMenuVH(inflate);
    }

    /**
     * 子菜单的数据
     *
     * @return 子菜单的布局
     */
    protected abstract int subMenuLayoutId();

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_MAIN_TYPE:
                MainMenuVH mainMenuVH = (MainMenuVH) holder;
                mainMenuVH.mTvMainMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickCallBack != null) {
                            int adapterPosition = holder.getAdapterPosition();
                            clickMainMenu(holder, adapterPosition);
                            mItemClickCallBack.onClickMainMenuItem(mTotalMenus.get(adapterPosition), adapterPosition);
                        }
                    }
                });
                bindMainMenuData(mainMenuVH, mTotalMenus.get(position));
                break;
            case ITEM_SUB_TYPE:
                SubMenuVH subMenuVH = (SubMenuVH) holder;
                subMenuVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickCallBack != null) {
                            int adapterPosition = holder.getAdapterPosition();
                            T t = clickSubMenu(adapterPosition);
                            T subMenu2MainMenuBean = subMenu2MainMenuBean(t);
                            mItemClickCallBack.onClickSubMenuItem(subMenu2MainMenuBean, t, adapterPosition);
                        }
                    }
                });
                bindSubMenuData(subMenuVH, mTotalMenus.get(position));
                break;
        }
    }

    /**
     * 点击主菜单
     *
     * @param holder
     * @param adapterPosition 主position
     */
    private void clickMainMenu(RecyclerView.ViewHolder holder, int adapterPosition) {
        T t = mTotalMenus.get(adapterPosition);
        if (t.isColl()) {
            if (expandAnimation() != 0) {
                holder.itemView.startAnimation(AnimationUtils.loadAnimation(mContext, expandAnimation()));
            }
            expand(adapterPosition, t);
        } else {
            if (collAnimation() != 0) {
                holder.itemView.startAnimation(AnimationUtils.loadAnimation(mContext, collAnimation()));
            }
            coll(adapterPosition, t);
        }
        t.setColl(!t.isColl());
    }

    /**
     * @return 折叠动画
     */
    @AnimRes
    protected int collAnimation() {
        return 0;
    }

    /**
     * @return 展开的动画
     */
    @AnimRes
    protected int expandAnimation() {
        return 0;
    }

    /**
     * 展开
     *
     * @param adapterPosition a
     * @param t               t
     */
    private void expand(int adapterPosition, T t) {
        int size = t.getSubMenu().size();
        boolean b = mTotalMenus.addAll(adapterPosition + 1, t.getSubMenu());
        if (b) {
            mItemClickCallBack.onExpandListener(t, true);
            notifyItemRangeInserted(adapterPosition + 1, size);
        }

    }

    /**
     * 折叠
     */
    private void coll(int adapterPosition, T t) {
        Trace.beginSection("coll");
        boolean b = mTotalMenus.removeAll(t.getSubMenu());
        int size = t.getSubMenu().size();
        if (b) {
            mItemClickCallBack.onExpandListener(t, false);
            notifyItemRangeRemoved(adapterPosition + 1, size);
        }
        Trace.endSection();

    }

    /**
     * 点击子菜单
     *
     * @param adapterPosition position
     * @return
     */
    private T clickSubMenu(int adapterPosition) {
        T t = mTotalMenus.get(adapterPosition);
        t.setSelect(!t.isSelect());
        int menu2index = subMenu2index(t);
        T mainMenu = mMainMap.get(menu2index);
        if (mainMenu.isMultipleSelect()) {//菜单是多选
            subMenuMultipleSelect(adapterPosition, t);
        } else {//单选
            subMenuSingleSelect(t, mainMenu);
        }
        return t;
    }

    /**
     * 单选
     */
    private void subMenuSingleSelect(T t, T mainMenu) {
        for (Object o : mainMenu.getSubMenu()) {
            if (o instanceof MenuManger) {
                MenuManger o1 = (MenuManger) o;
                if (t != o1) {
                    o1.setSelect(false);
                }
            }
        }
        notifyItemRangeChanged(mainMenu2Index(mainMenu) + 1, mainMenu.getSubMenu().size());
    }

    /**
     * 主菜单在整个集合的index
     *
     * @param mainMenu 主菜单
     * @return
     */
    private int mainMenu2Index(T mainMenu) {
        return mTotalMenus.indexOf(mainMenu);
    }


    /**
     * 子菜单多选的逻辑
     */
    private void subMenuMultipleSelect(int adapterPosition, T t) {
        if (hasUnlimited()) {
            if (isAllSelectNor(t))
                changeAllNor(t);
            else
                selectNoUnLimited(adapterPosition, t);
        } else
            notifyItemChanged(adapterPosition);
    }

    /**
     * 选择不是 不限的
     *
     * @param adapterPosition
     * @param t
     */
    private void selectNoUnLimited(int adapterPosition, T t) {
        int index = subMenu2IndexForMap(t);
        if (mTotalMenus.get(index + 1).isSelect()) {
            mTotalMenus.get(index + 1).setSelect(false);
            notifyItemChanged(index + 1);
        }
        notifyItemChanged(adapterPosition);
    }

    /**
     * 子菜单对应主菜单在totalList的index
     *
     * @param t
     * @return
     */
    private int subMenu2IndexForMap(T t) {
        return mTotalMenus.indexOf(subMenu2MainMenuBean(t));
    }

    /**
     * 子菜单对应的主菜单的实体
     *
     * @param t t
     * @return
     */
    private T subMenu2MainMenuBean(T t) {
        for (int i = 0; i < mMainMap.size(); i++) {
            T valueAt = mMainMap.valueAt(i);
            for (Object o : valueAt.getSubMenu()) {
                if (o == t) {
                    return valueAt;
                }
            }
        }
        return null;
    }

    /**
     * 设置子菜单 有一个可以不限
     *
     * @return
     */
    protected boolean hasUnlimited() {
        return true;
    }

    /**
     * 点击哪个菜单全部为未选
     */
    protected boolean isAllSelectNor(T t) {

        return false;
    }

    /**
     * 选不限 全部为未选
     *
     * @param t t
     */
    private void changeAllNor(T t) {
        int menu2index = subMenu2index(t);
        for (Object o : mMainMap.get(menu2index).getSubMenu()) {
            if (o instanceof MenuManger) {
                MenuManger o1 = (MenuManger) o;
                if (o1 != t && t.isSelect()) {
                    o1.setSelect(false);
                }
            }
        }
        int i = mTotalMenus.indexOf(t);
        notifyItemRangeChanged(i, mMainMap.get(menu2index).getSubMenu().size());
    }

    /**
     * 子菜单 对应的 主菜单在的mMainMap的index
     *
     * @param t t
     * @return index
     */
    private int subMenu2index(T t) {
        for (int i = 0; i < mMainMap.size(); i++) {
            T valueAt = mMainMap.valueAt(i);
            for (Object o : valueAt.getSubMenu()) {
                if (o == t) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * bind子菜单的数据
     *
     * @param subMenuVH     子菜单 vh
     * @param subMenuManger 子菜单数据
     */
    protected void bindSubMenuData(SubMenuVH subMenuVH, T subMenuManger) {
        subMenuVH.mSubMenuText.setBackgroundColor(subMenuManger.isSelect() ?
                mContext.getResources().getColor(getSelectColor())
                : mContext.getResources().getColor(getNorColor()));
    }

    /**
     * 普通状态下 颜色
     *
     * @return
     */
    @ColorRes
    protected abstract int getNorColor();

    /**
     * 子菜单呗选择中 颜色
     *
     * @return
     */
    @ColorRes
    protected abstract int getSelectColor();

    protected void bindMainMenuData(MainMenuVH mainMenuVH, T mainManger) {

    }

    public void setItemClickCallBack(ItemClickCallBack itemClickCallBack) {
        mItemClickCallBack = itemClickCallBack;
    }

    @Override
    public int getItemViewType(int position) {
        if (mTotalMenus.get(position).getSubMenu().size() != 0) {
            return ITEM_MAIN_TYPE;
        } else {
            return ITEM_SUB_TYPE;
        }
    }

    @Override
    public int getItemCount() {

        return mTotalMenus.size();
    }

    public void setData(ArrayList<T> mainList) {
        mMainMenus.clear();
        mMainMap.clear();
        mSubMenus.clear();
        mTotalMenus.clear();
        mMainMenus.addAll(mainList);
        for (int i = 0; i < mMainMenus.size(); i++) {
            T mainMenu = mMainMenus.get(i);
            mTotalMenus.add(mainMenu);
            mMainMap.put(i, mainMenu);
            if (mainMenu.getSubMenu().size() != 0) {
                mTotalMenus.addAll(mainMenu.getSubMenu());
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            mGridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        }
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return getItemViewType(position) == ITEM_SUB_TYPE ? 1 : mGridLayoutManager.getSpanCount();
            }
        });
    }
}
