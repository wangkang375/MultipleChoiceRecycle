package com.example.wang.multiplechoicerecycle;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 描述:
 * <p>
 * Created by wang on 2018/2/2.
 */

public class PrintTextView extends AppCompatTextView {

    private String mContentText;
    private Timer mTimer;
    private char[] mChars;

    public PrintTextView(Context context) {
        super(context);
        init(context);
    }

    public PrintTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PrintTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private double mDouble = 1.00;

    private void init(Context context) {
        mContentText = getText().toString();
        mChars = mContentText.toCharArray();
        mTimer = new Timer();
        boolean naN = Double.isNaN(mDouble);
        boolean infinite = Double.isInfinite(mDouble);
        double v = mDouble % 2;
        System.out.println("===========" + naN + infinite + v);
    }

    int prossbar;

    public void startPrint(long delay, long perid) {

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        int length = mContentText.length();
                        if (prossbar <= length - 1) {
                            prossbar++;
                            System.out.println("=======" + prossbar);
                            setText(mContentText.substring(0, prossbar));
                        } else {

                        }
                    }
                });

            }
        }, delay, perid);

    }
}
