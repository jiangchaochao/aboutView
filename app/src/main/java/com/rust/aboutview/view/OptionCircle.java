package com.rust.aboutview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class OptionCircle extends View {

    private final Paint paint;
    private final Context context;
    int radius = -1;
    int centerOffsetX = 0;
    int centerOffsetY = 0;
    int colorCircle;
    int colorBackground = Color.WHITE;
    int colorText;
    String textCircle = "";

    public OptionCircle(Context context) {
        this(context, null);
    }

    public OptionCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        colorCircle = Color.argb(205, 245, 2, 51);
        colorText = colorCircle;
    }

    public void setRadius(int r) {
        this.radius = r;
    }

    public void setCenterOffset(int x, int y) {
        this.centerOffsetX = x;
        this.centerOffsetY = y;
    }

    public void setColorCircle(int c) {
        this.colorCircle = c;
    }

    public void setColorText(int c) {
        this.colorText = c;
    }

    public void setColorBackground(int c) {
        this.colorBackground = c;
    }

    public void setText(String s) {
        this.textCircle = s;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int innerCircle = 86;
        if (radius > 0) {
            innerCircle = dip2px(context, radius); //set inner radius
        }

        // draw circle
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(colorCircle);
        this.paint.setStrokeWidth(1.5f);
        canvas.drawCircle(center + centerOffsetX, center + centerOffsetY, innerCircle, this.paint);

        // draw text
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setStrokeWidth(1);
        this.paint.setTextSize(22);
        this.paint.setTypeface(Typeface.MONOSPACE);
        this.paint.setColor(colorText);
        this.paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        canvas.drawText(textCircle, center + centerOffsetX,
                center + centerOffsetY - (fontMetrics.top + fontMetrics.bottom) / 2, this.paint);
        super.onDraw(canvas);
    }

    /**
     * convert dp to px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
