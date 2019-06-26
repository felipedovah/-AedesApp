package br.edu.ifsul.aedesapp.ViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifsul.aedesapp.R;

/**
 * A simple {@link Fragment} subclass.
 */

//icone de opçoes para logar ou criar acc
//+ para criar conta e outro para logar
// um float de opção para abrir dois

public class FragmentUsuario extends Fragment {


    public FragmentUsuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_usuario, container, false);
    }

}
