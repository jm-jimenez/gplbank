package adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chema.gplbankandroid.R;

import java.util.ArrayList;

import clasescomunes.modelo.productos.CuentaNomina;

/**
 * Adaptador de un ArryList de cuentas nómina para poblar un listView
 * Created by JoséMaría on 21/05/2016.
 */
public class CuentaNominaAdapter extends ArrayAdapter{

    private Context context;
    private int resource;
    private ArrayList<CuentaNomina> cuentasNomina;

    public CuentaNominaAdapter(Context context, int resource, ArrayList<CuentaNomina> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        cuentasNomina = objects;
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
            holder.proximoCargo = (TextView) item.findViewById(R.id.tv_proximo_cargo);
            item.setTag(holder);
        }
        else{
            holder = (ViewHolder)item.getTag();
        }

        holder.iban.setText(cuentasNomina.get(position).getIban());
        holder.saldo.setText(String.valueOf(cuentasNomina.get(position).getImporte()));
        holder.proximoCargo.setText(String.valueOf(cuentasNomina.get(position).getProximoCargo()));

        return item;
    }



    static class ViewHolder {
        TextView iban;
        TextView saldo;
        TextView proximoCargo;
    }
}
