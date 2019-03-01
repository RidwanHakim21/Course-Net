package coursenet.com.project;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordFragment extends Fragment {


    public PasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_password, container, false);

        Button b1 = (Button) x.findViewById(R.id.b1);
        final EditText t1 = (EditText) x.findViewById(R.id.t1);
        final EditText t2 = (EditText) x.findViewById(R.id.t2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password_baru = t1.getText().toString();
                String konfirmasi_password_baru = t2.getText().toString();

                if (password_baru.length() == 0) {
                    Toast.makeText(getActivity(), "password dibuthkan", Toast.LENGTH_SHORT).show();
                }

                if (konfirmasi_password_baru.length() == 0) {

                    Toast.makeText(getActivity(), "konfirmasi password", Toast.LENGTH_LONG).show();
                }

                if (password_baru.equals(konfirmasi_password_baru) == false) {

                    Toast.makeText(getActivity(), "Password Berbeda", Toast.LENGTH_LONG).show();
                }

                if (password_baru.equals(konfirmasi_password_baru)== true){

                    Toast.makeText(getActivity(),"Perubahan Password Behasil", Toast.LENGTH_LONG).show();
                }
            }
        });

        return x;
    }

}
