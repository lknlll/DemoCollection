package com.lkn.a11509.democollection.Bean;

import com.lkn.a11509.democollection.MenuItemOnClickListener;

/**
 * Created by kLin 11509 on 3/20/2017.
 * email 1150954859@qq.com
 */

public class MenuItem {

    private MenuItemOnClickListener menuItemOnClickListener;
    private String item_name;
    private String text;
    private MenuItemStyle style;

    public MenuItem() {
    }

    /**
     *
     * @param _item_name 菜单项名称
     * @param _text 菜单项显示内容
     * @param _style 菜单类型
     * @param _menuItemOnClickListener 菜单点击回调事件
     */
    public MenuItem(String _item_name, String _text, MenuItemStyle _style, MenuItemOnClickListener _menuItemOnClickListener) {
        this.item_name = _item_name;
        this.text = _text;
        this.style = _style;
        this.menuItemOnClickListener = _menuItemOnClickListener;
    }

    public MenuItemOnClickListener getMenuItemOnClickListener() {
        return menuItemOnClickListener;
    }

    public void setMenuItemOnClickListener(MenuItemOnClickListener menuItemOnClickListener) {
        this.menuItemOnClickListener = menuItemOnClickListener;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MenuItemStyle getStyle() {
        return style;
    }

    public void setStyle(MenuItemStyle style) {
        this.style = style;
    }

    public  enum MenuItemStyle{
        COMMON , STRESS
    }
}
