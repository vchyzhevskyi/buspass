/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.ui.pin;

import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bazted.yuliya.R;
import com.bazted.yuliya.app.BaseActivity;
import com.bazted.yuliya.app.YApp;
import com.bazted.yuliya.ui.MainActivity_;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by T.Bazyshyn on 30/05/15.
 *
 * @author T.Bazyshyn
 * @since 30/05/15
 */
@EActivity(R.layout.activity_pin)
@OptionsMenu({R.menu.menu_done})
public class PinActivity extends BaseActivity {
    @App
    YApp app;

    @ViewById(R.id.pin_et)
    EditText editText;

    @ViewById(R.id.pin_instruction_tv)
    TextView instructionTv;

    private String previousText;

    @EditorAction(R.id.pin_et)
    boolean pinEtActionReceived(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            checkOrSavePin();
            return true;
        }
        return false;
    }

    @Background
    void checkOrSavePin() {
        String textFromInput = editText.getText().toString();
        if (TextUtils.isEmpty(textFromInput) || textFromInput.length() < 4) {
            showError(getString(R.string.error_invalid_pin));
            return;
        }
        if (TextUtils.isEmpty(previousText)) {
            if (app.auth().isLogged()) {
                if (app.auth().matchPin(textFromInput)) {
                    finishActivityWithResult(RESULT_OK);
                } else {
                    showToast(getString(R.string.error_invalid_pin));
                }
            } else {
                this.previousText = textFromInput;
                askForConfirmation();
            }
        } else {
            if (textFromInput.equals(previousText)) {
                app.auth().setPin(textFromInput);
                startMainActivity();
            } else {
                showToast(getString(R.string.error_invalid_confirm_pin));
                previousText = null;
                askForPin();
            }
        }

    }

    @UiThread
    void askForPin() {
        instructionTv.setText(R.string.prompt_pin);
        editText.setText(null);
        editText.setError(null);
    }

    @UiThread
    void askForConfirmation() {
        instructionTv.setText(R.string.prompt_repeat_pin);
        editText.setText(null);
        editText.setError(null);
    }


    @OptionsItem(R.id.action_done)
    void doneOptionClicked() {
        checkOrSavePin();
    }

    @UiThread
    void finishActivityWithResult(int result) {
        setResult(result);
        finish();
    }

    @UiThread
    void startMainActivity() {
        MainActivity_.intent(this)
                .start();
        finish();
    }

    @UiThread
    void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @UiThread
    void showError(String text) {
        editText.setError(text);
    }

}
