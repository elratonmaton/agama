package incalpaca.pedidosincalpaca;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfigFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigFragment extends Fragment {


    public ConfigFragment() {
        // Required empty public constructor
    }


    public static ConfigFragment newInstance(String param1, String param2) {
        ConfigFragment fragment = new ConfigFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout a = (RelativeLayout) inflater.inflate(R.layout.fragment_config, container, false);

        final DBHandler db = DBHandler.getInstance(getActivity());

        final List<Emails> emm = db.getAllEmails();

        final EditText em1 = (EditText) a.findViewById(R.id.editText9);
        final EditText em2 = (EditText) a.findViewById(R.id.editText10);
        final EditText em3 = (EditText) a.findViewById(R.id.email3);

        if(emm.size() > 0){
            em1.setText(emm.get(0).getEmail1());
            em2.setText(emm.get(0).getEmail2());
            em3.setText(emm.get(0).getEmail3());
        }

        Button bt = (Button) a.findViewById(R.id.button4);
        bt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (emm.size() == 0) {
                    Emails emmm = new Emails(1, em1.getText().toString(), em2.getText().toString(), em3.getText().toString());
                    db.addEmail(emmm);
                } else {
                    Emails emmm = new Emails(1, em1.getText().toString(), em2.getText().toString(), em3.getText().toString());
                    db.updateEmail(emmm);
                }
            }
        });

        return a;
    }


}
