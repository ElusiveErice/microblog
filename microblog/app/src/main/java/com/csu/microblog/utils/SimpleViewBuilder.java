package com.csu.microblog.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.csu.microblog.R;

public class SimpleViewBuilder {

    public static void newToast(Context context, String message, int time) {
        Toast toast = Toast.makeText(context, message, time);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    /**
     * 生产一个纯文本通知框
     *
     * @param context
     * @param message
     * @return
     */
    public static Dialog newMessageDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_notification, null, false);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setText(message);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    /**
     * 生产一个自定义View的Dialog
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog newDialog(Context context, View view) {
        //TODO
        //完成这个方法
        return null;
    }
}
