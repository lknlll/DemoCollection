package com.lkn.a11509.democollection.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lkn.a11509.democollection.Bean.DataBean;
import com.lkn.a11509.democollection.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 11509 on 2017/2/28.
 */

public class TestAdapter extends BaseAdapter {
    private Context mContext;
    private List<DataBean> datas;

    public TestAdapter(Context mContext, List<DataBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //Alt+Insert Generate ButterKnife Injections
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.itemTitle.setText(datas.get(position).getTitle());
        holder.itemContent.setText(datas.get(position).getContent());
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_content)
        TextView itemContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
