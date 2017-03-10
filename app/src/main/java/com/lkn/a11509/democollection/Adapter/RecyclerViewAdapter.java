package com.lkn.a11509.democollection.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lkn.a11509.democollection.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kLin 11509 on 2017/3/9.
 * email 1150954859@qq.com
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    protected List<String> mListData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //定义接口
    public interface OnItemClickListener{
        void onItemClick(View v,int position);
        void onItemLongClick(View v,int position);
    }

    public RecyclerViewAdapter(Context context, List<String> data) {
        this.mListData = data;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item, parent, false);
        //item 布局的layout_height若设为match_parent，当滑动时元素会扩展至屏高
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    protected void setListener(final RecyclerView.ViewHolder viewHolder){
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = viewHolder.getPosition();
                    mOnItemClickListener.onItemClick(viewHolder.itemView,layoutPosition);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = viewHolder.getPosition();
                    mOnItemClickListener.onItemLongClick(viewHolder.itemView,layoutPosition);
                    return false;
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemContent.setText(mListData.get(position));
        holder.itemTitle.setText(mListData.get(position) + position);
        //在绑定ViewHolder的时候触发点击事件
        setListener(holder);
    }

    //添加布局
    public void add(int pos){
        mListData.add(pos,"add"+pos);
        notifyItemInserted(pos);
    }
    //删除布局
    public void delete(int pos){
        mListData.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    //RecyclerView.ViewHolder是泛型类，当继承RecyclerView.Adapter时需要使用具体的类来替换
    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_content)
        TextView itemContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
