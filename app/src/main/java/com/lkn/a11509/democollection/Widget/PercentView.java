package com.lkn.a11509.democollection.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lkn.a11509.democollection.R;
import com.lkn.a11509.democollection.Utils;

/**
 * Created by kLin 11509 on 6/15/2017.
 * email 1150954859@qq.com
 */

public class PercentView extends View{

    private Paint paint;    //画笔
    private RectF rectF;    //矩形
    private int w;          //View宽高
    private int h;
    private Context context;

    public PercentView(Context context) {
        super(context);
        this.context = context;
        initPaint();
    }

    public PercentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
    }

    public PercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    //初始化画笔
    private void initPaint() {
        paint = new Paint();
        //设置画笔默认颜色
        paint.setColor(Color.WHITE);
        //设置画笔模式：填充
        paint.setStyle(Paint.Style.FILL);
        //
        paint.setTextSize(30);
        //初始化区域
        rectF = new RectF();
    }
    //确定View大小
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;     //获取宽高
        this.h = h;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(w / 2, h / 2);             //将画布坐标原点移到中心位置
        float currentStartAngle = -90;                //起始角度
        float r = (float) (Math.min(w, h) / 2- Utils.dp2px(context,3));     //饼状图半径(取宽高里最小的值)
        rectF.set(-r, -r, r, r);                    //设置将要用来画扇形的矩形的轮廓
        paint.setColor(getResources().getColor(R.color.color_black));
        canvas.drawCircle(0,0,r+Utils.dp2px(context,3),paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(0,0,r+Utils.dp2px(context,1),paint);
        paint.setColor(Color.BLUE);
        //绘制扇形(通过绘制圆弧)
        canvas.drawArc(rectF, currentStartAngle, 90f, true, paint);
    }
}
