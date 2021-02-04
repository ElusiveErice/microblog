package com.csu.microblog.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

/**
 * @author xiangpengfei
 * 自定义的CompoundButton，空的类开关控件，使用Selector来控制不同check状态下的背景图
 */
public class ImageSwitch extends CompoundButton {


    public ImageSwitch(Context context) {
        super(context);
        setClickable(true);
    }

    public ImageSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    public ImageSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

}
