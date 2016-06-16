package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chema.gplbankandroid.R;

import java.util.ArrayList;

import clasescomunes.modelo.productos.CuentaAhorro;

/**
 * Adaptador de un ArrayList de CuentaAhorro para poblar un listView.
 * Created by JoséMaría on 21/05/2016.
 */
public class CuentaAhorroAdapter extends ArrayAdapter{

    private Context context;
    private int resource;
    private ArrayList<CuentaAhorro> cuentasAhorro;

    public CuentaAhorroAdapter(Context context, int resource, ArrayList<CuentaAhorro> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        cuentasAhorro = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View item = convertView;
        ViewHolder holder;
        if (item == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(resource, null);
            holder = new ViewHolder();
            holder.iban = (TextView) item.findViewById(R.id.tv_iban);
            holder.saldo = (TextView) item.findViewById(R.id.tv_saldo);
            holder.tipoAcreedor = (TextView) item.findViewById(R.id.tv_remuneracion);
            item.setTag(holder);
        }
        else{
            holder = (ViewHolder)item.getTag();
        }

        holder.iban.setText(cuentasAhorro.get(position).getIban());
        holder.saldo.setText(String.valueOf(cuentasAhorro.get(position).getImporte()));
        holder.tipoAcreedor.setText(String.valueOf(cuentasAhorro.get(position).getTipoAcreedor()));

        return item;
    }



    static class ViewHolder {
        TextView iban;
        TextView saldo;
        TextView tipoAcreedor;
    }
}
