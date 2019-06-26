package br.edu.ifsul.aedesapp.WService;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import br.edu.ifsul.aedesapp.Modelo.Denuncia;
import br.edu.ifsul.aedesapp.ViewPager.Inicio;

public class HttpServicePost extends AsyncTask<String, Void, String> {
    Denuncia denuncia;
    boolean foi = false;
    public HttpServicePost(Denuncia denunciarecebida) {
        denuncia= new Denuncia();
        denuncia = denunciarecebida;
    }
    protected void onPreExecute() {
    }
    protected String doInBackground(String... arg0) {
        try {
            //URL url = new URL("http://192.168.0.33:8080/aedesapp-web/api/denuncia");
            URL url = new URL("http://www2.bage.ifsul.edu.br:8181/aedesapp-web/api/denuncia");

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("_id",denuncia.get_id());
            postDataParams.put("usuario_id",denuncia.getUsuario_id());
            postDataParams.put("latitude",denuncia.getLatitude());
            postDataParams.put("longitude", denuncia.getLongitude());
            postDataParams.put("observacoes", denuncia.getObservacoes());
            postDataParams.put("prefeitura_id", denuncia.getPrefeitura_id());
            postDataParams.put("token", denuncia.getToken());
            postDataParams.put("imagem", Base64.encodeToString(denuncia.getImagem(),Base64.DEFAULT));


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            Log.i("json:", postDataParams.toString());
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.i("json:", String.valueOf(conn.getResponseCode()));

            if(String.valueOf(conn.getResponseCode()) != null){
                //Snackbar.make(, "Ocorrência enviada, obrigado.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }


            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    Log.i("retorno:", sb.append(line).toString());
                    break;
                }
                in.close();
                return sb.toString();

            } else {
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }

    }


    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}