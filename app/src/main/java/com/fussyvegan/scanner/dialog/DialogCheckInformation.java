package com.fussyvegan.scanner.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.fussyvegan.scanner.activity.LoginActivity;

public class DialogCheckInformation extends AlertDialog.Builder {
    public DialogCheckInformation(@NonNull final Context context) {
        super(context);
        setIcon(android.R.drawable.ic_dialog_alert);
        setTitle("Notification");
        setMessage("You have to login to use this feature");
        setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        });
        setNegativeButton("Cancel", null);
    }
}
