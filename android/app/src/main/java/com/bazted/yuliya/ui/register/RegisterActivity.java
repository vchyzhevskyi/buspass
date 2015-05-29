/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.ui.register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bazted.yuliya.BuildConfig;
import com.bazted.yuliya.R;
import com.bazted.yuliya.app.BaseActivity;
import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.ui.MainActivity_;
import com.bazted.yuliya.ui.login.LoginActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by T.Bazyshyn on 29/05/15.
 *
 * @author T.Bazyshyn
 * @since 29/05/15
 */
@EActivity(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @App
    YApp app;

    @ViewById(R.id.pb)
    ProgressBar pb;
    @ViewById(R.id.email)
    EditText emailEt;
    @ViewById(R.id.password)
    EditText passwordEt;
    @ViewById(R.id.confirm_password)
    EditText confirmPasswordEt;
    @ViewById(R.id.register_btn)
    Button registerBtn;
    @ViewById(R.id.register_form)
    ScrollView registerForm;

    @Extra
    String emailFromLogin;

    @AfterViews
    protected void start() {
        if (!TextUtils.isEmpty(emailFromLogin)) {
            emailEt.append(emailFromLogin);
        }
        if (BuildConfig.DEBUG) {
            passwordEt.setText(LoginActivity.PASS);
            confirmPasswordEt.setText(LoginActivity.PASS);
        }
        confirmPasswordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }

    @Click(R.id.register_btn)
    void registerClicked() {
        attemptLogin();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        emailEt.setError(null);
        passwordEt.setError(null);
        confirmPasswordEt.setError(null);

        // Store values at the time of the login attempt.
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();
        String confirmPassEt = confirmPasswordEt.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordEt.setError(getString(R.string.error_invalid_password));
            focusView = passwordEt;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordEt.setError(getString(R.string.error_invalid_password));
            focusView = passwordEt;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailEt.setError(getString(R.string.error_field_required));
            focusView = emailEt;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailEt.setError(getString(R.string.error_invalid_email));
            focusView = emailEt;
            cancel = true;
        }

        if (!password.equals(confirmPassEt)) {
            confirmPasswordEt.setError(getString(R.string.error_invalid_confirm_pass));
            focusView = confirmPasswordEt;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            startRegistrationRequest(email, password);
        }
    }

    @Background(delay = 3500)
    void startRegistrationRequest(String login, String pass) {
        //TODO make request
        if (login.equals(LoginActivity.LOGIN) && pass.equals(LoginActivity.PASS)) {
            startMainActivity();
            app.auth().login(login, login + pass);
        } else {
            showProgress(false);
        }

    }

    @UiThread
    void startMainActivity() {
        MainActivity_.intent(this)
                .start();
        finish();
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @UiThread
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerForm.setVisibility(show ? View.GONE : View.VISIBLE);
            registerForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            pb.setVisibility(show ? View.VISIBLE : View.GONE);
            pb.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pb.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pb.setVisibility(show ? View.VISIBLE : View.GONE);
            registerForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
