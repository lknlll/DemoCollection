package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkn.a11509.democollection.Adapter.TestAdapter;
import com.lkn.a11509.democollection.Bean.DataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.buk_tv)
    TextView bukTv;
    @BindView(R.id.buk_btn)
    Button bukBtn;
    @BindView(R.id.buk_lv)
    ListView bukLv;

    private List<DataBean> data;

    @Override
    protected void setUpContentView() {
        //layout处右击或者Alt + Insert Generate ButterKnife Injections
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setUpView() {
        initListViewData();
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.app_name);
    }

    private void initListViewData(){
        data = new ArrayList<>();
        for (int i = 0; i<6;i++){
            DataBean bean = new DataBean();
            bean.setTitle(getResources().getString(R.string.app_name)+i);
            bean.setContent(getResources().getString(R.string.app_name));
            data.add(bean);
        }
        bukLv.setAdapter(new TestAdapter(this,data));
    }

    @OnClick({R.id.buk_tv, R.id.buk_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buk_tv:
                break;
            case R.id.buk_btn:
                Toast.makeText(this, R.id.buk_btn+"", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //注意：这个方法返回boolean类型
    @OnLongClick({R.id.buk_tv})
    public boolean onLongClick(View view){
        switch (view.getId()){
            case R.id.buk_tv:
                //返回被调用方法名
                Toast.makeText(this, new Throwable().getStackTrace()[0].getMethodName(), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @OnItemClick({R.id.buk_lv})
    public void onItemClick(int position){
        switch (position) {
            case 0:
                Toast.makeText(this, getString(R.string.main_activity_position)
                        + position + getString(R.string.module_name_recycler), Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.this,RecyclerDemoActivity.class,null,false);
                break;
        }
    }

    //注意：这个方法返回boolean类型
    @OnItemLongClick({R.id.buk_lv})
    public boolean onItemLongClick(View view){
        Toast.makeText(this, "你要不要再按久点！", Toast.LENGTH_SHORT).show();
        return true;
    }
}
