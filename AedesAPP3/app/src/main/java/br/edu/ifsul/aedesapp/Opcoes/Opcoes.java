package br.edu.ifsul.aedesapp.Opcoes;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.edu.ifsul.aedesapp.Modelo.PrefeituraAPI;
import br.edu.ifsul.aedesapp.R;
import br.edu.ifsul.aedesapp.WService.HttpServiceListaDenuncasAPI;
import br.edu.ifsul.aedesapp.WService.HttpServiceListaPrefeitura;

public class Opcoes extends AppCompatActivity {
    Spinner listPrefeituras;
    List<PrefeituraAPI> prefeituras;
    List<String> listaprefeituras2 = new ArrayList<>();
    String[] listaopcoes = {"Cidade de preferência."};
    ListView listView_opcoes;
    ImageButton imageButtonSalvar;
    Dialog dialog_cidades;
    TextView textView_prefeitura;
    Integer idSelecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);
        listView_opcoes = findViewById(R.id.listview_opcoes);
        listView_opcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    try {
                        prefeituras = new HttpServiceListaPrefeitura().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    listaprefeituras2.clear();
                    for(Integer a = 0; a < prefeituras.size(); a++){
                        listaprefeituras2.add(prefeituras.get(a).getNome());
                    }
                    dialog_cidades = new Dialog(Opcoes.this);
                    dialog_cidades.setContentView(R.layout.dialog_opcoes_cidadepreferencia);
                    imageButtonSalvar = dialog_cidades.findViewById(R.id.image_salvar);
                    listPrefeituras = dialog_cidades.findViewById(R.id.spinner_prefeituras);
                    imageButtonSalvar.getBackground().setAlpha(0);
                    textView_prefeitura = dialog_cidades.findViewById(R.id.txt_prefeituras);
                    textView_prefeitura.setTextColor(Color.BLACK);
                    ArrayAdapter adapter = new ArrayAdapter<String>(Opcoes.this, android.R.layout.simple_list_item_1, listaprefeituras2);
                    listPrefeituras.setAdapter(adapter);
                    imageButtonSalvar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for(Integer a = 0; a < prefeituras.size(); a++){
                                if(listPrefeituras.getSelectedItem() == prefeituras.get(a).getNome()){
                                    idSelecionado = prefeituras.get(a).getId();
                                    Log.i("idprefeitura:", idSelecionado.toString());
                                    SharedPreferences preferences = getSharedPreferences("idPrefeitura", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor ed = preferences.edit();
                                    ed.putInt("idprefeitura",idSelecionado);
                                    ed.apply();
                                    NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(Opcoes.this)
                                            .setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.mipmap.ic_logoaedesnotification).setContentTitle("Aedes App")
                                            .setContentText("Cidade alterada para: " + prefeituras.get(a).getNome());
                                    NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(1, builder.build());
                                }
                            }
                            //Snackbar.make(view,"Prefeitura alterada com sucesso." , Snackbar.LENGTH_LONG).show();
                            dialog_cidades.dismiss();
                        }
                    });

                    dialog_cidades.setTitle("Cidade de preferência");
                    dialog_cidades.show();
                }
            }
        });

        //listaprefeituras2.add(prefeituras.get(0).getNome().toString());

        //listaprefeituras2.add("asdads");

        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaopcoes);
        listView_opcoes.setAdapter(adapter2);



    }
}
