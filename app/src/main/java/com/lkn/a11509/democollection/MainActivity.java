package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lkn.a11509.democollection.Adapter.TestAdapter;
import com.lkn.a11509.democollection.Bean.DataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.buk_tv)
    TextView bukTv;
    @BindView(R.id.buk_btn)
    Button bukBtn;
    @BindView(R.id.buk_lv)
    ListView bukLv;

    private List<DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //layout处右击或者Alt + Insert Generate ButterKnife Injections
        setContentView(R.layout.activity_main);
        //绑定Activity
        ButterKnife.bind(this);
        initListViewData();
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
                break;
        }
    }

    //注意：这个方法返回boolean类型
    @OnLongClick({R.id.buk_tv})
    public boolean onLongClick(View view){
        switch (view.getId()){
            case R.id.buk_tv:
                break;
        }
        return true;
    }
}
