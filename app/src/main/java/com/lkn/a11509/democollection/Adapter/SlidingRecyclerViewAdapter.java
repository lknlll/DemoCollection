package com.lkn.a11509.democollection.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkn.a11509.democollection.R;
import com.lkn.a11509.democollection.Utils;
import com.lkn.a11509.democollection.Widget.SlidingButtonView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kLin 11509 on 3/23/2017.
 * email 1150954859@qq.com
 */

public class SlidingRecyclerViewAdapter extends RecyclerView.Adapter<SlidingRecyclerViewAdapter.ViewHolder> implements SlidingButtonView.IonSlidingButtonListener {

    private SlidingButtonView mMenu = null;
    private IonSlidingViewClickListener mIonSlidingViewClickListener;
    private LayoutInflater mLayoutInflater;
    protected List<String> mListData;
    private Context mContext;

    public SlidingRecyclerViewAdapter(List<String> mListData, Context mContext) {
        this.mListData = mListData;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //定义接口
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);

        void onLongItemClick(View view, int position);

        void onDeleteBtnCilck(View view, int position);
    }

    //设置监听
    public void setDeleteLister(IonSlidingViewClickListener iDeleteBtnClickListener) {
        if (iDeleteBtnClickListener != null) {
            mIonSlidingViewClickListener = iDeleteBtnClickListener;
        }
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TAG", "onCreateViewHolder");
        View v = mLayoutInflater.inflate(R.layout.item_sliding_delete, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        //实现SlidingButtonView里面的IonSlidingButtonListener接口，并在创建ViewHolder的onCreateViewHolder()方法里面设置监听
        ((SlidingButtonView) v).setSlidingButtonListener(SlidingRecyclerViewAdapter.this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mListData.get(position));
        //设置内容布局的宽为屏幕宽度
        holder.mViewGroup.getLayoutParams().width = Utils.getScreenWidth(mContext);
        holder.mViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        Log.i("TAG", "关闭菜单");
        mMenu.closeMenu();
        mMenu = null;
    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            Log.i("asd", "删除菜单处于打开状态");
            return true;
        }
        Log.i("asd", "删除菜单处于关闭状态");
        return false;
    }

    @Override
    public void onMenuIsOpen(View view) {
        Log.i("TAG", "onMenuIsOpen   23");
        mMenu = (SlidingButtonView) view;
        Log.i("TAG", "删除菜单打开信息接收");
    }

    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        Log.i("TAG", "onDownOrMove    22");
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                Log.i("TAG", "关闭删除菜单111");
                closeMenu();
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_sliding_delete)
        TextView mTextView;
        @BindView(R.id.item_sliding_text)
        TextView mDeleteText;
        @BindView(R.id.item_sliding_lay)
        RelativeLayout mViewGroup;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
