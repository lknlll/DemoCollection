package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.lkn.a11509.democollection.Adapter.RecyclerViewAdapter;
import com.lkn.a11509.democollection.Adapter.StaggeredRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StaggeredMainActivity extends BaseActivity {

    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private StaggeredRecyclerViewAdapter mRecyclerViewAdapter = null;
    private List<String> mListData = new ArrayList<String>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_recycler_demo);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        for (int i = 'A'; i <= 'z'; i++) {
            mListData.add((char) i + "");
        }
        mRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(this,mListData);
        //设置adapter
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //设置ListView垂直如何显示
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        //创建布局管理器
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //添加监听事件
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(StaggeredMainActivity.this,"onClick瀑布流",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(StaggeredMainActivity.this,"onLongClick瀑布流",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
