package com.bazted.yuliya.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.bazted.yuliya.R;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
@EViewGroup(R.layout.view_nfc_header)
public class NfcHeader extends CardView {
    public NfcHeader(Context context) {
        super(context);
    }

    public NfcHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NfcHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
