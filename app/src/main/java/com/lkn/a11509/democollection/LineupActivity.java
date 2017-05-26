package com.lkn.a11509.democollection;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.android.flexbox.FlexboxLayout;
import com.lkn.a11509.democollection.Bean.DataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LineupActivity extends BaseActivity {

    @BindView(R.id.alpha_flexbox)
    FlexboxLayout alphaFlexbox;
    @BindView(R.id.beta_flexbox)
    FlexboxLayout betaFlexbox;
    @BindView(R.id.alpha_banned_first)
    TextView alphaBannedFirst;
    @BindView(R.id.beta_banned_first)
    TextView betaBannedFirst;
    @BindView(R.id.optional_selection)
    TextView optionalSelection;
    @BindView(R.id.alpha_lineup)
    RecyclerView alphaLineup;
    @BindView(R.id.beta_lineup)
    RecyclerView betaLineup;
    @BindView(R.id.alpha_settle)
    Button alphaSettle;
    @BindView(R.id.beta_settle)
    Button betaSettle;
    @BindView(R.id.open_battle)
    Button openBattle;


    List<DataBean> teamAlphaList = new ArrayList<DataBean>();
    List<DataBean> teamBetaList = new ArrayList<DataBean>();
    List<DataBean> largerTeamList = new ArrayList<DataBean>();
    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.title_lineup);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_lineup);
    }

    @Override
    protected void setUpView() {
        Intent intent = getIntent();
        String s = intent.getStringExtra("A");
        String b = intent.getStringExtra("B");
        teamAlphaList = JSONObject.parseArray(s, DataBean.class) == null ?
                teamAlphaList : JSONObject.parseArray(s, DataBean.class);
        teamBetaList = JSONObject.parseArray(b, DataBean.class) == null ?
                teamBetaList : JSONObject.parseArray(b, DataBean.class);

        for (int i = 0; i < teamAlphaList.size(); i++) {
            teamAlphaList.get(i).setId(i);
            alphaFlexbox.addView(createNewFlexItemTextView(teamAlphaList.get(i),R.id.alpha_flexbox));
        }
        for (int i = 0; i < teamBetaList.size(); i++) {
            teamBetaList.get(i).setId(i);
            betaFlexbox.addView(createNewFlexItemTextView(teamBetaList.get(i),R.id.beta_flexbox));
        }
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        if (0 != (teamAlphaList.size() + teamBetaList.size()) % 2 ){
            largerTeamList = teamAlphaList.size()>teamBetaList.size()?teamAlphaList:teamBetaList;
            optionalSelection.setVisibility(View.VISIBLE);
        }else {
            optionalSelection.setVisibility(View.GONE);
        }
    }

    private Dialog buildAlertDialog_ban(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_ban, null);
        builder.setView(v);
        final FlexboxLayout banFlexbox = (FlexboxLayout) v.findViewById(R.id.ban_flexbox);
        switch (id) {
            case R.id.alpha_banned_first:
                for (int i = 0; i < teamAlphaList.size(); i++) {
                    final DataBean dataBean = teamAlphaList.get(i);
                    teamAlphaList.get(i).setId(i);
                    final TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(teamAlphaList.get(i).getTitle());
                    textView.setTextSize(12);
                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
                    textView.setTag(teamAlphaList.get(i).getId());
                    textView.setBackgroundResource(teamAlphaList.get(i).isBanned() ? R.drawable.bg_box_selected :
                            R.drawable.bg_box);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("name",dataBean.getTitle());
                            Log.e("id",""+dataBean.getId());
                            dataBean.setBanned(!dataBean.isBanned());
                            int bannedCount =0;
                            for (int i = 0; i < teamAlphaList.size(); i++) {
                                if (teamAlphaList.get(i).isBanned()) {
                                    bannedCount ++;
                                }
                            }
                            if (2 < bannedCount) {
                                for (int i = 0; i < teamAlphaList.size(); i++) {
                                    if (1 == teamAlphaList.get(i).getBannedCount()) {
                                        teamAlphaList.get(i).setBanned(false);
                                        teamAlphaList.get(i).setBannedCount(0);
                                    }else if (2 == teamAlphaList.get(i).getBannedCount()){
                                        teamAlphaList.get(i).setBannedCount(1);
                                    }
                                }
                                dataBean.setBannedCount(2);
                            }else {
                                dataBean.setBannedCount(bannedCount);
                            }
                            for (int i1 = 0; i1 < teamAlphaList.size(); i1++) {
                                banFlexbox.getChildAt(i1).setBackgroundResource(teamAlphaList.get(i1).isBanned() ? R.drawable.bg_box_selected :
                                        R.drawable.bg_box);
                            }
                        }
                    });

                    int padding = Utils.dp2px(this, 4);
                    int paddingLeftAndRight = Utils.dp2px(this, 8);
                    ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
                    FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    int margin = Utils.dp2px(this, 6);
                    int marginTop = Utils.dp2px(this, 16);
                    layoutParams.setMargins(margin, marginTop, margin, 0);
                    textView.setLayoutParams(layoutParams);
                    banFlexbox.addView(textView);
                }
                break;
            case R.id.beta_banned_first:
                for (int i = 0; i < teamBetaList.size(); i++) {
                    final DataBean dataBean = teamBetaList.get(i);
                    teamBetaList.get(i).setId(i);
                    final TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(teamBetaList.get(i).getTitle());
                    textView.setTextSize(12);
                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
                    textView.setTag(teamBetaList.get(i).getId());
                    textView.setBackgroundResource(teamBetaList.get(i).isBanned() ? R.drawable.bg_box_selected :
                            R.drawable.bg_box);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("name",dataBean.getTitle());
                            Log.e("id",""+dataBean.getId());
                            dataBean.setBanned(!dataBean.isBanned());
                            int bannedCount =0;
                            for (int i = 0; i < teamBetaList.size(); i++) {
                                if (teamBetaList.get(i).isBanned()) {
                                    bannedCount ++;
                                }
                            }
                            if (2 < bannedCount) {
                                for (int i = 0; i < teamBetaList.size(); i++) {
                                    if (1 == teamBetaList.get(i).getBannedCount()) {
                                        teamBetaList.get(i).setBanned(false);
                                        teamBetaList.get(i).setBannedCount(0);
                                    }else if (2 == teamBetaList.get(i).getBannedCount()){
                                        teamBetaList.get(i).setBannedCount(1);
                                    }
                                }
                                dataBean.setBannedCount(2);
                            }else {
                                dataBean.setBannedCount(bannedCount);
                            }
                            for (int i1 = 0; i1 < teamBetaList.size(); i1++) {
                                banFlexbox.getChildAt(i1).setBackgroundResource(teamBetaList.get(i1).isBanned() ? R.drawable.bg_box_selected :
                                        R.drawable.bg_box);
                            }
                        }
                    });

                    int padding = Utils.dp2px(this, 4);
                    int paddingLeftAndRight = Utils.dp2px(this, 8);
                    ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
                    FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    int margin = Utils.dp2px(this, 6);
                    int marginTop = Utils.dp2px(this, 16);
                    layoutParams.setMargins(margin, marginTop, margin, 0);
                    textView.setLayoutParams(layoutParams);
                    banFlexbox.addView(textView);
                }
                break;
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setTitle("您点击的是确定按钮!");
                alphaFlexbox.removeAllViews();
                betaFlexbox.removeAllViews();
                for (int i = 0; i < teamAlphaList.size(); i++) {
                    teamAlphaList.get(i).setId(i);
                    alphaFlexbox.addView(createNewFlexItemTextView(teamAlphaList.get(i),R.id.alpha_flexbox));
                }
                for (int i = 0; i < teamBetaList.size(); i++) {
                    teamBetaList.get(i).setId(i);
                    betaFlexbox.addView(createNewFlexItemTextView(teamBetaList.get(i),R.id.beta_flexbox));
                }
                String s = "";
                switch (id) {
                    case R.id.alpha_banned_first:
                        for (int i = 0; i < teamAlphaList.size(); i++) {
                            if (teamAlphaList.get(i).isBanned()) {
                                s = s.equals("")?teamAlphaList.get(i).getTitle():s + "和" + teamAlphaList.get(i).getTitle();
                            }
                        }
                        s = getString(R.string.banned_couple,s);
                        alphaBannedFirst.setText(s);
                        alphaBannedFirst.setTextColor(getResources().getColor(R.color.color_red));
                        break;
                    case R.id.beta_banned_first:
                        for (int i = 0; i < teamBetaList.size(); i++) {
                            if (teamBetaList.get(i).isBanned()) {
                                s = s.equals("")?teamBetaList.get(i).getTitle():s + "和" + teamBetaList.get(i).getTitle();
                            }
                        }
                        s = getString(R.string.banned_couple,s);
                        betaBannedFirst.setText(s);
                        betaBannedFirst.setTextColor(getResources().getColor(R.color.color_red));
                        break;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setTitle("您点击的是取消按钮!");
            }
        });
        return builder.create();
    }

    private TextView createNewFlexItemTextView(final DataBean book, int id) {
        final TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(book.getTitle());
        textView.setTextSize(12);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setTag(book.getId());
        switch (id) {
            case R.id.alpha_flexbox:
            case R.id.beta_flexbox:
                textView.setBackgroundResource(book.isSelected() ? R.drawable.bg_box_selected :
                        R.drawable.bg_box);
                if (book.isBanned()) {
                    textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("name",book.getTitle());
                        Log.e("id",""+book.getId());
                    }
                });
                break;

        }
        int padding = Utils.dp2px(this, 4);
        int paddingLeftAndRight = Utils.dp2px(this, 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = Utils.dp2px(this, 6);
        int marginTop = Utils.dp2px(this, 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    @OnClick({R.id.alpha_banned_first, R.id.beta_banned_first, R.id.optional_selection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alpha_banned_first:
                buildAlertDialog_ban(R.id.alpha_banned_first).show();
                break;
            case R.id.beta_banned_first:
                buildAlertDialog_ban(R.id.beta_banned_first).show();
                break;
            case R.id.optional_selection:
                buildAlertDialog_ban(R.id.beta_banned_first).show();
                break;
        }
    }

    @OnClick(R.id.open_battle)
    public void onOpenBattleClicked() {
        alphaLineup.setVisibility(View.VISIBLE);
        betaLineup.setVisibility(View.VISIBLE);
    }
}
