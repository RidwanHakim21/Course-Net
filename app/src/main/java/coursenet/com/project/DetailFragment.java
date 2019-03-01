package coursenet.com.project;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_detail, container, false);

        final EditText edt1 = (EditText) x.findViewById(R.id.edt1);
        final EditText edt2 = (EditText) x.findViewById(R.id.edt2);
        Button b1 = (Button) x.findViewById(R.id.b1);
        Button b2 = (Button) x.findViewById(R.id.b2);

        //ambil idnya
        final String id = getArguments().getString("id");

        //1.postman
        OkHttpClient postman = new OkHttpClient();

        //2.request (set alamat dan methodnya GET)
        Request request = new Request.Builder()
                .get()
                .url(Setting.IP_SERVER + "get_register.php?id=" + id)
                .build();

        //3.progress dialog
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Processing..");
        pd.setMessage("Wait..");
        pd.setCancelable(true);
        pd.show();

        //4.send
        postman.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "please check your connection " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String hasil = response.body().string();
                try {
                    final JSONObject j = new JSONObject(hasil);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            try {

                                edt1.setText(j.getString("nama"));
                                edt2.setText(j.getString("email"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    Log.e("ERORNYA", Log.getStackTraceString(e));
                    e.printStackTrace();
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkHttpClient postman = new OkHttpClient();

                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("a", id)
                        .build();

                Request request = new Request.Builder()
                        .post(body)
                        .url(Setting.IP_SERVER + "proses_delete.php")
                        .build();

                final ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setTitle("Processing..");
                pd.setMessage("Wait..");
                pd.setCancelable(true);
                pd.show();

                postman.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                pd.dismiss();
                                Toast.makeText(getActivity(), "please check your connection " + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                pd.dismiss();
                                Toast.makeText(getActivity(), "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().popBackStackImmediate();


                            }
                        });

                    }
                });
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = edt1.getText().toString();
                String email = edt2.getText().toString();

                OkHttpClient postman = new OkHttpClient();

                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("a", email)
                        .addFormDataPart("b", nama)
                        .addFormDataPart("id", id)
                        .build();

                Request request = new Request.Builder()
                        .post(body)
                        .url(Setting.IP_SERVER + "proses_edit.php")
                        .build();

                final ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setTitle("Processing..");
                pd.setMessage("Wait..");
                pd.setCancelable(true);
                pd.show();

                postman.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                pd.dismiss();
                                Toast.makeText(getActivity(), "please check your connection " + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                pd.dismiss();
                                Toast.makeText(getActivity(), "Data Berhasil Diubah", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().popBackStackImmediate();


                            }
                        });

                    }
                });

            }
        });

        return x;
    }

}
