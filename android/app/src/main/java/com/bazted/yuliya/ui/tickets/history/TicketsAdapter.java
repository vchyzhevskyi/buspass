package com.bazted.yuliya.ui.tickets.history;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.rest.response.TicketRes;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
@EBean
public class TicketsAdapter extends BaseAdapter {

    @App
    YApp app;

    private final ArrayList<TicketRes> items = new ArrayList<>();
    private static final Object LOCK = new Object();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TicketRes getItem(int position) {
        return items.get(position);
    }

    public void add(Collection<TicketRes> items) {
        synchronized (LOCK) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void set(Collection<TicketRes> items) {
        synchronized (LOCK) {
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getType().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TicketHistoryCard card;
        if (convertView == null) {
            card = TicketHistoryCard_.build(parent.getContext());
        } else {
            card = (TicketHistoryCard) convertView;
        }
        card.bind(getItem(position));
        return card;
    }
}
