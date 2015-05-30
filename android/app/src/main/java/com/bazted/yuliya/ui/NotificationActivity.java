package com.bazted.yuliya.ui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
@EActivity
public class NotificationActivity extends Activity {

    @Extra
    int notificationId = -1;

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
                        break;
                    case BUY_TICKET:
                        from.cancel(notificationId);
                        break;
                    case CHANGE_TICKET_TYPE:
                        from.cancel(notificationId);
                        break;
                }
            }
        }
        finish();
    }

    public static PendingIntent getDismissIntent(int notificationId, Context context) {
        Intent intent = NotificationActivity_.intent(context)
                .action(Action.DISMISS)
                .notificationId(notificationId)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .get();
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public static PendingIntent getBuyIntent(int notificationId, Context context) {
        Intent intent = NotificationActivity_.intent(context)
                .action(Action.BUY_TICKET)
                .notificationId(notificationId)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .get();
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public static PendingIntent getChangeTicketIntent(int notificationId, Context context) {
        Intent intent = NotificationActivity_.intent(context)
                .action(Action.DISMISS)
                .notificationId(notificationId)
                .flags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .get();
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
