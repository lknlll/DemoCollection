package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lkn.a11509.democollection.Adapter.RecyclerViewAdapter;
import com.lkn.a11509.democollection.Widget.BGARefresh.DefineBAGRefreshWithLoadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class DefineLoadWithRefreshActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @BindView(R.id.fml_title)
    TextView mTitle;
    @BindView(R.id.fml_others_type)
    TextView fmlOthersType;
    @BindView(R.id.define_bga_refresh_with_load_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.define_bga_refresh_with_load)
    BGARefreshLayout mBGARefreshLayout;
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    /** 设置一共请求多少次数据 */
    private int ALLSUM = 0;
    /** 数据 */
    private List<String> mListData = new ArrayList<String>();
    /** 数据填充adapter */
    private RecyclerViewAdapter mRecyclerViewAdapter = null;

    /** 一次加载数据的条数 */
    private int DATASIZE = 10;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_define_refresh_with_load);
    }

    @Override
    protected void setUpView() {
        mTitle.setText("自定义刷新和加载更多样式");
        //设置刷新和加载监听回调
        mBGARefreshLayout.setDelegate(this);
        setRecyclerView();
        setBgaRefreshLayout();
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(this , true , true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("自定义加载更多");
    }

    /** 设置RecyclerView的布局方式 */
    private void setRecyclerView(){
        //垂直ListView显示方式
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /** 进入页面首次加载数据 */
    @Override
    protected void onStart() {
        super.onStart();
        mBGARefreshLayout.beginRefreshing();
        onBGARefreshLayoutBeginRefreshing(mBGARefreshLayout);
    }

    /** 数据填充 */
    private void setRecyclerCommAdapter() {
        mRecyclerViewAdapter = new RecyclerViewAdapter(this , mListData);
        mRecycler.setAdapter(mRecyclerViewAdapter);
        //点击事件
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(DefineLoadWithRefreshActivity.this, "onClick  " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(DefineLoadWithRefreshActivity.this, "onLongClick  " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 添加假数据
     * */
    private void setData() {
        for(int i = 0 ; i < DATASIZE ; i++){
            mListData.add("第" + (ALLSUM * 10 + i) +"条数据");
        }
        if(ALLSUM == 0){
            setRecyclerCommAdapter();
        }else{
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /** 模拟请求网络数据 */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mListData.clear();
                    setData();
                    mBGARefreshLayout.endRefreshing();
                    break;
                case 1:
                    setData();
                    mBGARefreshLayout.endLoadingMore();
                    break;
                case 2:
                    mBGARefreshLayout.endLoadingMore();
                    break;
                default:
                    break;

            }
        }
    };

    /** 刷新 */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("自定义加载更多");
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        ALLSUM = 0;
        handler.sendEmptyMessageDelayed(0 , 2000);
    }

    /** 加载 */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if(ALLSUM == 2){
            /** 设置文字 **/
            mDefineBAGRefreshWithLoadView.updateLoadingMoreText("没有更多数据");
            /** 隐藏图片 **/
            mDefineBAGRefreshWithLoadView.hideLoadingMoreImg();
            handler.sendEmptyMessageDelayed(2 , 2000);
            return true;
        }
        ALLSUM++;
        handler.sendEmptyMessageDelayed(1 , 2000);
        return true;
    }
}
