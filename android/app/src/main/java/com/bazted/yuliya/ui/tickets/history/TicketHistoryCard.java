package com.bazted.yuliya.ui.tickets.history;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.utils.AttributeUtils;
import com.bazted.yuliya.rest.response.TicketRes;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15ū
 */
@EViewGroup(R.layout.view_history_ticket)
public class TicketHistoryCard extends CardView {

    @ViewById(R.id.ticket_name_tv)
    TextView ticketNameTv;

    @ViewById(R.id.ticket_price_tv)
    TextView ticketPriceTv;

    @ViewById(R.id.ticket_date_tv)
    TextView ticketDateTv;

    public TicketHistoryCard(Context context) {
        super(context);
        init(context);
    }

    public TicketHistoryCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TicketHistoryCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setForeground(AttributeUtils.getDrawable(context, R.attr.selectableItemBackground));
    }

    public void bind(TicketRes ticketTypeRes) {
        ticketNameTv.setText(ticketTypeRes.getType().getName());
        ticketPriceTv.setText(Double.toString(ticketTypeRes.getType().getCost()));
        ticketDateTv.setText(ticketTypeRes.getBought());
    }
}
