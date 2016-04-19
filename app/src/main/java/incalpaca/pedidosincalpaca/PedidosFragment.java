package incalpaca.pedidosincalpaca;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidosFragment extends Fragment{

    TextView dt;
    List<Pedido> pedidosList;

    public PedidosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PedidosFragment newInstance(String param1, String param2) {
        PedidosFragment fragment = new PedidosFragment();
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
        final LinearLayout a = (LinearLayout) inflater.inflate(R.layout.fragment_pedidos, container, false);

        ImageButton im = (ImageButton) a.findViewById(R.id.imageButton);
        dt = (TextView) a.findViewById(R.id.editText8);
        final DBHandler db = DBHandler.getInstance(getActivity());

        im.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int anio = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dt.setText(String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth));
                            }

                        }, anio, mes, dia);

                dpd.show();
            }
        });

        Button ft = (Button) a.findViewById(R.id.button3);
        ft.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                RecyclerView rv = (RecyclerView) a.findViewById(R.id.rv);
                pedidosList = new ArrayList<>();

                if(dt.getText().toString().compareTo("0000/00/00") == 0) {
                    Toast.makeText(getContext(), "Seleccionar Fecha primero", Toast.LENGTH_LONG).show();
                }else {
                    pedidosList = db.getPedidosFecha(dt.getText().toString());
                    PedidosRecy mAdapter = new PedidosRecy(pedidosList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    rv.setLayoutManager(mLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        Button ex = (Button) a.findViewById(R.id.Exportar);
        ex.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                String dt = String.valueOf(c.get(Calendar.YEAR))+String.valueOf((c.get(Calendar.MONTH)+1))+String.valueOf(c.get(Calendar.DAY_OF_MONTH))
                        +String.valueOf(c.get(Calendar.HOUR))+String.valueOf(c.get(Calendar.MINUTE))+String.valueOf(c.get(Calendar.SECOND));

                final String ruta_files = "/mnt/sdcard/PedidosIncalpaca/files/";
                File file = new File(ruta_files);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String ruta = "/mnt/sdcard/PedidosIncalpaca/files/"+dt+".csv";

                File archivo = new File(ruta);

                BufferedWriter bw;
                List<Emails> emm = db.getAllEmails();
                String[] to = { emm.get(0).getEmail1(), emm.get(0).getEmail2(), emm.get(0).getEmail3() };
                String Asunto = "PedidosIncalpaca";
                //String[] cc = { "@" };
                try {
                    bw = new BufferedWriter(new FileWriter(archivo));

                    bw.write("Nombre,Empresa,Direccion,Pais,Email,Observaciones,Foto,Items\n");
                    for (Pedido ped : pedidosList) {
                        bw.write(ped.getNombre() + "," + ped.getEmpresa() + "," + ped.getDireccion() + "," + ped.getPais() + "," + ped.getEmail() + ","
                                + ped.getObservaciones() + ",,\"" + ped.getItems() + "\"\n");
                    }

                    bw.close();
                }catch (Exception e){}
                enviar(to, Asunto,"Email Enviado Autmaticamente",archivo);
            }
        });

        return a;
    }
    private void enviar(String[] to, String asunto, String mensaje, File adjunto) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        //String[] to = direccionesEmail;
        //String[] cc = copias;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        //emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(adjunto));
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }
}
