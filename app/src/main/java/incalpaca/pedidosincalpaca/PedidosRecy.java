package incalpaca.pedidosincalpaca;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by josimar on 18/04/16.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class PedidosRecy extends RecyclerView.Adapter<PedidosRecy.MyViewHolder> {

    private List<Pedido> pedidos;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, empresa, fecha, items;

        public MyViewHolder(View view) {
            super(view);
            nombre = (TextView) view.findViewById(R.id.ad_nombre);
            empresa = (TextView) view.findViewById(R.id.ad_empresa);
            fecha = (TextView) view.findViewById(R.id.ad_fecha);
            items = (TextView) view.findViewById(R.id.ad_items);
        }
    }


    public PedidosRecy(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterpedido, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.nombre.setText(pedido.getNombre());
        holder.empresa.setText(pedido.getEmpresa());
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        holder.fecha.setText(pedido.getFecha());
        String it = pedido.getItems();
        holder.items.setText(String.valueOf(it.split(",").length));
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }
}