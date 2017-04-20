package com.lkn.a11509.democollection.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.lkn.a11509.democollection.R;

/**
 * Created by kLin 11509 on 3/23/2017.
 * email 1150954859@qq.com
 */

public class SlidingButtonView extends HorizontalScrollView {

    private Boolean once = false;

    private TextView mTextView_Delete;
    private Boolean isOpen = false;
    private int mScrollWidth;
    private IonSlidingButtonListener mIonSlidingButtonListener;
    public SlidingButtonView(Context context) {
        super(context);
        Log.i("TAG", "1");
    }

    public SlidingButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("TAG", "2");
    }

    public SlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //在任何情况下都不可以OverScroll(边界回弹)
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        Log.i("TAG", "3");
    }

    /**
     * 关闭菜单
     */
    public void closeMenu()
    {
        Log.i("TAG" , "18");
        if (!isOpen){
            Log.i("TAG" , "19");
            return;
        }
        Log.i("TAG" , "20");
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }
    public void setSlidingButtonListener(IonSlidingButtonListener listener){
        Log.i("TAG" , "设置SlidingButtonListener  21");
        mIonSlidingButtonListener = listener;
    }

    /**
     * 打开菜单
     */
    public void openMenu()
    {
        Log.i("TAG" , "15");
        if (isOpen){
            Log.i("TAG" , "16");
            return;
        }
        Log.i("TAG" , "17");
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        mIonSlidingButtonListener.onMenuIsOpen(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("TAG", "4");
        if(!once){
            mTextView_Delete = (TextView) findViewById(R.id.item_sliding_delete);
            once = true;
        }Log.i("TAG" , "5");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i("TAG", "6");
        if(changed){
            this.scrollTo(0,0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            mScrollWidth = mTextView_Delete.getWidth();
            Log.i("TAG" , "7");
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("TAG", "11");
        mTextView_Delete.setTranslationX(l - mScrollWidth);
    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx(){

        Log.i("TAG" , "滚动滚动条  12");
        if (getScrollX() >= (mScrollWidth/2)) {
            /*设置视图平滑滚动后的位置。这将引起onScrollChanged(int,int,int,int)的调用，同时使此视图失效。
            此版本同时将滚动锁定于子视图的范围。
            参数
            x 要滚动到的x位置
            y 要滚动到的y位置*/
            this.smoothScrollTo(mScrollWidth,0);
            isOpen = true;
            mIonSlidingButtonListener.onMenuIsOpen(this);
            Log.i("TAG", "滚动滚动条  展开  13" + isOpen);
        }else{
            Log.i("TAG" , "滚动滚动条  关闭   14");
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("TAG" , "18");

        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.i("TAG" , "ACTION_MOVE  8");
                mIonSlidingButtonListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.i("TAG" , "ACTION_CANCEL  10");
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface IonSlidingButtonListener{
        void onMenuIsOpen(View view);
        void onDownOrMove(SlidingButtonView slidingButtonView);
    }
}
