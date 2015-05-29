/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.ui;

import android.widget.TextView;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.hello_tv)
    TextView textView;

    @AfterViews
    void start() {
        textView.setText(R.string.app_name);
    }
}
