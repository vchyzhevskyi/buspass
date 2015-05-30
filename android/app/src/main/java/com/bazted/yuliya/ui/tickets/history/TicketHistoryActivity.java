package com.bazted.yuliya.ui.tickets.history;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.BaseActivity;
import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.rest.response.TicketRes;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
@EActivity(R.layout.activity_main)
public class TicketHistoryActivity extends BaseActivity {


    public static final String LOG = "TicketHistoryActivity";

    @App
    YApp app;

    @ViewById(R.id.swipe_refresh_ll)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById(R.id.ticket_lv)
    ListView ticketList;

    @Bean
    TicketsAdapter adapter;

    @AfterViews
    void start() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadTickets();
            }
        });
        ticketList.setAdapter(adapter);
        if (adapter.getCount() == 0) {
            downloadTickets();
        }
    }

    @Background
    void downloadTickets() {
        showRefreshing(true);
        try {
            List<TicketRes> listOfTicketTypes = app.api().getTicketHistory();
            addToAdapter(listOfTicketTypes);
        } catch (RetrofitError e) {
            Log.e(LOG, e.toString());
            showToast("Error Loading");
        }
    }

    @UiThread
    void addToAdapter(List<TicketRes> listOfTicketTypes) {
        adapter.set(listOfTicketTypes);
        showRefreshing(false);
    }

    @UiThread
    void showRefreshing(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

}
