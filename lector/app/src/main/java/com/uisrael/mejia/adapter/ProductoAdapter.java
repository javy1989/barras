package com.uisrael.mejia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uisrael.mejia.model.Producto;
import com.uisrael.mejia.lector.R;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ricardo on 24/01/2018.
 */

public class ProductoAdapter extends ArrayAdapter<Producto> {

    private List<Producto> productos;
    private Context context;


    public ProductoAdapter(List<Producto> productos, Context context) {

        super(context, R.layout.producto_layout, productos);
        this.productos = productos;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("Entro al adapter", "adapter");
        ViewHolder mViewHolder = new ViewHolder();
        Producto producto = this.productos.get(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.producto_layout, parent, false);
            mViewHolder.mNombre = (TextView) convertView.findViewById(R.id.txt_nombre);
            mViewHolder.mBarras = (TextView) convertView.findViewById(R.id.txt_barras);
            mViewHolder.mPrecio=(TextView)convertView.findViewById(R.id.txt_precio);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder=(ViewHolder)convertView.getTag();
        }

        mViewHolder.mNombre.setText(producto.getNombre());
        mViewHolder.mBarras.setText(producto.getBarras());
        mViewHolder.mPrecio.setText(producto.getPrecio().toString());
        return convertView;
    }

    static class ViewHolder {
        TextView mNombre;
        TextView mBarras;
        TextView mPrecio;
    }
}
