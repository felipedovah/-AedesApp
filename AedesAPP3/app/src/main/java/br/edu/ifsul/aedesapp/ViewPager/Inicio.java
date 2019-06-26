package br.edu.ifsul.aedesapp.ViewPager;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifsul.aedesapp.Adapter.ViewPagerAdapter;
import br.edu.ifsul.aedesapp.Opcoes.Opcoes;
import br.edu.ifsul.aedesapp.R;

public class Inicio extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setTitle("AedesApp");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setLogo(R.mipmap.ic_logo_app);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu ){
        getMenuInflater().inflate(R.menu.menu_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item_opcoes:
                Intent intent = new Intent(this, Opcoes.class);
                startActivity(intent);
                Log.i("caraca", "opa");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentDenunciar(), "Denunciar");
        adapter.addFragment(new FragmentListaDenuncias(), "Ocorrências");
        //adapter.addFragment(new FragmentUsuario(), "+ Info");
        viewPager.setAdapter(adapter);
    }
}
