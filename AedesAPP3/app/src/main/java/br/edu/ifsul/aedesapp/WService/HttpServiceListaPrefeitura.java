package br.edu.ifsul.aedesapp.WService;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import br.edu.ifsul.aedesapp.Modelo.Denuncia;
import br.edu.ifsul.aedesapp.Modelo.DenunciaAPI;
import br.edu.ifsul.aedesapp.Modelo.PrefeituraAPI;

public class HttpServiceListaPrefeitura extends AsyncTask<Void,Void,List<PrefeituraAPI>> {
    String nomepesquisa;
    /*public HttpServiceListaPrefeitura(String nomepes) {
        nomepesquisa=nomepes;
    }*/
    @Override
    protected List<PrefeituraAPI> doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        try {
            URL  url;
            url = new URL("http://www2.bage.ifsul.edu.br:8181/aedesapp-web/api/prefeituras");
            ////////////////////////////////
            // Requisitar todas as prefeituras
            // URL url = new URL("http://192.168.0.33:8080/aedesapp-web/api/prefeituras");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
        } catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        List<PrefeituraAPI> prefeituras = new Gson().fromJson(resposta.toString(), new TypeToken<List<PrefeituraAPI>>() {
        }.getType());
        //---- convertendo dados do JSON
        return prefeituras;
    }
}