package com.uisrael.mejia.lector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.uisrael.mejia.control.ProductoControl;
import com.uisrael.mejia.control.ProductoControlmpl;
import com.uisrael.mejia.model.Producto;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ProductoActivity extends AppCompatActivity {

    ImageView imageView;
    private ZXingScannerView escanerView;
    private EditText mNombre;
    private EditText mCodigo;
    private EditText mPrecio;
    private EditText mCantidad;
    private Button btnScan;
    private boolean tipo;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        Button btnCamera = (Button) findViewById(R.id.btnCamara);
        imageView = (ImageView) findViewById(R.id.imageViewProducto);
        mCodigo = (EditText) findViewById(R.id.editTextCodigo);
        mCodigo.setEnabled(false);
        mNombre = (EditText) findViewById(R.id.editTextNombre);
        mPrecio=(EditText)findViewById(R.id.editTextPrecio);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                tipo = true;
            }
        });
        btnScan = (Button) findViewById(R.id.btnScan);
        final Activity activity = this;
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Escanear Producto");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
                tipo = false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (tipo) {
            super.onActivityResult(requestCode, resultCode, data);
            image = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);

        } else {
            if (result != null) {
                if (result.getContents() == null) {
                    Log.d("MainActivity", "Escaneo Cancelado");
                    Toast.makeText(this, "Escaneao Cancelado", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("MainActivity", "Escaneo Realizado");
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                    mCodigo.setText(result.getContents());
                }
            }
        }

    }

    public void scan(View view) {
        escanerView = new ZXingScannerView(this);
        escanerView.setResultHandler(new scanerclass());
        setContentView(escanerView);
        escanerView.startCamera();

    }

    class scanerclass implements ZXingScannerView.ResultHandler {

        @Override
        public void handleResult(Result result) {
            String dato = result.getText();
            setContentView(R.layout.activity_producto);
            escanerView.stopCamera();
            Log.e("reciviendo dato", dato);
            //mBarras = (EditText) findViewById(R.id.txt_barras);
            mCodigo.setText(dato);
            // producto.setBarras(dato);
        }

    }

    public void ingresar(View view) {
        try {
            if (validar()) {
                Producto p = new Producto();
                p.setNombre(mNombre.getText().toString());
                p.setBarras(mCodigo.getText().toString());
                p.setPrecio(new BigDecimal(mPrecio.getText().toString()));
                p.setCantidad(Integer.parseInt(mCantidad.getText().toString()));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (image != null) {
                    image.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
                    String decode = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    p.setFoto(decode);
                }

                boolean resultado = new addProducto().execute(p).get();
                if (resultado) {
                    Intent intent = new Intent(ProductoActivity.this, ListActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(ProductoActivity.this);
                    dialogo.setTitle("Error"); // setando título
                    // setando mensagem
                    dialogo.setMessage("Error al guardar");
                    dialogo.setNeutralButton("OK", null); // setando botão
                    dialogo.show();
                }
            }else{
                AlertDialog.Builder dialogo = new AlertDialog.Builder(ProductoActivity.this);
                dialogo.setTitle("Resultado"); // setando título
                // setando mensagem
                dialogo.setMessage("Campos vacios");
                dialogo.setNeutralButton("OK", null); // setando botão
                dialogo.show();
            }

        } catch (Exception e) {
            Log.e("Error", e.getLocalizedMessage());
        }
    }

    private class addProducto extends AsyncTask<Producto, Void, Boolean> {
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        @Override
        protected Boolean doInBackground(Producto... productos) {
            ProductoControl productoControl = new ProductoControlmpl();
            return productoControl.addProducto(productos[0]);
        }
    }

    private boolean validar() {
        if (mNombre.getText().toString() == null || mNombre.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (mPrecio.getText().toString() == null || mPrecio.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (mCodigo.getText() == null || mCodigo.getText().toString().trim().isEmpty()) {
            return false;
        }
        if (mCantidad.getText().toString() == null || mCantidad.getText().toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }

}
