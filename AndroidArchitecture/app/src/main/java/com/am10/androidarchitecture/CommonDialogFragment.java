package com.am10.androidarchitecture;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.EventListener;

public class CommonDialogFragment extends DialogFragment {
    private DialogListener listener = null;
    public static final String DIALOG_TAG = "CommonDialogFragment";
    private static final String ARGUMENTS_TITLE = "ARGUMENTS_TITLE";
    private static final String ARGUMENTS_MESSAGE = "ARGUMENTS_MESSAGE";
    private static final String ARGUMENTS_POSITIVE_TITLE = "ARGUMENTS_POSITIVE_TITLE";
    private static final String ARGUMENTS_NEGATIVE_TITLE = "ARGUMENTS_NEGATIVE_TITLE";
    private static final String ARGUMENTS_SHOW_NEGATIVE_FLAG = "ARGUMENTS_SHOW_NEGATIVE_FLAG";

    public static CommonDialogFragment newInstance(String title, String message,
                                                   String positiveTitle, String negativeTitle,
                                                   boolean isShowNegative) {
        CommonDialogFragment frag = new CommonDialogFragment();
        frag.setCancelable(false);
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENTS_TITLE, title);
        bundle.putString(ARGUMENTS_MESSAGE, message);
        bundle.putString(ARGUMENTS_POSITIVE_TITLE, positiveTitle);
        bundle.putString(ARGUMENTS_NEGATIVE_TITLE, negativeTitle);
        bundle.putBoolean(ARGUMENTS_SHOW_NEGATIVE_FLAG, isShowNegative);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = getArguments().getString(ARGUMENTS_TITLE);
        String message = getArguments().getString(ARGUMENTS_MESSAGE);
        String positiveTitle = getArguments().getString(ARGUMENTS_POSITIVE_TITLE);
        String negativeTitle = getArguments().getString(ARGUMENTS_NEGATIVE_TITLE);
        boolean isShowNegative = getArguments().getBoolean(ARGUMENTS_SHOW_NEGATIVE_FLAG);
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.common_dialog, null);
        builder.setView(dialogView);

        TextView titleText = dialogView.findViewById(R.id.textView_title);
        titleText.setText(title);

        TextView messageText = dialogView.findViewById(R.id.textView_message);
        messageText.setMovementMethod(ScrollingMovementMethod.getInstance());
        messageText.setText(message);

        Button positiveButton = dialogView.findViewById(R.id.button_positive);
        positiveButton.setText(positiveTitle);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPositiveClick();
                }
                dismiss();
            }
        });

        Button negativeButton = dialogView.findViewById(R.id.button_negative);
        if (isShowNegative) {
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setText(negativeTitle);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onNegativeClick();
                    }
                    dismiss();
                }
            });
        } else {
            negativeButton.setVisibility(View.GONE);
        }

        return builder.create();
    }

    /**
     * リスナーを追加する
     *
     * @param listener
     */
    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

    /**
     * リスナーを削除する
     */
    public void removeDialogListener() {
        this.listener = null;
    }

    public interface DialogListener extends EventListener {

        /**
         * okボタンが押されたイベントを通知する
         */
        public void onPositiveClick();

        /**
         * cancelボタンが押されたイベントを通知する
         */
        public void onNegativeClick();
    }
}
