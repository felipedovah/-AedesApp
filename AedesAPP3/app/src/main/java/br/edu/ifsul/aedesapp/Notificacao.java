package br.edu.ifsul.aedesapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsul.aedesapp.Modelo.Prefeitura;
import br.edu.ifsul.aedesapp.Trabzin.PrefeituraBD;

public class Notificacao extends AppCompatActivity {
    private PrefeituraBD prefeituraBD;
    private Prefeitura prefeitura;
    public List<Prefeitura> prefeituras;
    //ListView lista;
    EditText editnomezin;
    TextView aliascodigo;
    ArrayAdapter<Prefeitura> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);
        editnomezin = (EditText)findViewById(R.id.edit_nome);
        aliascodigo = (TextView)findViewById(R.id.id_txt);
        //aliaslistinha = (ListView)findViewById(R.id.localizalista);

        prefeituraBD = PrefeituraBD.getInstance(this);
        prefeitura = new Prefeitura();

        verificaparametro();

    }

    private void verificaparametro() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("Objeto")==null){
            // Toast.makeText(this, "Vazio", Toast.LENGTH_SHORT).show();/
            // como o metodo verificarparametro está sendo chamado no oncreate ou no onstart,
            // é necessário verificar com o if acima se o objeto está vazio (novo) ou se o objeto vem de um clique
            // em uma lista.
            prefeitura = new Prefeitura();
        }
        else {
            //  Toast.makeText(this, "Cheio", Toast.LENGTH_SHORT).show();
            prefeitura = (Prefeitura) intent.getSerializableExtra("Objeto"); // nome do parametro recebido é Objeto...
            aliascodigo.setText(prefeitura.get_id().toString());
            editnomezin.setText(prefeitura.getNome());
            editnomezin.requestFocus();
        }
    }

    private void ClickMe() {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)
                new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Alerta").setContentText("Status da denuncia: 3 atualizado para: Concluído");
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_notificacao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item_salvar){

            if (prefeitura.get_id() == null) { //se é uma inclusão
                prefeitura = new Prefeitura(); //apaga dados antigos
            }
            prefeitura.setNome(editnomezin.getText().toString());

            prefeituraBD.save(prefeitura);

            ClickMe();
        }
        else if(id == R.id.item_procurar){
            prefeituras = prefeituraBD.getByname(editnomezin.getText().toString());
            prefeitura = new Prefeitura();

            prefeitura.set_id(prefeituras.get(0).get_id());
            prefeitura.setNome(prefeituras.get(0).getNome());

            editnomezin.setText(prefeitura.getNome().toString());
            aliascodigo.setText(prefeitura.get_id().toString());


        }
        else if(id == R.id.item_excluir){
            ClickMe();

            Prefeitura excluir = new Prefeitura();
            excluir.set_id(Long.valueOf(aliascodigo.getText().toString()));

            prefeituraBD.delete(excluir);

        }

        return true;
    }




}
