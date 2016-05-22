package gr.teiscm.msc.restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {

    Activity act;
    Button bMenu, bWebsite, bContact, bAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = Main.this;

        fillViews();

        bMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(act, SearchDishes.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        bWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedToInternet()) {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://10.0.3.2:8000/"));
                    startActivity(i);
                } else {
                    Snackbar.make(v, getString(R.string.ServerError), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        bContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"nick_kofo@yahoo.gr"});
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject));
                i.putExtra(Intent.EXTRA_TEXT, getString(R.string.Message));
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Snackbar.make(v, getString(R.string.EmailError), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        bAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(act, About.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public void fillViews() {
        bMenu = (Button) findViewById(R.id.bMenu);
        bWebsite = (Button) findViewById(R.id.bWebsite);
        bContact = (Button) findViewById(R.id.bContact);
        bAbout = (Button) findViewById(R.id.bAbout);
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(act.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

}
