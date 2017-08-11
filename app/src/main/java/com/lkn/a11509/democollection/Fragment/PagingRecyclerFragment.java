package com.lkn.a11509.democollection.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lkn.a11509.democollection.Bean.DataBean;
import com.lkn.a11509.democollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kLin 11509 on 8/7/2017.
 * email 1150954859@qq.com
 */

public class PagingRecyclerFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    @BindView(R.id.lin_demo_recycler)
    RecyclerView linDemoRecycler;
    Unbinder unbinder;
    private LinearLayoutManager mLinearLayoutManager;

    private int position;
    List<DataBean> dataBeanList = new ArrayList<DataBean>();

    public static PagingRecyclerFragment newInstance(int position) {
        PagingRecyclerFragment f = new PagingRecyclerFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        for (int i = 0; i < position; i++) {
            DataBean dataBean = new DataBean();
            dataBean.setTitle("title" + position);
            dataBean.setContent("Content" + position);
            dataBeanList.add(dataBean);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paging_recycler, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        linDemoRecycler.setAdapter();
        mLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        linDemoRecycler.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
