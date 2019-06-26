package br.edu.ifsul.aedesapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import br.edu.ifsul.aedesapp.Modelo.Prefeitura;
import br.edu.ifsul.aedesapp.Trabzin.PrefeituraBD;

public class ListDenuncias extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Prefeitura prefeitura;
    private PrefeituraBD prefeituraBD;
    public ListView lista;
    private List<Prefeitura> prefeituras;
    ArrayAdapter<Prefeitura> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_denuncias);
        lista = (ListView)findViewById(R.id.list_pref);
        prefeitura = new Prefeitura();
        prefeituraBD = PrefeituraBD.getInstance(this);
        prefeituras = prefeituraBD.getAll();

        adapter = new ArrayAdapter<Prefeitura>(this, android.R.layout.simple_list_item_1, prefeituras);
        lista.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        lista.setOnItemClickListener(this);

        /*ArrayAdapter<Prefeitura> adapter = new ArrayAdapter<Prefeitura>(this, android.R.layout.simple_list_item_1, prefeituras);
        lista.setAdapter(adapter);*/
    }



    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menulistagem, menu);

        MenuItem item = menu.findItem(R.id.localizalista);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(onSearch());
        return true;
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        };
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        prefeitura = (Prefeitura) parent.getAdapter().getItem(i);
        Intent nova= new Intent(this, Notificacao.class);
        nova.putExtra("Objeto", prefeitura);
        startActivity(nova);
    }





}
