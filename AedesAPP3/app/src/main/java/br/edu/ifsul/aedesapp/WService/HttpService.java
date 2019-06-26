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

public class HttpService extends AsyncTask<Void,Void,List<Denuncia>> {
    String nomepesquisa;
    public HttpService(String nomepes) {
        nomepesquisa=nomepes;
    }
    @Override
    protected List<Denuncia> doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        try {
            URL  url;
            if (nomepesquisa!=null)
            {url = new URL("http://10.0.86.214/webservice2018/wsGetData.php?nomepesquisa="+nomepesquisa);}
            else
            {url = new URL("http://10.0.86.214/webservice2018/wsGetData.php");}
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
        List<Denuncia> denuncias = new Gson().fromJson(resposta.toString(), new TypeToken<List<Denuncia>>() {
        }.getType());
       //---- convertendo dados do JSON
        return denuncias;
    }



    public String post(JSONObject data) {
        try {
            String resposta="";
           // final URL url = new URL("http://www.diegoneves.pro.br/webservice2018/wsPutData.php");
            URL url = new URL("http://10.0.86.214/webservice2018/wsPutData.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");

            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            writer.write(data.toString());
            writer.flush();
            writer.close();
            outputStream.close();
            connection.connect();
            int responseCode=connection.getResponseCode();
            if (responseCode==HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=br.readLine())!=null){
                    resposta+=line;
                }
            }else
            {
                resposta="";
            }

           // InputStream stream = connection.getInputStream();

           // new Scanner(stream, "UTF-8").next();

        } catch (Exception e) {
            Log.e("Your tag", "Error", e);
        }

        return null;
    }
}