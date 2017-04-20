package com.lkn.a11509.democollection.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lkn.a11509.democollection.Bean.MenuItem;
import com.lkn.a11509.democollection.MenuItemOnClickListener;
import com.lkn.a11509.democollection.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kLin 11509 on 3/20/2017.
 * email 1150954859@qq.com
 */

public class MenuItemAdapter extends BaseAdapter {

    private Context context;//运行上下文

    private LayoutInflater listContainer;  //视图容器

    private List<MenuItem> menuItems;

    public MenuItemAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.listContainer = LayoutInflater.from(context);
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return this.menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= menuItems.size() || position < 0) {
            return null;
        } else {
            return menuItems.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = listContainer.inflate(R.layout.menu_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        MenuItem menuItem = menuItems.get(position);
        holder.textView.setText(menuItem.getText());
        if(menuItems.size() == 1) {
            holder.textView.setBackgroundResource(R.drawable.bottom_menu_btn_selector);
        } else if(position == 0) {
            holder.textView.setBackgroundResource(R.drawable.bottom_menu_top_btn_selector);
        } else if(position < menuItems.size() - 1) {
            holder.textView.setBackgroundResource(R.drawable.bottom_menu_mid_btn_selector);
        } else {
            holder.textView.setBackgroundResource(R.drawable.bottom_menu_bottom_btn_selector);
        }
        if(menuItem.getStyle() == MenuItem.MenuItemStyle.COMMON) {
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.bottom_menu_btn_text_commom_color));
        } else {
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.bottom_menu_btn_text_stress_color));
        }
        MenuItemOnClickListener _menuItemOnClickListener =menuItem.getMenuItemOnClickListener();
        if(_menuItemOnClickListener != null) {
            holder.textView.setOnClickListener(_menuItemOnClickListener);
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.menu_item)
        TextView textView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
