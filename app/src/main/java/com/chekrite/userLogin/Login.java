/*
 * Date: 2020.4.19
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite.userLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chekrite.R;
import com.chekrite.dashBoard.WelcomeSplash;

public class Login extends AppCompatActivity {
    private EditText EmployeeID;
    private EditText PassWord;
    private Button Enter;
    private TextView WrongMatching;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        EmployeeID = (EditText) findViewById(R.id.editText_employee_id);
        PassWord = (EditText) findViewById(R.id.editText_password);
        Enter = (Button) findViewById(R.id.enter);

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(EmployeeID.getText().toString(), PassWord.getText().toString());
            }
        });

    }
private void validation(String userEmloyeeID, String userPassWord){
        if((userEmloyeeID == "8554544") && (userPassWord =="1111")){
            Intent intent = new Intent(Login.this, WelcomeSplash.class);
            startActivity(intent);
        }else {
           // go to login fail page
        }
}

}
