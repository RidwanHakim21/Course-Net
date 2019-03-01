package coursenet.com.project;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        //Toast.makeText(getApplicationContext(), "login sukses", Toast.LENGTH_LONG).show();
    }

    public void forgot(View view) {
        EditText ema = (EditText) findViewById(R.id.ema);
        EditText pass = (EditText) findViewById(R.id.pass);

        String email = ema.getText().toString();
        String password = pass.getText().toString();

        if (email.length() == 0) {
            ema.setError("enter your email");
            return;
        }

        if (password.length() == 0) {
            pass.setError(getString(R.string.password_is_required));
            return;
        }

        Snackbar.make(view, "succses", Snackbar.LENGTH_SHORT).show();

        Intent bg = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(bg);
    }
}
