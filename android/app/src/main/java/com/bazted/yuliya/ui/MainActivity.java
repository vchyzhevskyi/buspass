/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.BaseActivity;
import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.app.nfc.BusNfc;
import com.bazted.yuliya.rest.response.TicketTypeRes;
import com.bazted.yuliya.ui.login.LoginActivity_;
import com.bazted.yuliya.ui.tickets.TicketsTypeAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit.RetrofitError;

@EActivity(R.layout.activity_main)
@OptionsMenu({R.menu.menu_main})
public class MainActivity extends BaseActivity {

    public static final String LOG = "MainActivity";

    @App
    YApp app;

    @ViewById(R.id.swipe_refresh_ll)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.ticket_lv)
    ListView ticketList;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;

    @Bean
    TicketsTypeAdapter adapter;

    @AfterViews
    void start() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadTickets();
            }
        });
        ticketList.addHeaderView(NfcHeader_.build(this), null, false);
        ticketList.setAdapter(adapter);
        if (adapter.getCount() == 0) {
            downloadTickets();
        }
    }

    @Background
    void downloadTickets() {
        showRefreshing(true);
        try {
            List<TicketTypeRes> listOfTicketTypes = app.api().getListOfTicketTypes();
            addToAdapter(listOfTicketTypes);
        } catch (RetrofitError e) {
            Log.e(LOG, e.toString());
            showToast("Error Loading");
        }
    }

    @UiThread
    void addToAdapter(List<TicketTypeRes> listOfTicketTypes) {
        adapter.set(listOfTicketTypes);
        showRefreshing(false);
    }

    @UiThread
    void showRefreshing(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @OptionsItem(R.id.action_logout)
    void logoutOptionSelected() {
        app.auth().logout();
        LoginActivity_.intent(this).start();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Log.e(LOG, "no nfc");
            return;
        } else {
            Log.e(LOG, "we have nfc");
        }

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0);

        intentFilters = new IntentFilter[3];
        intentFilters[0] = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        intentFilters[1] = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFilters[2] = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(LOG, "onNewIntent|" + intent.toString());
        String action = intent.getAction();
        if (action != null) {
            processIntent(intent);
            Log.e(LOG, action);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        if (intent == null) {
            Log.e(LOG, "intent is null");
            return;
        }
        Log.e(LOG, intent.toString());
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMsgs != null) {
            Parcelable rawMsg;
            NdefMessage msg;
            NdefRecord ndefRecord;
            String payload;
            String jsonBody = null;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < rawMsgs.length; i++) {
                rawMsg = rawMsgs[i];
                msg = (NdefMessage) rawMsg;
                //noinspection ForLoopReplaceableByForEach
                int length = msg.getRecords().length;
                for (int j = 0; j < length; j++) {
                    ndefRecord = msg.getRecords()[j];
                    payload = new String(ndefRecord.getPayload());
                    Log.e(LOG, payload);
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
