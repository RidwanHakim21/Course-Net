package coursenet.com.project;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler g = new Handler();
        g.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getSharedPreferences("DATA_LOGIN", 0).contains("dataemail")) {
                    Intent g = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(g);
                    finish();

                }

                else {
                    Intent g = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(g);
                    finish();
                }
            }
        }, 2000);
    }
}

