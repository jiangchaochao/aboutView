package com.rust.aboutview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.rust.aboutview.view.OptionCircle;

public class CirclesActivity extends Activity {

    private static final int UPDATE_ALL_CIRCLE = 99;

    OptionCircle centerCircle;
    OptionCircle circle0;
    OptionCircle circle1;

    CircleHandler handler = new CircleHandler(this);

    /**
     * Handler : update circles UI
     */
    static class CircleHandler extends Handler {
        CirclesActivity activity;
        boolean zoomDir = true;
        int r = 86;

        int moveDir = 0;// 4 directions : 0 ~ 3
        int circle1_x = 0;// offset value
        int circle1_y = 0;

        CircleHandler(CirclesActivity a) {
            activity = a;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_ALL_CIRCLE: {
                    if (zoomDir) {
                        r++;
                        if (r >= 99) zoomDir = false;
                    } else {
                        r--;
                        if (r <= 86) zoomDir = true;
                    }
                    activity.circle0.invalidate();
                    activity.circle0.setRadius(r);
                    calOffsetX();
                    activity.circle1.invalidate();
                    activity.circle1.setCenterOffset(circle1_x, circle1_y);
                }
            }
        }

        private void calOffsetX() {
            if (moveDir == 0) {
                circle1_x--;
                circle1_y++;
                if (circle1_x <= -6) moveDir = 1;
            }
            if (moveDir == 1) {
                circle1_x++;
                circle1_y++;
                if (circle1_x >= 0) moveDir = 2;
            }
            if (moveDir == 2) {
                circle1_x++;
                circle1_y--;
                if (circle1_x >= 6) moveDir = 3;
            }
            if (moveDir == 3) {
                circle1_x--;
                circle1_y--;
                if (circle1_x <= 0) moveDir = 0;
            }
        }
    }

    class UpdateCircles implements Runnable {

        @Override
        public void run() {
            while (true) {
                Message message = new Message();
                message.what = UPDATE_ALL_CIRCLE;
                handler.sendEmptyMessage(message.what);
                try {
                    Thread.sleep(160); // pause
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_choose);
        centerCircle = (OptionCircle) findViewById(R.id.center_circle);
        circle0 = (OptionCircle) findViewById(R.id.circle_0);
        circle1 = (OptionCircle) findViewById(R.id.circle_1);

        centerCircle.setRadius(69);
        centerCircle.setColorText(Color.BLUE);
        centerCircle.setColorCircle(Color.BLUE);
        centerCircle.setText("点击圈圈");

        circle0.setColorText(Color.RED);
        circle0.setText("RED");

        circle1.setColorCircle(Color.GREEN);
        circle1.setColorText(Color.GREEN);
        circle1.setText("Green");
        circle1.setRadius(95);

        circle0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Thread t = new Thread(new UpdateCircles());
        t.start();
    }
}
