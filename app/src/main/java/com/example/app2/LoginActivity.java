package com.example.app2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //定义
        SharedPreferences sp= null;
        EditText user =findViewById(R.id.user);
        EditText pwd = findViewById(R.id.pwd);
        Button login = findViewById(R.id.Login);
        Button re = findViewById(R.id.re);
        CheckBox remember = findViewById(R.id.remember);
        sp =this.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        if (sp.getBoolean("checkboxBoolean",false)){
            user.setText(sp.getString("user",null));
            pwd.setText(sp.getString("pwd",null));
            remember.setChecked(true);
        }
        SharedPreferences finalSp =sp;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getText().toString().trim().equals("")){
                    Toast.makeText(LoginActivity.this,"请您输入用户名！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.getText().toString().trim().equals("")){
                    Toast.makeText(LoginActivity.this,"请您输入密码！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //SharedPreferences记住数据
                SharedPreferences.Editor editor= finalSp.edit();
                if(remember.isChecked()){
                    editor.putString("user",user.getText().toString());
                    editor.putString("pwd",pwd.getText().toString());
                    editor.putBoolean("checkboxBoolean",true);
                }
                else{
                    editor.putString("user",null);
                    editor.putString("pwd",null);
                    editor.putBoolean("checkboxBoolean",false);
                }
                editor.commit();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("name",user.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}
