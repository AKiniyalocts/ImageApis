package com.example.anthony.wedpics.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.anthony.wedpics.R;
import com.example.anthony.wedpics.helpers.SharedPreferenceHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.login_button)
    Button loginButton;

    @InjectView(R.id.login_userid)
    EditText loginUserid;

    @InjectView(R.id.login_password)
    EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.login_button)
    public void onLoginClicked(){
        ArrayList<EditText> errors = new ArrayList<EditText>();

        if(loginUserid.getText() == null || loginUserid.getText().length() < 1){
            errors.add(loginUserid);
        }
        if(loginPassword.getText() == null || loginPassword.getText().length() < 1){
            errors.add(loginPassword);
        }

        /*
            Set errors if appicable
         */
        if(errors.size() > 0){
            for(EditText editText: errors)
                editText.setError(getString(R.string.error_not_empty));
        }

        /*
            No errors, lets authenticate
         */
        else{
            if(loginPassword.getText().toString().equals(getString(R.string.auth_pass))){
                SharedPreferenceHelper.writeGoodSession(true, this);

                Intent mainScreen = new Intent(this, MainActivity.class);
                mainScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(mainScreen);

                finish();
            }
            else{
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

                dialogBuilder
                        .setTitle(getString(R.string.auth_title))
                        .setMessage(getString(R.string.auth_fail))
                        .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                loginPassword.selectAll();
                            }
                        });
                dialogBuilder.show();

            }
        }
    }


}
