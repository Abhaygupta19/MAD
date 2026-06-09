package com.techiguru.fitmylife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText et_emailID, et_password;
    TextView tv_forgetPassword, tv_createAccount;
    Button bt_login;
    SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        boolean isLoggedIn = sp.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            Intent loggedIn = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(loggedIn);
            finish();
            return;
        }

        bt_login = findViewById(R.id.bt_login);
        et_emailID = findViewById(R.id.et_emailID);
        et_password = findViewById(R.id.et_password);
        tv_forgetPassword = findViewById(R.id.tv_forgetPassword);
        tv_createAccount = findViewById(R.id.tv_createAccount);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_emailID.getText().toString();
                String password = et_password.getText().toString();

                String savedEmail = sp.getString("emailID", null);
                String savedPassword = sp.getString("password", null);

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                } else if (email.equals(savedEmail) && password.equals(savedPassword)) {
                    // Save login state
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}