/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.ui;

import android.widget.TextView;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.BaseActivity;
import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.ui.login.LoginActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@OptionsMenu({R.menu.menu_main})
public class MainActivity extends BaseActivity {

    @App
    YApp app;

    @ViewById(R.id.hello_tv)
    TextView textView;

    @AfterViews
    void start() {
        textView.setText(R.string.app_name);
    }

    @OptionsItem(R.id.action_logout)
    void logoutOptionSelected() {
        app.auth().logout();
        LoginActivity_.intent(this).start();
        finish();
    }
}
