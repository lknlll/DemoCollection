package com.lkn.a11509.democollection.Widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.lkn.a11509.democollection.R;

/**
 * Created by kLin 11509 on 4/24/2017.
 * email 1150954859@qq.com
 */

public class LoadCircleView extends View {

    /**
     * 第一个动画的索引
     */
    private int changeIndex = 0;

    /**
     * 圆的颜色值
     */
    private int[] colors = new int[]{
            getResources().getColor(R.color.color_red),
            getResources().getColor(R.color.color_blue),
            getResources().getColor(R.color.color_black)};

    /**
     * 偏移量
     */
    private Float maxWidth = 50f;

    /**
     * 圆的半径 默认10f
     */
    private Float circleRadius = 10f;

    /**
     * 当前偏移的X坐标
     */
    private Float currentX = 0f;

    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 属性动画
     */
    private ValueAnimator valueAnimator;
    /**
     * 持续时间
     */
    private int duration = 800;


    /**
     * 默认的布局文件调用的是两个参数的构造方法
     * 让所有的构造调用我们的三个参数的构造
     * 在三个参数的构造中获得自定义属性
     */
    public LoadCircleView(Context context) {
        this(context,null);
    }

    public LoadCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (null != attrs) {
            //获得对控件自定义属性集的引用
            TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.LoadCircleView);
            circleRadius = typedArray.getFloat(R.styleable.LoadCircleView_circle_radius,circleRadius);
            duration = typedArray.getInt(R.styleable.LoadCircleView_duration,duration);
            typedArray.recycle();//记得回收
        }
        startAnimator();
    }

    /**
     * 位移动画
     */
    private void startAnimator(){
        valueAnimator = ValueAnimator.ofFloat(0f,maxWidth,0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (Float)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                changePoint(changeIndex);
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * 先执行动画的目标和中间停止的动画目标交换
     *
     * @param index 最先执行的动画的索引
     */
    private void changePoint(int index) {
        int temp = colors[2];
        colors[2] = colors[index];
        colors[index] = temp;
        changeIndex = (index == 0) ? 1 : 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        /**左边圆**/
        paint.setColor(colors[0]);
        canvas.drawCircle(centerX - currentX, centerY, circleRadius, paint);
        /**右边圆**/
        paint.setColor(colors[1]);
        canvas.drawCircle(centerX + currentX, centerY, circleRadius, paint);
        /**中间圆**/
        paint.setColor(colors[2]);
        canvas.drawCircle(centerX, centerY, circleRadius, paint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        valueAnimator.cancel();
    }
}
