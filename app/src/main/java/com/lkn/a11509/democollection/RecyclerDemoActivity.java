package com.lkn.a11509.democollection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lkn.a11509.democollection.Adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecyclerDemoActivity extends BaseActivity {

    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;
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
        //填充数据
        mRecyclerViewAdapter = new RecyclerViewAdapter(RecyclerDemoActivity.this,mListData);
        myRecyclerView.setAdapter(mRecyclerViewAdapter);
        //设置listView垂直如何显示
        mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //创建默认的线性布局LinearLayout
        myRecyclerView.setLayoutManager(mLinearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        myRecyclerView.setHasFixedSize(true);
        //添加动画
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //点击事件
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(RecyclerDemoActivity.this,"onClick  "+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(RecyclerDemoActivity.this,"onLongClick  "+position,Toast.LENGTH_SHORT).show();
            }
        });

        //绘制分割线
        myRecyclerView.addItemDecoration(new DividerItemDecoration(RecyclerDemoActivity.this, DividerItemDecoration.VERTICAL));/*VERTICAL_LIST*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            //添加布局
            case R.id.action_add:
                mRecyclerViewAdapter.add(1);
                break;
            //移除布局
            case R.id.action_delete:
                mRecyclerViewAdapter.delete(1);
                break;
            //ListView
            case R.id.action_listview:
                myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            //GridView
            case R.id.action_gridview:
                myRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
                break;
            //水平GridVIew
            case R.id.action_hor_gridview:
                myRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL));
                break;
            //瀑布流
            case R.id.action_staggered:
                Intent intent = new Intent(RecyclerDemoActivity.this,StaggeredMainActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
