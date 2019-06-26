package br.edu.ifsul.aedesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VaiCidade extends AppCompatActivity {
    public ListView bla;
    public String[] menuzao = new String[]{" Cidade1:\n24% das denuncias",
            " Cidade2:\n26% das denuncias"," Cidade3:\n20% das denuncias",
            " Cidade4:\n15% das denuncias"," Cidade5:\n15% das denuncias"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vai_cidade);
        bla = (ListView)findViewById(R.id.listaCidades);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuzao);
        bla.setAdapter(adaptador);


    }
}
