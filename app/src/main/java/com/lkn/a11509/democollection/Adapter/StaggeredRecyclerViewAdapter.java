package com.lkn.a11509.democollection.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kLin 11509 on 3/10/2017.
 * email 1150954859@qq.com
 */

public class StaggeredRecyclerViewAdapter extends RecyclerViewAdapter {

    private List<Integer> mHeights;

    public StaggeredRecyclerViewAdapter(Context context, List<String> data) {
        super(context, data);
        mHeights = new ArrayList<Integer>();
        for(int i = 0 ; i < mListData.size() ; i++){
            mHeights.add((int) (100+Math.random()*300));
            Log.i("TAG",mHeights.get(i)+"");
        }
    }

    @Override
    public void onBindViewHolder(StaggeredRecyclerViewAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = mHeights.get(position);
        holder.itemView.setLayoutParams(lp);

        holder.itemContent.setText(mListData.get(position));
        setListener(holder);
    }
}
