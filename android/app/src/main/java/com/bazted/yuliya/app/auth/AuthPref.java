/*
 * Copyright (c) 2015. V. Chyzhevskyi & T.Bazyshyn
 */

package com.bazted.yuliya.app.auth;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by T.Bazyshyn on 29/05/15.
 *
 * @author T.Bazyshyn
 * @since 29/05/15
 */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface AuthPref {

    String token();

    String email();

}
