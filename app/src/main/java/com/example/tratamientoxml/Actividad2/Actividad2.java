package com.example.tratamientoxml.Actividad2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tratamientoxml.R;
import com.example.tratamientoxml.Actividad1.RssParserSAX;

import java.util.List;

public class Actividad2 extends AppCompatActivity {


    public String url = "http://www.aemet.es/xml/municipios/localidad_01059.xml";
    public List<Tiempo> tiempos;
    public AdaptadorTiempo adaptadorTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad2);
        cargarXML();
    }

    private void cargarXML()
    {
        //Carga del XML mediante tarea Asincrona
        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute(url);
    }

    //Tarea Asíncrona para cargar un XML en segundo plano
    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {
            RssParserSAXSimplificado saxparser = new RssParserSAXSimplificado(params[0]);
            tiempos = saxparser.parse();
            System.out.println("El tamaño de la lista es " + tiempos.size());
            return true;
        }

        protected void onPostExecute(Boolean result) {
            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los títulos en pantalla
            if (tiempos!=null)
                cargarLista();
        }

    }

    private void cargarLista(){
        RecyclerView recyclerView = findViewById(R.id.lista_tiempos);
        adaptadorTiempo = new AdaptadorTiempo(this, tiempos);

        recyclerView.setAdapter(adaptadorTiempo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    //Adaptador para la lista
    public static class AdaptadorTiempo extends RecyclerView.Adapter<AdaptadorTiempo.myViewHolder>{

        public Context context;
        public List<Tiempo> mdata;
        public OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onTiempoPulsado(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        public AdaptadorTiempo(Context context, List<Tiempo> mdata){
            this.context = context;
            this.mdata = mdata;
        }


        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.tiempo, parent, false);
            return new myViewHolder(v, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
            holder.txtProvincia.setText(mdata.get(position).getProvincia());
            holder.txtDia.setText(mdata.get(position).getDia());
            holder.txtEstado.setText(mdata.get(position).getEstadoCielo());
            holder.txtTemperaturaMin.setText(mdata.get(position).getTemperatura_min());
            holder.txtTemperaturaMax.setText(mdata.get(position).getTemperatura_max());

        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{

            TextView txtProvincia;
            TextView txtDia;
            TextView txtEstado;
            TextView txtTemperaturaMin;
            TextView txtTemperaturaMax;

            public myViewHolder(View itemView, final OnItemClickListener listener){
                super(itemView);
                txtProvincia = itemView.findViewById(R.id.txtProvincia);
                txtDia = itemView.findViewById(R.id.txtProvincia);
                txtEstado = itemView.findViewById(R.id.txtEstado);
                txtTemperaturaMin = itemView.findViewById(R.id.txtTemperaturaMin);
                txtTemperaturaMax = itemView.findViewById(R.id.txtTemperaturaMax);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onTiempoPulsado(position);
                            }
                        }
                    }
                });

            }
        }
    }
}

