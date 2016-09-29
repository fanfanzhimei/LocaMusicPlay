package com.zhi.www.locamusicplay;

import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/29.
 */
public class ToastUtils {

    public static void showText(String text){
        Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
