package incalpaca.pedidosincalpaca;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevoFragment extends Fragment {


    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private String rfoto="";

    public NuevoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NuevoFragment newInstance(String param1, String param2) {
        NuevoFragment fragment = new NuevoFragment();
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

    public void scanFromFragment() {
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ScrollView a = (ScrollView) inflater.inflate(R.layout.fragment_nuevo, container, false);

        Button qr = (Button) a.findViewById(R.id.qr);
        qr.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                scanFromFragment();
            }
        });

        arrayList = new ArrayList<String>();

        final Button ver = (Button) a.findViewById(R.id.VerItems);
        ver.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.poplist, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                list = (ListView) popupView.findViewById(R.id.ListaQR);
                adapter = new ArrayAdapter<String>(getContext(), R.layout.listqr, arrayList);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(ver, 50, -30);
            }
        });

        Button nuev = (Button) a.findViewById(R.id.in_nuevo);
        nuev.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText ed1 = (EditText) a.findViewById(R.id.editText);
                EditText ed2 = (EditText) a.findViewById(R.id.editText2);
                EditText ed3 = (EditText) a.findViewById(R.id.editText3);
                EditText ed4 = (EditText) a.findViewById(R.id.editText4);
                EditText ed5 = (EditText) a.findViewById(R.id.editText5);
                EditText ed6 = (EditText) a.findViewById(R.id.editText6);
                EditText ed7 = (EditText) a.findViewById(R.id.editText7);

                ed1.setText("");
                ed2.setText("");
                ed3.setText("");
                ed4.setText("");
                ed5.setText("");
                ed6.setText("");
                ed7.setText("");
                arrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        Button foto = (Button) a.findViewById(R.id.button);
        foto.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                final String ruta_fotos = "/mnt/sdcard/PedidosIncalpaca/fotos/";
                File file = new File(ruta_fotos);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String file2 = ruta_fotos + getCode() + ".jpg";
                rfoto = file2;
                //Toast.makeText(getContext(),file2,Toast.LENGTH_LONG).show();
                File mi_foto = new File(file2);
                try {
                    mi_foto.createNewFile();
                } catch (Exception ex) {

                }
                Uri uri = Uri.fromFile( mi_foto );
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(cameraIntent, 0);
            }
        });

        Button guardar = (Button) a.findViewById(R.id.button2);
        guardar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                DBHandler db = DBHandler.getInstance(getActivity());
                EditText n = (EditText) a.findViewById(R.id.editText2);
                String nombre = n.getText().toString();

                EditText ep = (EditText) a.findViewById(R.id.editText3);
                String empresa = ep.getText().toString();

                EditText d = (EditText) a.findViewById(R.id.editText4);
                String direccion = d.getText().toString();

                EditText p = (EditText) a.findViewById(R.id.editText5);
                String pais = p.getText().toString();

                EditText em = (EditText) a.findViewById(R.id.editText6);
                String email = em.getText().toString();

                EditText o = (EditText) a.findViewById(R.id.editText7);
                String observaciones = o.getText().toString();

                Calendar c = Calendar.getInstance();
                String dt = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
                db.addPedido(new Pedido(arrayList.toString().replace("[", "").replace("]", "").replace(" ", ""), nombre, empresa, direccion, pais, email, observaciones, rfoto, dt));
            }
        });

        return a;
    }

    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = "pic_" + date;
        return photoCode;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String txt="";
        if(result != null) {
            if(result.getContents() != null) {
                txt = result.getContents();
            }
        }

        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
        arrayList.add(txt);
    }
}

