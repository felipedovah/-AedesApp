package br.edu.ifsul.aedesapp.ViewPager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.edu.ifsul.aedesapp.Modelo.DenunciaAPI;
import br.edu.ifsul.aedesapp.R;
import br.edu.ifsul.aedesapp.RecyclerView.DenunciaAdapter;
import br.edu.ifsul.aedesapp.WService.HttpServiceListaDenuncasAPI;

/**
 * A simple {@link Fragment} subclass.
 */
//imagem, mapa, data, endereco, status

public class FragmentListaDenuncias extends Fragment {
    RecyclerView recyclerView;
    DenunciaAdapter denunciaAdapter;
    TextView lista_vazia, lista_vazia2, lista_vazia3;
    FloatingActionButton float_atualizar;
    ListView aliaslistview;
    DenunciaAPI denuncia;
    //ImageView imageVer;
    List<DenunciaAPI> denuncias;
    String tokin;

    public FragmentListaDenuncias() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_lista_denuncias, container, false);
        //aliaslistview=view.findViewById(R.id.listviewlistatadenuncias);

        recyclerView = view.findViewById(R.id.rView);
        float_atualizar = view.findViewById(R.id.float_atualiza);
        lista_vazia = view.findViewById(R.id.listaVazia);
        lista_vazia2 = view.findViewById(R.id.listaVazia2);
        lista_vazia3 = view.findViewById(R.id.listaVazia3);

        float_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float_atualizar.getBackground().setAlpha(100);
                float_atualizar.setEnabled(false);
                float_atualizar.setAlpha(100);
                atualizarOcorrencias();
            }
        });

        FirebaseApp.initializeApp(getContext());
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token = task.getResult().getToken();
                Log.d("token", token);
                tokin = token;

            }
        });

        return view;
    }
    private void atualizarOcorrencias(){
        try {

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            denuncias = new HttpServiceListaDenuncasAPI(tokin).execute().get();
            denunciaAdapter = new DenunciaAdapter(getContext(), denuncias);
            recyclerView.setAdapter(denunciaAdapter);
            if(denuncias.size() > 0){
                lista_vazia.setVisibility(View.INVISIBLE);
                lista_vazia2.setVisibility(View.INVISIBLE);
                lista_vazia3.setVisibility(View.INVISIBLE);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        denunciaAdapter.notifyDataSetChanged();
        float_atualizar.getBackground().setAlpha(255);
        float_atualizar.setEnabled(true);
        float_atualizar.setAlpha(255);
    }

}
