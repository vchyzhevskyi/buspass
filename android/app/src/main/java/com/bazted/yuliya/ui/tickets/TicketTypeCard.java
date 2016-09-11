package com.bazted.yuliya.ui.tickets;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.utils.AttributeUtils;
import com.bazted.yuliya.rest.response.TicketTypeRes;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15ū
 */
@EViewGroup(R.layout.view_ticket_type)
public class TicketTypeCard extends CardView {

    @ViewById(R.id.ticket_name_tv)
    TextView ticketNameTv;

    @ViewById(R.id.ticket_price_tv)
    TextView ticketPriceTv;

    @ViewById(R.id.ticket_fav_ic)
    View ticketFavIc;

    public TicketTypeCard(Context context) {
        super(context);
        init(context);
    }

    public TicketTypeCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TicketTypeCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setForeground(AttributeUtils.getDrawable(context, R.attr.selectableItemBackground));
    }

    public void bind(TicketTypeRes ticketTypeRes, boolean isFav) {
        ticketNameTv.setText(ticketTypeRes.getName());
        ticketPriceTv.setText(Double.toString(ticketTypeRes.getCost()));
        ticketFavIc.setVisibility(isFav ? VISIBLE : GONE);
    }
}
