package coursenet.com.project;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_list, container, false);

        final RecyclerView rv1 = (RecyclerView) x.findViewById(R.id.rv1);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);

        GridLayoutManager gm = new GridLayoutManager(getActivity(), 2);

        rv1.setLayoutManager(lm);

        //GET TIDAK ADA BODY

        OkHttpClient postman = new OkHttpClient();

        Request request = new Request.Builder().get()
                .url(Setting.IP_SERVER + "data_register.php")
                .build();

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Processing..");
        pd.setMessage("wait..");
        pd.setCancelable(false);
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

                String hasil = response.body().string();
                try {
                    JSONArray j = new JSONArray(hasil);

                    final RegisterAdapter adapter = new RegisterAdapter();
                    adapter.data = new ArrayList<>();

                    for (int i = 0;i< j.length();i++)
                    {
                        Register r = new Register();
                        JSONObject jo = j.getJSONObject(i);

                        r.nama = jo.getString("nama");
                        r.foto = jo.getString("foto");
                        r.email = jo.getString("email");
                        r.id = jo.getInt("id");

                        adapter.data.add(r);

                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rv1.setAdapter(adapter);
                            pd.dismiss();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return x;
    }

}
