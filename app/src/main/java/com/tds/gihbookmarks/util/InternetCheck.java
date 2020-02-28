package com.tds.gihbookmarks.util;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.tds.gihbookmarks.R;

public class InternetCheck {

    private Context context;

    public InternetCheck() {
    }

    public InternetCheck(Context context) {
        this.context = context;
    }

    public boolean getInternetStatus() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (!(activeNetwork != null && activeNetwork.isConnectedOrConnecting())) {
            final Dialog dialog1 = new Dialog(context, R.style.df_dialog);
            dialog1.setContentView(R.layout.dialog_no_internet);
            dialog1.setCancelable(true);
            dialog1.setCanceledOnTouchOutside(true);
            dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog1.dismiss();
                }
            });
            dialog1.show();
        }
        return true;
    }
}
