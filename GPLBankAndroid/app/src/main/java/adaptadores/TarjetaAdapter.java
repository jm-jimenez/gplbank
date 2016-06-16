package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chema.gplbankandroid.R;

import java.util.ArrayList;

import clasescomunes.modelo.productos.Tarjeta;

/**
 * Adaptador de un ArryList de tarjetas para poblar un listView
 * Created by JoséMaría on 21/05/2016.
 */
public class TarjetaAdapter extends ArrayAdapter{

    private Context context;
    private int resource;
    private ArrayList<Tarjeta> tarjetas;

    public TarjetaAdapter(Context context, int resource, ArrayList<Tarjeta> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        tarjetas = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View item = convertView;
        ViewHolder holder;
        if (item == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(resource, null);
            holder = new ViewHolder();
            holder.numeroTarjeta = (TextView) item.findViewById(R.id.tv_numero_tarjeta);
            holder.dispuesto = (TextView) item.findViewById(R.id.tv_dispuesto);
            holder.caducidad = (TextView) item.findViewById(R.id.tv_caducidad);
            item.setTag(holder);
        }
        else{
            holder = (ViewHolder)item.getTag();
        }

        holder.numeroTarjeta.setText(tarjetas.get(position).getNumeroTarjeta());
        holder.dispuesto.setText(String.valueOf(tarjetas.get(position).getImporte()));
        holder.caducidad.setText(String.valueOf(tarjetas.get(position).getCaducidad()));

        return item;
    }



    static class ViewHolder {
        TextView numeroTarjeta;
        TextView dispuesto;
        TextView caducidad;
    }
}
