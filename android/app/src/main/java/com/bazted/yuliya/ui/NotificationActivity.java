package com.bazted.yuliya.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.BaseActivity;
import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.rest.request.BuyTicketReq;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;

import retrofit.RetrofitError;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
@EActivity(R.layout.activity_notifications)
public class NotificationActivity extends BaseActivity {

    public static final int PIN_REQUEST = 1235;

    @App
    YApp app;

    @Extra
    int notificationId = -1;

    @Extra
    int busId = -1;

    @Extra
    Action action;

    enum Action {
        DISMISS,
        BUY_TICKET,
        CHANGE_TICKET_TYPE,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (notificationId > -1) {
            if (action != null) {
                NotificationManagerCompat from = NotificationManagerCompat.from(this);
                switch (action) {
                    case DISMISS:
                        from.cancel(notificationId);
                        finish();
                        break;
                    case BUY_TICKET:
                        buyTicket(busId, app.auth().favTicketId());
                        from.cancel(notificationId);
                        break;
                    case CHANGE_TICKET_TYPE:
                        from.cancel(notificationId);
                        MainActivity_.intent(this).start();
                        finish();
                        break;
                }
            }
        }
    }

    public static PendingIntent getDismissIntent(int notificationId, Context context) {
        Intent intent = NotificationActivity_.intent(context)
                .action(Action.DISMISS)
                .notificationId(notificationId)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .get();
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public static PendingIntent getBuyIntent(int notificationId, int busId, Context context) {
        Intent intent = NotificationActivity_.intent(context)
                .action(Action.BUY_TICKET)
                .busId(busId)
                .notificationId(notificationId)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .get();
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public static PendingIntent getChangeTicketIntent(int notificationId, Context context) {
        Intent intent = NotificationActivity_.intent(context)
                .action(Action.CHANGE_TICKET_TYPE)
                .notificationId(notificationId)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .get();
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @OnActivityResult(PIN_REQUEST)
    void pinResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            buyTicket(busId, app.auth().favTicketId());
        } else {
            showToast("Wrong PIN");
        }
        finish();
    }

    @Background
    void buyTicket(int busId, int typeId) {
        try {
            app.api().buyTicket(new BuyTicketReq(busId, typeId));
            showToastAndClose("Ticket bought");
        } catch (RetrofitError e) {
            Log.e("buy ticket", e.toString());
            showToastAndClose("ERROR! Ticket was not bought.");
        }
    }

    @UiThread
    void showToastAndClose(String message) {
        showToast(message);
        finish();
    }


}
