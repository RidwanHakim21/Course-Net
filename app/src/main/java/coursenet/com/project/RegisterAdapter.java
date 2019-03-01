package coursenet.com.project;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterViewHolder> {

    //Arraylist dibuat untuk keranjang untuksimpan data register yang di dapat dari php
    ArrayList<Register> data;

    @NonNull
    @Override
    public RegisterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //card_register.xml dihubungkan dengan RegisterViewHolder
        View vw = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_register,viewGroup, false);

        RegisterViewHolder rv = new RegisterViewHolder(vw);
        return rv;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterViewHolder registerViewHolder, int i) {

        Register r = data.get(i);
        registerViewHolder.t1.setText(r.nama);
        registerViewHolder.t2.setText(r.id + "");

        Picasso.get().load(Setting.IP_SERVER + "images/" + r.foto).into(registerViewHolder.img1);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
