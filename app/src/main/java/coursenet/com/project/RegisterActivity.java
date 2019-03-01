package coursenet.com.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //INI DIJALANKAN PAS DIKLIK
        /* BEDANYA..
        INI BISA DI ENTER PESANNYA */

    public void woh(View view) {
        //1. ambil edittext dari xml nya
        EditText t1 = (EditText) findViewById(R.id.t1);
        EditText t2 = (EditText) findViewById(R.id.t2);
        EditText t3 = (EditText) findViewById(R.id.t3);
        ImageView gambar = (ImageView) findViewById(R.id.gambar);

        //2.ambil isi dari t1 dan t2
        String email = t1.getText().toString();
        String password = t2.getText().toString();
        String nama = t3.getText().toString();
        Bitmap gg = ((BitmapDrawable) gambar.getDrawable()).getBitmap();

        //convert gambar ke base64

        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        gg.compress(Bitmap.CompressFormat.JPEG,100, baos);

        byte[] hasilconvert = baos.toByteArray();
        String base64 = Base64.encodeToString(hasilconvert, Base64.NO_WRAP);

        //3.cek emailnya kosong atau tidak
        if (email.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.Email_is_required, Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() == 0) {
            t2.setError(getString(R.string.password_is_required));
            return;
        }

        if (nama.length() == 0) {
            t3.setError(getString(R.string.enter_your_name));
            return;
        }

        //Proses Kirim Ke PHP :

        //1.buka postman
        OkHttpClient postman = new OkHttpClient();

        //2.buat request body dan data yang mau dikirim
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("a", email)
                .addFormDataPart("b", password)
                .addFormDataPart("c", nama)
                .addFormDataPart("d", base64)
                .build();

        //3.set alamat tujuan dan method kirim : POST
        Request request = new Request.Builder()
                .post(body)
                .url(Setting.IP_SERVER + "proses_register.php")
                .build();

        //4.progress dialog (ngga wajib)
        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setTitle("Processing..");
        pd.setMessage("please wait..");
        pd.setCancelable(false);
        pd.show();

        //5.send
        //NOTE : postman dapaet dari variabel step 1, request dari variabel step 3
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
                    if (r== true){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Intent b = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(b);
                            }
                        });
                    }

                    else {
                        String m = j.getString("message");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Registrasi Gagal", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                } catch (JSONException e)

                { e.printStackTrace();

                }
            }
        });

    }


    public void openCamera(View view) {
        Intent k = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(k, 8888);
    }

    public void openVideo(View view) {

        Intent k = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(k, 9999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8888) {
            if (resultCode == RESULT_OK) {
                ImageView gambar = (ImageView) findViewById(R.id.gambar);
                Bitmap img = (Bitmap) data.getExtras().get("data");

                gambar.setImageBitmap(img);
            }

        } else if (requestCode == 9999) {

        }
    }
}

