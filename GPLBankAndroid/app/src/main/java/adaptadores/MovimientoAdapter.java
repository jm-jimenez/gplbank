package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chema.gplbankandroid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import clasescomunes.modelo.productos.CuentaAhorro;
import clasescomunes.modelo.productos.Movimiento;

/**
 * Adaptador de un ArryList de movimientos para poblar un listView
 * Created by JoséMaría on 21/05/2016.
 */
public class MovimientoAdapter extends ArrayAdapter{

    private Context context;
    private int resource;
    private ArrayList<Movimiento> movimientos;
    private SimpleDateFormat sdf;

    public MovimientoAdapter(Context context, int resource, ArrayList<Movimiento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        movimientos = objects;
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View item = convertView;
        ViewHolder holder;
        if (item == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(resource, null);
            holder = new ViewHolder();
            holder.dia = (TextView) item.findViewById(R.id.tv_dia);
            holder.concepto = (TextView) item.findViewById(R.id.tv_concepto);
            holder.importe = (TextView) item.findViewById(R.id.tv_importe);
            item.setTag(holder);
        }
        else{
            holder = (ViewHolder)item.getTag();
        }

        holder.dia.setText(sdf.format(movimientos.get(position).getDia()));
        holder.concepto.setText(movimientos.get(position).getConcepto());
        holder.importe.setText(String.valueOf(movimientos.get(position).getImporte()));

        return item;
    }



    static class ViewHolder {
        TextView dia;
        TextView concepto;
        TextView importe;
    }
}
