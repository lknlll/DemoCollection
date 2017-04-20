package com.lkn.a11509.democollection;

import android.util.Log;
import android.view.View;

import com.lkn.a11509.democollection.Bean.MenuItem;
import com.lkn.a11509.democollection.Fragment.BottomMenuFragment;

/**
 * Created by kLin 11509 on 3/20/2017.
 * email 1150954859@qq.com
 */

public abstract class MenuItemOnClickListener implements View.OnClickListener {

    private BottomMenuFragment bottomMenuFragment;
    private MenuItem menuItem;

    public MenuItemOnClickListener(BottomMenuFragment _bottomMenuFragment, MenuItem _menuItem) {
        this.bottomMenuFragment = _bottomMenuFragment;
        this.menuItem = _menuItem;
    }
    private final String TAG = "MenuItemOnClickListener";

    public BottomMenuFragment getBottomMenuFragment() {
        return bottomMenuFragment;
    }

    public void setBottomMenuFragment(BottomMenuFragment bottomMenuFragment) {
        this.bottomMenuFragment = bottomMenuFragment;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    public abstract void onClickMenuItem(View v, MenuItem menuItem);
    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");

        if(bottomMenuFragment != null && bottomMenuFragment.isVisible()) {
            bottomMenuFragment.dismiss();
        }

        this.onClickMenuItem(v, this.menuItem);
    }
}
