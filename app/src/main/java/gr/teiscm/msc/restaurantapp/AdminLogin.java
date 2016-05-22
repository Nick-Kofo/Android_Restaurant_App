package gr.teiscm.msc.restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Cooper on 19/4/2016.
 */
public class AdminLogin extends AppCompatActivity {
    Activity act;
    EditText AdminUserName, AdminPassword;
    FloatingActionButton CheckUserNamePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        act = AdminLogin.this;

        AdminUserName = (EditText) findViewById(R.id.etAdminUserName);
        AdminPassword = (EditText) findViewById(R.id.etAdminPassword);
        CheckUserNamePassword = (FloatingActionButton) findViewById(R.id.fabCheckUserNamePassword);

        CheckUserNamePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = AdminUserName.getText().toString();
                String password = AdminPassword.getText().toString();
                if (username.equals("cooper") && password.equals("NorthRemembers")) {
                    Intent i = new Intent(act, AddDish.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Snackbar.make(v, getString(R.string.WrongAdmin), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
