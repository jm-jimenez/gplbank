package controlador;

import android.os.AsyncTask;

import clasescomunes.utils.Info;

/**
 * Created by JoséMaría on 08/05/2016.
 */
public abstract class GestorTareas extends AsyncTask implements MiInterface {
    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    public abstract Info callback(Object result);

}
