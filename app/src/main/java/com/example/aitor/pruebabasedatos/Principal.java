package com.example.aitor.pruebabasedatos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class Principal extends Activity {

    private GestorJugador gj;
    private EditText etNombre, etTelefono, etValoracion, etFecha;
    private Adaptador ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        gj = new GestorJugador(this);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etTelefono = (EditText)findViewById(R.id.etTelefono);
        etFecha = (EditText)findViewById(R.id.etFecha);
        final ListView lv = (ListView)findViewById(R.id.lvJugadores);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Cursor cursor = (Cursor)lv.getItemAtPosition(posicion);
                Jugador obj = GestorJugador.getRow(cursor);
                Intent i=new Intent(getApplicationContext(),Partidos.class);
                i.putExtra("id",obj.getId());
                startActivity(i);
            }
        });



        gj.open();
        Cursor c = gj.getCursor();
        ad = new Adaptador(this, c);
        lv.setAdapter(ad);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gj.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gj.close();
    }


    public void alta(View v){
        String nombre, telefono, valoracion, fecha;
        nombre = etNombre.getText().toString();
        telefono = etTelefono.getText().toString();
        valoracion = etValoracion.getText().toString();
        fecha = etFecha.getText().toString();
        Jugador j = new Jugador(nombre, telefono, fecha);
        long id = gj.insert(j);
        tostada("El jugador " + j + " se ha creado con un ID " + id);
        Cursor c = gj.getCursor();
        ad.changeCursor(c);
        vaciarCampos();
    }



    public void tostada(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void vaciarCampos(){
        etNombre.setText("");
        etTelefono.setText("");
        etValoracion.setText("");
        etFecha.setText("");
    }
}
