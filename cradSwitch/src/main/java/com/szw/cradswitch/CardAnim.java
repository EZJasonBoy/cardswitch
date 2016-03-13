package com.szw.cradswitch;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;

import android.annotation.SuppressLint;
import android.view.View;

public class CardAnim {
    private View view;

    /**
     * 速度
     */
    private float xVelocity;
    private float yVelocity;

    /**
     * 点击位置
     */
    private float downX, downY, upY, upX;

    /**
     * 手指移动放开View 后的位置
     */
    private float upYTrans, upXTrans;

    private ValueAnimator valueAnimator;

    public CardAnim() {
        super();
        // TODO Auto-generated constructor stub
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                if (isRunOffAnim) {
                    drawOffAnim();
                }

                if (isRunZN) {
                    drawZnAnim();
                }

                if (isRunRotaionAnim) {
                    drawRotaionAnim();
                }
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(3000);
    }

    private long drawRoteAnimTime = 0;

    @SuppressLint("NewApi")
    protected void drawRotaionAnim() {
        // TODO Auto-generated method stub
        long time = System.currentTimeMillis() - drawRoteAnimTime;
        drawRoteAnimTime = System.currentTimeMillis();
        if (Math.abs(vRote) > 2) {

            if (vRote > 0) {
                vRote = 2;
            } else {
                vRote = -2;
            }
        }

        double addDegree = time * Math.abs(vRote);

        if (vRote > 0) {
            vRote = vRote - 0.01;
        } else {
            addDegree = addDegree * -1;
            vRote = vRote + 0.01;
        }

        ViewHelper.setRotation(view, (float) (view.getRotation() + addDegree));

    }

    /**
     * 阻尼振动
     * 阻尼运动公式：x(t) = A * exp(-1 * β * t) * cos(sprt(ω^2 - β^2 * t))
     *///1. a 为点击的单摆基点   ab长度为摆长 b为刚体的重心，在单摆运动中可以看做 质点.
    //2. 角A 为单摆的初相位
    //    a
    //    |\
    //    | \
    //    |  \
    //    |___\
    //    c    b
    private double W = 7;
    private double B = 0.5;
    private double vRote;

    @SuppressLint("NewApi")
    protected void drawZnAnim() {
        // TODO Auto-generated method stub
        double time = (System.currentTimeMillis() - runZNStartTime) / 1000d;

        float ax = pivoftX, bx = view.getWidth() / 2, cx = pivoftX;
        float ay = pivoftY, by = view.getHeight() / 2, cy = view.getHeight() / 2;
        double ac = Math.abs(ay - cy);
        double bc = Math.abs(bx - cx);
        double tan = Math.atan(bc / ac);
        // 三角函数计算角A tan(a/c)

        double A = Math.toDegrees(Math.abs(tan));

        //x(t) = A * exp(-1 * β * t) * cos(sprt(ω^2 - β^2 * t))
        double degrees = A * Math.exp(-1 * B * time) * Math.cos(Math.sqrt(W * time));

        if (ax < bx && ay < by) {
            // -- 第一象限
            ViewHelper.setRotation(view, (float) (A - degrees));
        } else if (ax > bx && ay < by) {
            // 第二象限
            ViewHelper.setRotation(view, (float) (-(A - degrees)));
        } else if (ax > bx && ay > by) {
            A = 90 + (90 - A);
            double degrees2 = A * Math.exp(-1 * B * time) * Math.cos(Math.sqrt(W * time));
            ViewHelper.setRotation(view, (float) -(A - degrees2));
        } else {
            A = 90 + (90 - A);
            double degrees2 = A * Math.exp(-1 * B * time) * Math.cos(Math.sqrt(W * time));
            ViewHelper.setRotation(view, (float) (A - degrees2));
        }

        W = W + 0.2;

        vRote = (view.getRotation() - cacheDegree) / (System.currentTimeMillis() - vTime);
        vTime = System.currentTimeMillis();
        cacheDegree = view.getRotation();

    }

    private float cacheDegree = 0;
    private long vTime = 0;

    private boolean isRunRotaionAnim = false;

    /**
     * 绘制掉落效果
     */
    protected void drawOffAnim() {
        // TODO Auto-generated method stub
        double time = (System.currentTimeMillis() - runOffAnimStartTime) / 1000d;
        float heaveY = upYTrans - getHeaveY(time, yVelocity * -1);
        ViewHelper.setTranslationY(view, heaveY);

        float heavex = getHeavex(time, Math.abs(xVelocity));
        if (upX - downX > 0) {
            heavex = upXTrans + heavex;
        } else {
            heavex = upXTrans - heavex;
        }

        if (heavex > 2000 || heaveY > 2000) {
            valueAnimator.cancel();
            if (drawOffAnimListener != null) {
                drawOffAnimListener.onff(view);
            }
        } else {

        }

        ViewHelper.setTranslationX(view, heavex);
//		y 300 400 , 300 ..-200

    }

    private DrawOffAnimListener drawOffAnimListener;

    public void setDrawOffAnimListener(DrawOffAnimListener drawOffAnimListener) {
        this.drawOffAnimListener = drawOffAnimListener;
    }

    public interface DrawOffAnimListener {
        /**
         * 动画已经移出屏幕范围了，可以回收该View了、
         */
        public void onff(View view);
    }

    private float getHeavex(double t, double v) {
        // TODO Auto-generated method stub
        double a = -1000f;
        // s秒以后 他的速度为0，停止运动 s = vo- v/a;
        double s = 0 - v / a;
        if (t > s) {
            t = s;
        }
        //s = vt+at^2/2
        double xPoint = v * t + a * (t * t) / 2;

        return (float) xPoint;

    }

    private float getHeaveY(double t, double v) {
        // TODO Auto-generated method stub
        // 位移公式x=Vot-gt^2/2
        double g = 5000d;
        double y = v * t - g * (t * t) / 2;
        return (float) y;
    }

    private long runOffAnimStartTime = 0;
    private boolean isRunOffAnim = false;

    /**
     * 开始执行 掉落运动
     *
     * @param view
     * @param xVelocity
     * @param yVelocity
     * @param downX
     * @param downY
     * @param upY
     * @param upX
     * @param upxTrans
     * @param upYTrans
     */
    @SuppressLint("NewApi")
    public void runOff(View view, float xVelocity, float yVelocity, float downX, float downY, float upY, float upX,
                       float upxTrans, float upYTrans) {
        this.view = view;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.downX = downX;
        this.downY = downY;
        this.upX = upX;
        this.upY = upY;
        this.upXTrans = upxTrans;
        this.upYTrans = upYTrans;

        isRunZN = false;
        isRunRotaionAnim = true;
        drawRoteAnimTime = System.currentTimeMillis();
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);

        runOffAnimStartTime = System.currentTimeMillis();
        isRunOffAnim = true;

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
    }

    private long runZNStartTime = 0;
    private float pivoftX, pivoftY;
    private boolean isRunZN = false;

    @SuppressLint("NewApi")
    public void runZN(View view, float downX, float downY) {

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
            this.view = view;
            this.downX = downX;
            this.downY = downY;

            pivoftX = view.getPivotX();
            pivoftY = view.getPivotY();

            runZNStartTime = System.currentTimeMillis();
            isRunZN = true;
        }


    }


    public void Stop() {
        valueAnimator.cancel();
    }


}
