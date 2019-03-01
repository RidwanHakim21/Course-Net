package coursenet.com.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void showMessage(View view) {
        //INI DIJALANKAN PAS DIKLIK
        /* BEDANYA..
        INI BISA DI ENTER PESANNYA */

        //1. ambil edittext dari xml nya
        EditText t1 = (EditText) findViewById(R.id.t1);
        EditText t2 = (EditText) findViewById(R.id.t2);

        //2.ambil isi dari t1 dan t2
        final String email = t1.getText().toString();
        final String password = t2.getText().toString();

        //3.cek emailnya kosong atau tidak
        if (email.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.Email_is_required, Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() == 0) {
            t2.setError(getString(R.string.password_is_required));
            return;
        }


        OkHttpClient postman = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("a", email)
                .addFormDataPart("b", password).build();

        Request request = new Request.Builder()
                .post(body)
                .url(Setting.IP_SERVER + "proses_login.php")
                .build();

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("processing..");
        pd.setMessage("wait..");
        pd.setCancelable(false);
        pd.show();

        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "please check your connection " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            String hasil = response.body().string();
                try {
                    JSONObject j = new JSONObject(hasil);
                    boolean r = j.getBoolean("result");
                    if (r == false)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();

                                    SharedPreferences.Editor sp = getSharedPreferences("DATA_LOGIN",0).edit();

                                    sp.putString("dataemail", email);
                                    sp.putString("datapassword", password);
                                    sp.commit();


                                    Intent cs = new Intent(getApplicationContext(), MainActivity.class);
                                    cs.putExtra("data1", email);
                                    cs.putExtra("data2", password);
                                    startActivity(cs);
                                    Toast.makeText(getApplicationContext(),
                                            getSharedPreferences("DATA_LOGIN", 0).getString("dataemail", "---"),
                                            Toast.LENGTH_LONG).show();
                                    finish();

                                }
                            });
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void Message(View view) {
        Intent cs = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(cs);
    }

    public void coba(View view) {
        Intent cb = new Intent(getApplicationContext(), ForgotActivity.class);
        startActivity(cb);
    }
}
