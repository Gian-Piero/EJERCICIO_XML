package com.example.tratamientoxml.Actividad1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tratamientoxml.R;

import java.util.List;

public class Actividad1 extends AppCompatActivity {


    public String url = "https://e00-elmundo.uecdn.es/elmundo/rss/espana.xml";
    public List<Noticia> noticias;
    public AdaptadorNoticia adaptadorNoticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad1);
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
            RssParserSAX saxparser = new RssParserSAX(params[0]);
            noticias = saxparser.parse();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los títulos en pantalla
            cargarLista();
        }

    }

    private void cargarLista(){
        RecyclerView recyclerView = findViewById(R.id.lista_noticias);
        adaptadorNoticia = new AdaptadorNoticia(this, noticias);

        recyclerView.setAdapter(adaptadorNoticia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptadorNoticia.setOnItemClickListener(new AdaptadorNoticia.OnItemClickListener() {
            @Override
            public void onNoticiaPulsada(int position) {
                Uri uriUrl = Uri.parse(adaptadorNoticia.mdata.get(position).getLink());
                Intent verLink = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(verLink);
            }
        });
    }


    public static class AdaptadorNoticia extends RecyclerView.Adapter<AdaptadorNoticia.myViewHolder>{

        public Context context;
        public List<Noticia> mdata;
        public OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onNoticiaPulsada(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        public AdaptadorNoticia(Context context, List<Noticia> mdata){
            this.context = context;
            this.mdata = mdata;
        }


        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.item_noticia, parent, false);
            return new myViewHolder(v, mListener);
        }

        @Override
        public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
            holder.txtTitulo.setText(mdata.get(position).getTitulo());
            holder.txtDescripcion.setText(mdata.get(position).getDescripcion());
            holder.txtCategoria.setText("• " + mdata.get(position).getCategoria());
        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{

            TextView txtTitulo;
            TextView txtDescripcion;
            TextView txtCategoria;


            public myViewHolder(View itemView, final OnItemClickListener listener){
                super(itemView);
                txtTitulo = itemView.findViewById(R.id.txtTitulo);
                txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
                txtCategoria = itemView.findViewById(R.id.txtCategoria);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onNoticiaPulsada(position);
                            }
                        }
                    }
                });

            }
        }
    }
}
