package com.uisrael.mejia.lector;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uisrael.mejia.adapter.ProductoAdapter;
import com.uisrael.mejia.control.ProductoControl;
import com.uisrael.mejia.control.ProductoControlmpl;
import com.uisrael.mejia.model.Producto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListActivity extends AppCompatActivity {

    private ListView listViewProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        try {
            final List<Producto> productos = new callWsProductos().execute().get();
            listViewProductos = (ListView) findViewById(R.id.listViewProducto);
            if (productos!=null){
                listViewProductos.setAdapter(new ProductoAdapter(productos, getApplicationContext()));
            }

            listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent visorProducto=new Intent(view.getContext(),ProductoViewActivity.class);
                    Producto p =productos.get(position);
                    visorProducto.putExtra("nombre",p.getNombre());
                    visorProducto.putExtra("precio",p.getPrecio().toString());
                    visorProducto.putExtra("cantidad",p.getCantidad());
                    visorProducto.putExtra("barras",p.getBarras());
                    visorProducto.putExtra("foto",p.getFoto());
                    startActivity(visorProducto);
                }
            });

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            AlertDialog.Builder dialogo = new AlertDialog.Builder(getApplicationContext());
            dialogo.setMessage(e.getMessage());
            dialogo.create().show();   // chamando o AlertDialog
            e.printStackTrace();
            Log.e("error", e.getLocalizedMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e("error", e.getLocalizedMessage());
            System.out.println(e.getMessage());
            AlertDialog.Builder dialogo = new AlertDialog.Builder(getApplicationContext());
            dialogo.setMessage(e.getMessage());
            dialogo.create().show();   // chamando o AlertDialog
        }

    }

    private class callWsProductos extends AsyncTask<Void, Void, List<Producto>> {
        @Override
        protected List<Producto> doInBackground(Void... voids) {
            try {
                ProductoControl productoControl = new ProductoControlmpl();
                return productoControl.listAll();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Producto> productos) {
            super.onPostExecute(productos);

        }
    }

    public void addProducto(View v){
        Intent intent = new Intent(this,ProductoActivity.class);
        startActivity(intent);
    }

    public void about(View v){
        Intent intent=new Intent(this,AboutActivity.class);
        startActivity(intent);
    }
}
