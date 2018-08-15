package com.uisrael.mejia.lector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProductoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_view);
        Intent intent=getIntent();
        Bundle b= intent.getExtras();

        TextView mNombrev=(TextView)findViewById(R.id.editTextNombrev);
        mNombrev.setEnabled(false);

        TextView mCodigov=(TextView)findViewById(R.id.editTextCodigov);
        mCodigov.setEnabled(false);

        TextView mPreciov=(TextView)findViewById(R.id.editTextPreciov);
        mPreciov.setEnabled(false);

        TextView mCantidav=(TextView)findViewById(R.id.editTextCantidadv);
        mCantidav.setEnabled(false);

        ImageView mImagev=(ImageView)findViewById(R.id.imageViewProductov);
        if (b!=null){
            mNombrev.setText(b.getString("nombre"));
            mPreciov.setText(b.getString("precio"));
            mCantidav.setText(b.getString("cantidad"));
            mCodigov.setText(b.getString("barras"));
            String foto=b.getString("foto");
            Glide.with(ProductoViewActivity.this).load(Base64.decode(foto,Base64.DEFAULT)).into(mImagev);
        }
    }
}
