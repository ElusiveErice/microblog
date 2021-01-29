package com.csu.microblog.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csu.microblog.R;

/**
 * @author xiangpengfei
 */
public class SimpleViewBuilder {

    /**
     * 简单吐司显示
     *
     * @param context 要将吐司显示在的上下文上
     * @param message 指定的文本内容
     * @param time    显示的时间长短 Toast.LENGTH_LONG 或 Toast.LENGTH_SHORT
     */
    public static void newToast(Context context, String message, int time) {
        Toast toast = Toast.makeText(context, message, time);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    /**
     * 生产一个纯文本通知框
     *
     * @param context 上下文对象
     * @param message 指定的文本内容
     * @return 返回dialog对象
     */
    public static Dialog newMessageDialog(Context context, String message, boolean canceledOnTouchOutside) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_notification, null, false);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setText(message);
        return newDialog(context, view, canceledOnTouchOutside);
    }

    public static Dialog newConfirmDialog(Context context, String message) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_confirm, null, false);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setText(message);
        Dialog dialog = newDialog(context, view, true);
        Button btConfirm = (Button) view.findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(v -> dialog.dismiss());
        return dialog;
    }

    /**
     * 生产一个自定义View的Dialog
     *
     * @param context                上下文对象
     * @param view                   指定的视图
     * @param canceledOnTouchOutside 是否点击背景自动关闭dialog
     * @return 返回dialog对象
     */
    public static Dialog newDialog(Context context, View view, boolean canceledOnTouchOutside) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return alertDialog;
    }
}
