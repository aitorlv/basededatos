package com.example.aitor.pruebabasedatos;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class Partidos extends Activity {
    private GestorPartido gj;
    private EditText etValoracion, etContrincante;
    private Adaptador ad;
    private String []id=new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidos);
        gj = new GestorPartido(this);
        etValoracion = (EditText)findViewById(R.id.etVal);
        etContrincante = (EditText)findViewById(R.id.etContri);
        final ListView lv = (ListView)findViewById(R.id.lvPartido);
        Bundle b=this.getIntent().getExtras();
        id[0]= b.getLong("id")+"";
        gj.open();
        Cursor c = gj.getCursor(Contrato.TablaPartido.IDJUGADOR+" = ?",id,null);
        ad = new Adaptador(this, c);
        lv.setAdapter(ad);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partidos, menu);
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
    public void tostada(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void alta(View v){
        String  valoracion, contrincante,idJugador;
        idJugador=id[0];
        valoracion = etValoracion.getText().toString();
        contrincante = etContrincante.getText().toString();
        Partido obj = new Partido(Long.parseLong(idJugador), contrincante,Integer.parseInt(valoracion));
        long insert = gj.insert(obj);
        tostada("El partido se se ha creado con un ID " + insert);
        Cursor c = gj.getCursor(Contrato.TablaPartido.IDJUGADOR+" = ?",id,null);
        ad.changeCursor(c);
        vaciarCampos();
    }

    public void vermedia(View v){

        tostada("La media es: "+gj.media(id[0].toString())+"");
    }

    public void vaciarCampos(){
        etValoracion.setText("");
        etContrincante.setText("");
    }
}
