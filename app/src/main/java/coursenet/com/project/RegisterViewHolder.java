package coursenet.com.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

//java untuk card register
public class RegisterViewHolder extends RecyclerView.ViewHolder{

    CircleImageView img1;
    TextView t1;
    TextView t2;

    public RegisterViewHolder(@NonNull final View itemView) {
        super(itemView);
        img1 = (CircleImageView) itemView.findViewById(R.id.img1);
        t1 = (TextView) itemView.findViewById(R.id.t1);
        t2 = (TextView)itemView.findViewById(R.id.t2);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) itemView
                            .getContext();

                String idnya = t2.getText().toString();

                DetailFragment df = new DetailFragment();

                Bundle b = new Bundle();
                b.putString("id", idnya);
                df.setArguments(b);

                ma.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, df).addToBackStack(null)
                        .commit();
            }
        });
    }
}
