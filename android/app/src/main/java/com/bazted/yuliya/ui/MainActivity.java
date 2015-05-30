/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.ui;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.BaseActivity;
import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.app.nfc.BusNfc;
import com.bazted.yuliya.ui.login.LoginActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@OptionsMenu({R.menu.menu_main})
public class MainActivity extends BaseActivity {

    public static final String LOG = "MainActivity";

    @App
    YApp app;

    @ViewById(R.id.hello_tv)
    TextView textView;

    @AfterViews
    void start() {
        textView.setText(R.string.app_name);
    }

    @OptionsItem(R.id.action_logout)
    void logoutOptionSelected() {
        app.auth().logout();
        LoginActivity_.intent(this).start();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(LOG, "new Intent" + intent.toString());
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        if (intent == null) {
            Log.e(LOG, "intent is null");
            return;
        }
        if (intent.getAction() != null) {
            Log.e("LOG", intent.getAction());
        }
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMsgs != null) {
            Parcelable rawMsg;
            NdefMessage msg;
            String payload;
            String jsonBody = null;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < rawMsgs.length; i++) {
                rawMsg = rawMsgs[i];
                msg = (NdefMessage) rawMsg;
                //noinspection ForLoopReplaceableByForEach
                int length = msg.getRecords().length;
                for (int j = 0; j < length; j++) {
                    payload = new String(msg.getRecords()[j].getPayload());
                    switch (j) {
                        case 1:
                            jsonBody = payload;
                            break;
                    }
                }
            }
            if (jsonBody != null) {
                BusNfc busNfc = app.getRestBean().converter().fromJson(jsonBody, BusNfc.class);
                if (busNfc != null) {
                    Log.e(LOG, busNfc.toString());
                }
            }
        } else {
            showToast("Sorry TAG is not readable retry");
        }
    }
}