package com.group1.eda_397_group1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements AsyncResponse {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private DatabaseHandler databaseHandler = null;
    private JSONParser parser = new JSONParser();

    private EditText mEmailView = null;
    private EditText mUsernameView = null;
    private EditText mPassword1View = null;
    private EditText mPassword2View = null;
    private Button mRegisterView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (EditText) findViewById(R.id.txtEmail);
        mUsernameView = (EditText) findViewById(R.id.txtUsername);
        mPassword1View = (EditText) findViewById(R.id.txtPassword1);
        mPassword2View = (EditText) findViewById(R.id.txtPassword2);
        mRegisterView = (Button) findViewById(R.id.btnRegister);
        mRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        // Reset errors.
        mEmailView.setError(null);
        mUsernameView.setError(null);
        mPassword1View.setError(null);
        mPassword2View.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String username = mUsernameView.getText().toString();
        String password1 = mPassword1View.getText().toString();
        String password2 = mPassword2View.getText().toString();

        Log.d("TESTTAG", email + ", " + username + ", " + password1);
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password1) && !isPasswordValid(password1)) {
            mPassword1View.setError(getString(R.string.error_invalid_password));
            focusView = mPassword1View;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password2) && !isPasswordValid(password2)) {
            mPassword2View.setError(getString(R.string.error_invalid_password));
            focusView = mPassword2View;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password2) && TextUtils.equals(password1, password2)) {
            mPassword2View.setError(getString(R.string.error_passwords_not_match));
            focusView = mPassword2View;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            // perform the user registering attempt.
            databaseHandler = new DatabaseHandler(parser.getRegisterJSON(email, username, password1));
            databaseHandler.delegate = this;
            databaseHandler.execute();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    @Override
    public void processFinish(JSONObject json) throws JSONException {
        if (json.get("success").equals(1)) {
            Log.d("RegisterActivity", "registering success");

            // Go to another activity and store user
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Log.e("RegisterActivity", "registering error");
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Registering failed")
                    .setMessage(json.get("error_msg").toString())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing here
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
