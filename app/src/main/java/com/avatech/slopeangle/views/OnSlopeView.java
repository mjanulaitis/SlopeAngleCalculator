package com.avatech.slopeangle.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.avatech.slopeangle.R;

//Draws a circle with a line that runs through. When the phone is in an upright
//orientation the line simulates flat ground.  Uses databinding to automatically
//update the angle of the line.
public class OnSlopeView extends View
{
    private static final int BUF = 4;

    private float angle;
    public float radians;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public OnSlopeView(Context context)
    {
        super(context);   // TODO Auto-generated constructor stub
    }

    public OnSlopeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);   // TODO Auto-generated constructor stub
    }

    public OnSlopeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);   // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int r;
        int centerW = w / 2;
        int centerH = h / 2;
        if (w > h)
        {
            r = centerH;
        }
        else
        {
            r = centerW;
        }

        r -= BUF;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawCircle(centerW, centerH, r, paint);
        paint.setColor(getResources().getColor(R.color.colorAccent));

        int x = (int) (r * Math.cos(angle));
        int y = (int) (r * Math.sin(angle));
        canvas.drawLine(centerW - x, centerH - y, centerW + x, centerH + y, paint);
    }

    public void setRadians(float value)
    {
        this.angle = value;
        invalidate();
    }
}
