package com.bazted.yuliya.ui.tickets;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.rest.response.TicketTypeRes;

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
public class TicketsTypeAdapter extends BaseAdapter {

    @App
    YApp app;

    private final ArrayList<TicketTypeRes> items = new ArrayList<>();
    private static final Object LOCK = new Object();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TicketTypeRes getItem(int position) {
        return items.get(position);
    }

    public void add(Collection<TicketTypeRes> items) {
        synchronized (LOCK) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void set(Collection<TicketTypeRes> items) {
        synchronized (LOCK) {
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TicketTypeCard card;
        if (convertView == null) {
            card = TicketTypeCard_.build(parent.getContext());
        } else {
            card = (TicketTypeCard) convertView;
        }
        TicketTypeRes item = getItem(position);
        card.bind(item, app.auth().favTicketId() == item.getId());
        return card;
    }
}
