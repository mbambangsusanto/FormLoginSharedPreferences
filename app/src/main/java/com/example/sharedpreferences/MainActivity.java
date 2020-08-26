package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    private EditText edtTextEmail;
    private EditText edtTextPw;

    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxRememberMe =(CheckBox) findViewById(R.id.checkbox_remember_me);

        edtTextEmail = (EditText) findViewById(R.id.email);
        edtTextPw = (EditText) findViewById(R.id.password);

        edtTextPw.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                if (i == R.id.password || i == EditorInfo.IME_NULL)
                {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });


        if(!new PrefManager(this).isUserLogOut())
        {
            startHomeActivity();
        }
    }

    private void attemptLogin()
    {

        edtTextEmail.setError(null);
        edtTextEmail.setError(null);

        String email = edtTextEmail.getText().toString();
        String password = edtTextPw.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password))
        {
            edtTextPw.setError(getString(R.string.invalid_password));
            focusView = edtTextPw;
            cancel = true;
        }

        if (TextUtils.isEmpty(email))
        {
            edtTextEmail.setError(getString(R.string.field_required));
            focusView = edtTextEmail;
            cancel = true;
        } else if (!isEmailValid(email))
        {
            edtTextEmail.setError(getString(R.string.invalid_email));
            focusView = edtTextEmail;
            cancel = true;
        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else
            {
            // save data in local shared preferences
            if (checkBoxRememberMe.isChecked())
                saveLoginDetails(email, password);
            startHomeActivity();
            }
    }

    private void startHomeActivity()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void saveLoginDetails(String email, String password)
    {
        new PrefManager(this).saveLoginDetails(email, password);
    }

    private boolean isEmailValid(String email)
    {
        //TODO: Replace this with your own logic
        return email.contains("mobilecomputing@gmail.com");
    }
    private boolean isPasswordValid(String password)
    {
        //TODO: Replace this with your own logic
        return password.length() > 4 && password.equals("1234567");
    }
}