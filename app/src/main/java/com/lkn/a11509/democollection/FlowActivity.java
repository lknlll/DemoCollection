package com.lkn.a11509.democollection;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.android.flexbox.FlexboxLayout;
import com.lkn.a11509.democollection.Bean.DataBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlowActivity extends BaseActivity {

    int countSign = 0;
    int priority;//0:A 1:B
    int awaitCount = 2;
    int nextSelection;

    @BindView(R.id.flexbox_layout)
    FlexboxLayout flexboxLayout;
    @BindView(R.id.new_member_et)
    EditText newMemberEt;
    @BindView(R.id.add_new_member)
    Button addNewMember;

    List<DataBean> memberList = new ArrayList<DataBean>();
    List<DataBean> signingList = new ArrayList<DataBean>();
    List<DataBean> teamAlphaList = new ArrayList<DataBean>();
    List<DataBean> teamBetaList = new ArrayList<DataBean>();
    @BindView(R.id.random_team_leader)
    Button randomTeamLeader;
    @BindView(R.id.team_leader)
    TextView teamLeader;
    int currentTag;
    TextView currentMember;
    @BindView(R.id.start_game)
    Button startGame;
    @BindView(R.id.signing_player_flex_box)
    FlexboxLayout signingPlayerFlexBox;
    @BindView(R.id.team_alpha)
    TextView teamAlpha;
    @BindView(R.id.team_beta)
    TextView teamBeta;
    @BindView(R.id.remove_member)
    Button removeMember;

    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.title_leader_mode);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_flow);
    }

    @Override
    protected void setUpView() {
        String s = PreferencesUtils.getString(FlowActivity.this, "MemberList", "");
        memberList = JSONObject.parseArray(s, DataBean.class) == null ?
                memberList : JSONObject.parseArray(s, DataBean.class);

        for (int i = 0; i < memberList.size(); i++) {
            flexboxLayout.addView(createNewFlexItemTextView(memberList.get(i), i, R.id.flexbox_layout));
        }
        randomTeamLeader.setEnabled(8 <= countSign);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
    }

    /**
     * 含可以输入文本的弹出框
     */
    private Dialog buildAlertDialog_input() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.input, null);
        builder.setView(v);
        final EditText memberName = (EditText) v.findViewById(R.id.member_name);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setTitle("您点击的是确定按钮!");
                currentMember = (TextView) flexboxLayout.findViewWithTag(currentTag);
                currentMember.setText(memberName.getEditableText().toString());
                for (int i = 0; i < memberList.size(); i++) {
                    if (currentTag == memberList.get(i).getId()) {
                        memberList.get(i).setTitle(memberName.getEditableText().toString());
                    }
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

    /**
     * 动态创建TextView
     *
     * @param book
     * @return
     */
    private TextView createNewFlexItemTextView(final DataBean book, int num, int parentId) {
        final TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(book.getTitle());
        textView.setTextSize(12);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setTag(book.getId());
        switch (parentId) {
            case R.id.flexbox_layout:
                textView.setBackgroundResource(memberList.get(num).isSelected() ? R.drawable.bg_box_selected :
                        R.drawable.bg_box);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentTag = (Integer) textView.getTag();
                        currentMember = (TextView) flexboxLayout.findViewWithTag(currentTag);
                        for (int i = 0; i < memberList.size(); i++) {
                            if (currentTag == memberList.get(i).getId()) {
                                memberList.get(i).setSelected(!memberList.get(i).isSelected());
                                currentMember.setBackgroundResource(memberList.get(i).isSelected() ? R.drawable.bg_box_selected :
                                        R.drawable.bg_box);
                                currentMember.setTextColor(memberList.get(i).isSelected() ?
                                        getResources().getColor(R.color.bottom_menu_btn_bg_common_color ):
                                        getResources().getColor(R.color.colorAccent ));
                            }
                        }
                        countSign = 0;
                        for (DataBean dataBean : memberList) {
                            if (dataBean.isSelected()) {
                                countSign++;
                            }
                        }
                        randomTeamLeader.setEnabled(8 <= countSign);
                        startGame.setVisibility(countSign>0?View.VISIBLE:View.GONE);
                        removeMember.setVisibility(countSign>0?View.VISIBLE:View.GONE);
                    }
                });
                textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        currentTag = (Integer) textView.getTag();
                        buildAlertDialog_input().show();
                        return false;
                    }
                });
                break;
            case R.id.signing_player_flex_box:
                textView.setBackgroundResource(signingList.get(num).isSelected()
                        ? R.drawable.bg_box_selected
                        : R.drawable.bg_box);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (0 == teamAlphaList.size() && 0 == teamBetaList.size()) {
                            return;
                        }
                        int tag = (Integer) textView.getTag();
                        switch (priority) {
                            //A First
                            case 0:
                                if (1 == teamBetaList.size() && 1 == teamAlphaList.size()) {
                                    teamBetaList.add(signingList.get(tag));
                                    teamBeta.setText(teamBeta.getText() + "\n" + signingList.get(tag).getTitle());
                                    teamLeader.setText("请B队为A队选择一名队员");
                                } else if (2 == teamBetaList.size() && 1 == teamAlphaList.size()) {
                                    teamAlphaList.add(signingList.get(tag));
                                    teamAlpha.setText(teamAlpha.getText() + "\n" + signingList.get(tag).getTitle());
                                    teamLeader.setText("请B队选择一名队员");
                                } else if (2 == teamBetaList.size() && 2 == teamAlphaList.size()) {
                                    teamBetaList.add(signingList.get(tag));
                                    teamBeta.setText(teamBeta.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 0;//A
                                    teamLeader.setText("请A队选择一名队员");
                                } else if (awaitCount == 2 && teamAlphaList.size() < teamBetaList.size() && nextSelection == 0) {
                                    awaitCount--;
                                    teamAlphaList.add(signingList.get(tag));
                                    teamAlpha.setText(teamAlpha.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 0;//A
                                    teamLeader.setText("请A队再选择一名队员");
                                } else if (awaitCount == 1 && teamAlphaList.size() == teamBetaList.size() && nextSelection == 0) {
                                    awaitCount--;
                                    teamAlphaList.add(signingList.get(tag));
                                    teamAlpha.setText(teamAlpha.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 1;//B
                                    awaitCount = 2;
                                    teamLeader.setText("请B队选择一名队员");
                                } else if (awaitCount == 2 && teamAlphaList.size() > teamBetaList.size() && nextSelection == 1) {
                                    awaitCount--;
                                    teamBetaList.add(signingList.get(tag));
                                    teamBeta.setText(teamBeta.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 1;//B
                                    teamLeader.setText("请B队再选择一名队员");
                                } else if (awaitCount == 1 && teamAlphaList.size() == teamBetaList.size() && nextSelection == 1) {
                                    teamBetaList.add(signingList.get(tag));
                                    teamBeta.setText(teamBeta.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 0;//A
                                    awaitCount = 2;
                                    teamLeader.setText("请A队选择一名队员");
                                }
                                break;
                            //B First
                            case 1:
                                if (1 == teamAlphaList.size() && 1 == teamBetaList.size()) {
                                    teamAlphaList.add(signingList.get(tag));
                                    teamAlpha.setText(teamAlpha.getText() + "\n" + signingList.get(tag).getTitle());
                                    teamLeader.setText("请A队为B队选择一名队员");
                                } else if (2 == teamAlphaList.size() && 1 == teamBetaList.size()) {
                                    teamBetaList.add(signingList.get(tag));
                                    teamBeta.setText(teamBeta.getText() + "\n" + signingList.get(tag).getTitle());
                                    teamLeader.setText("请A队选择一名队员");
                                } else if (2 == teamAlphaList.size() && 2 == teamBetaList.size()) {
                                    teamAlphaList.add(signingList.get(tag));
                                    teamAlpha.setText(teamAlpha.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 1;//B
                                    teamLeader.setText("请B队选择一名队员");
                                } else if (awaitCount == 2 && teamAlphaList.size() > teamBetaList.size() && nextSelection == 1) {
                                    awaitCount--;
                                    teamBetaList.add(signingList.get(tag));
                                    teamBeta.setText(teamBeta.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 1;//B
                                    teamLeader.setText("请B队再选择一名队员");
                                } else if (awaitCount == 1 && teamAlphaList.size() == teamBetaList.size() && nextSelection == 1) {
                                    awaitCount--;
                                    teamBetaList.add(signingList.get(tag));
                                    teamBeta.setText(teamBeta.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 0;//A
                                    awaitCount = 2;
                                    teamLeader.setText("请A队选择一名队员");
                                } else if (awaitCount == 2 && teamAlphaList.size() < teamBetaList.size() && nextSelection == 0) {
                                    awaitCount--;
                                    teamAlphaList.add(signingList.get(tag));
                                    teamAlpha.setText(teamAlpha.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 0;//A
                                    teamLeader.setText("请A队再选择一名队员");
                                } else if (awaitCount == 1 && teamAlphaList.size() == teamBetaList.size() && nextSelection == 0) {
                                    awaitCount--;
                                    teamAlphaList.add(signingList.get(tag));
                                    teamAlpha.setText(teamAlpha.getText() + "\n" + signingList.get(tag).getTitle());
                                    nextSelection = 1;//B
                                    awaitCount = 2;
                                    teamLeader.setText("请B队选择一名队员");
                                }
                                break;
                        }
                        signingList.remove(tag);
                        for (int i = 0; i < signingList.size(); i++) {
                            signingList.get(i).setId(i);
                        }
                        signingPlayerFlexBox.removeAllViews();
                        for (int i = 0; i < signingList.size(); i++) {
                            signingPlayerFlexBox.addView(createNewFlexItemTextView(signingList.get(i), i, R.id.signing_player_flex_box));
                        }
                        if (0 == signingList.size()) {
                            teamLeader.setText("选人结束");
                            Intent i = new Intent(FlowActivity.this, LineupActivity.class);
                            i.putExtra("A", JSON.toJSONString(teamAlphaList));
                            i.putExtra("B", JSON.toJSONString(teamBetaList));
                            startActivity(i);
                        }
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

    private void randomTeamLeader() {
        int k = 2;
        int n = signingList.size();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = i;
        }
        int[] sample = reservoirSampling(data, k);
        priority = new Random().nextInt(2);
        switch (priority) {
            case 0://A
                teamLeader.setText("请A队为B队选择一名队员");
                break;
            case 1://B
                teamLeader.setText("请B队为A队选择一名队员");
                break;
        }
        teamAlpha.setText(priority == 0
                ? "A队(先行动)：\n" + signingList.get(sample[0]).getTitle() + "(队长)" :
                "A队：\n" + signingList.get(sample[0]).getTitle() + "(队长)");
        teamBeta.setText(priority == 0
                ? "B队：\n" + signingList.get(sample[1]).getTitle() + "(队长)" :
                "B队(先行动)：\n" + signingList.get(sample[1]).getTitle() + "(队长)");
        List<DataBean> removing = new ArrayList<DataBean>();
        removing.add(signingList.get(sample[0]));
        teamAlphaList.add(signingList.get(sample[0]));
        removing.add(signingList.get(sample[1]));
        teamBetaList.add(signingList.get(sample[1]));
        signingList.removeAll(removing);
        for (int i = 0; i < signingList.size(); i++) {
            signingList.get(i).setId(i);
        }
        signingPlayerFlexBox.removeAllViews();
        for (int i = 0; i < signingList.size(); i++) {
            signingPlayerFlexBox.addView(createNewFlexItemTextView(signingList.get(i), i, R.id.signing_player_flex_box));
        }
    }

    public static int[] reservoirSampling(int[] data, int k) {
        if (data == null) {
            return new int[0];
        }
        if (data.length < k) {
            return new int[0];
        }
        int[] sample = new int[k];
        int n = data.length;
        for (int i = 0; i < n; i++) {
            if (i < k) {
                sample[i] = data[i];
            } else {
                int j = new Random().nextInt(i);
                if (j < k) {
                    sample[j] = data[i];
                }
            }
        }
        return sample;
    }

    @OnClick(R.id.add_new_member)
    public void onViewClicked() {
        if (newMemberEt.getEditableText().toString().equals("")) {
            return;
        }
        DataBean dataBean = new DataBean();
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            l.add(memberList.get(i).getId());
        }
        int id =0;
        for(int i = 0;l.contains(i);i++){
            id = i+1;
        }
        dataBean.setId(id);
        dataBean.setTitle(newMemberEt.getEditableText().toString());
        dataBean.setSelected(false);
        memberList.add(dataBean);
        newMemberEt.setText("");
        flexboxLayout.removeAllViews();
        PreferencesUtils.putString(FlowActivity.this, "MemberList", JSON.toJSONString(memberList));
        setUpView();
    }

    @OnClick(R.id.random_team_leader)
    public void onRandomLeaderClicked() {
        if (8 > signingList.size()) {
            return;
        }
        randomTeamLeader();
        randomTeamLeader.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        for (DataBean dataBean : memberList) {
            dataBean.setSelected(false);
        }
        PreferencesUtils.putString(FlowActivity.this, "MemberList", JSON.toJSONString(memberList));
        super.onBackPressed();
    }

    @OnClick(R.id.start_game)
    public void onGameStarted() {
        signingPlayerFlexBox.removeAllViews();
        teamAlphaList.clear();
        teamBetaList.clear();
        signingList.clear();
        countSign = 0;
        awaitCount = 2;
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).isSelected()) {
                DataBean dataBean = new DataBean();
                dataBean.setSelected(false);
                dataBean.setId(signingList.size());
                dataBean.setTitle(memberList.get(i).getTitle());
                signingList.add(dataBean);
            }
        }
        for (int i = 0; i < signingList.size(); i++) {
            signingPlayerFlexBox.addView(createNewFlexItemTextView(signingList.get(i), i, R.id.signing_player_flex_box));
        }
    }

    //删除队员
    @OnClick(R.id.remove_member)
    public void onRemoveMemberClicked() {
        List<DataBean> removing = new ArrayList<DataBean>();
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).isSelected()) {
                removing.add(memberList.get(i));
            }
        }
        memberList.removeAll(removing);
        flexboxLayout.removeAllViews();
        PreferencesUtils.putString(FlowActivity.this, "MemberList", JSON.toJSONString(memberList));
        setUpView();
    }
}
