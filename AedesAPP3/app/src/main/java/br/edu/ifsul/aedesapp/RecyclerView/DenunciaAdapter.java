package br.edu.ifsul.aedesapp.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.List;

import br.edu.ifsul.aedesapp.Activitys.DetalhesActivity;
import br.edu.ifsul.aedesapp.Modelo.Denuncia;
import br.edu.ifsul.aedesapp.Modelo.DenunciaAPI;
import br.edu.ifsul.aedesapp.R;
import br.edu.ifsul.aedesapp.ViewPager.Inicio;

public class DenunciaAdapter extends RecyclerView.Adapter<DenunciaAdapter.DenunciaViewHolder> {
    private Context dCtx;
    ImageView imageVer;
    Dialog dialog2;
    DenunciaAPI denunciaAPI;
    Integer denunciaid;
    private List<DenunciaAPI> denunciaList;


    public DenunciaAdapter(Context dCtx, List<DenunciaAPI> denunciaList) {
        this.dCtx = dCtx;
        this.denunciaList = denunciaList;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;

        public DownloadImage(ImageView imageView){
            this.imageView = imageView;
            //Toast.makeText(get, "calma ae", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imgUrl = strings[0];
            Bitmap bitmap = null;
            try{
                InputStream in = new java.net.URL(imgUrl).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                Log.e("Error Message:", e.getMessage());
                e.printStackTrace();
            }

            return bitmap;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

    @Override
    public DenunciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(dCtx);
        View view = inflater.inflate(R.layout.recycler_layout, null);
        return new DenunciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DenunciaViewHolder holder, final int position) {
        denunciaAPI = denunciaList.get(position);
        Picasso.with(dCtx).load(denunciaAPI.getImagem()).fit().into(holder.imageHolder);
        Integer de_id = position + 1;
        holder.textId.setText("Ocorrência : " + de_id + " (" + denunciaAPI.getId().toString() + ")");

        holder.imageVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txt_id;
                Log.d("id:", denunciaList.get(position).getId().toString());
                Fragment fragment = new Fragment();

                Intent intent = new Intent(dCtx, DetalhesActivity.class);
                intent.putExtra("denunciaid", denunciaList.get(position).getId());
                intent.putExtra("foto", denunciaList.get(position).getImagem());
                intent.putExtra("data", denunciaList.get(position).getDataCriacao());
                intent.putExtra("latitude", denunciaList.get(position).getLatitude());
                intent.putExtra("longitude", denunciaList.get(position).getLongitude());
                intent.putExtra("statusid", denunciaList.get(position).getStatus());

                //Bundle bundle = new Bundle();
                //bundle.putInt("denunciaid", denunciaList.get(position).getId());
                //bundle.putString("foto", denunciaList.get(position).getImagem());
                //bundle.putString("data", denunciaList.get(position).getDataCriacao());
                //bundle.putDouble("latitude", denunciaList.get(position).getLatitude());
                //bundle.putDouble("longitude", denunciaList.get(position).getLongitude());
                dCtx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return denunciaList.size();
    }

    class DenunciaViewHolder extends RecyclerView.ViewHolder {
        TextView textId;
        ImageView imageHolder;
        ImageView imageVer;

         public DenunciaViewHolder(View itemView) {
            super(itemView);

            imageHolder = itemView.findViewById(R.id.imagem);
            textId = itemView.findViewById(R.id.denunciaid);
            imageVer = itemView.findViewById(R.id.image_ver);
            imageVer.getBackground().setAlpha(0);
            //textStatus = itemView.findViewById(R.id.statusid);
        }

    }

}

