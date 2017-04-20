package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.lkn.a11509.democollection.Bean.DataBean;

import butterknife.BindView;

public class FlowActivity extends BaseActivity {

    @BindView(R.id.flexbox_layout)
    FlexboxLayout flexboxLayout;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_flow);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        String[] tags = {"婚姻育儿", "散文", "设计", "上班这点事儿", "影视天堂", "大学生活", "美人说", "运动和健身", "工具癖", "生活家", "程序员", "想法", "短篇小说", "美食", "教育", "心理", "奇思妙想", "美食", "摄影"};
        for (int i = 0; i < tags.length; i++) {
            DataBean model = new DataBean();
            model.setId(i);
            model.setTitle(tags[i]);
            flexboxLayout.addView(createNewFlexItemTextView(model));
        }
    }

    /**
     * 动态创建TextView
     * @param book
     * @return
     */
    private TextView createNewFlexItemTextView(final DataBean book) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setText(book.getTitle());
        textView.setTextSize(12);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
//        textView.setBackgroundResource(R.drawable.tag_states);
        textView.setTag(book.getId());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", book.getTitle());
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
        return textView;
    }
}
