package br.edu.ifsul.aedesapp.ViewPager;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.edu.ifsul.aedesapp.Modelo.Denuncia;
import br.edu.ifsul.aedesapp.Modelo.DenunciaAPI;
import br.edu.ifsul.aedesapp.Opcoes.Opcoes;
import br.edu.ifsul.aedesapp.R;
import br.edu.ifsul.aedesapp.RecyclerView.DenunciaAdapter;
import br.edu.ifsul.aedesapp.WService.HttpService;
import br.edu.ifsul.aedesapp.WService.HttpServiceListaDenuncasAPI;
import br.edu.ifsul.aedesapp.WService.HttpServicePost;
import dmax.dialog.SpotsDialog;

//http://www2.bage.ifsul.edu.br:8181/aedesapp-web/arquivos/40.jpg

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDenunciar extends Fragment implements OnMapReadyCallback, LocationListener {
    long MIN_TIME_BW_UPDATES = 6000;
    long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

    Integer denunciasFalsas = 0;
    GoogleMap mMap;
    Boolean novaposicao = false;
    MapView mMapView;
    MarkerOptions markerOptions = new MarkerOptions();
    View mView;
    EditText editObervacoes;
    String tokin;
    LatLng posicao, novaposicaoLatLng;
    ImageButton image_enviar;
    Denuncia denuncia;
    FloatingActionButton float_cam, float_gps;
    Dialog dialog;
    Bitmap photo;
    ProgressBar progressBar;
    String obser;
    List<DenunciaAPI> denuncias;
    ImageView imgDenuncia,testeimagem;
    Integer tamanhoDenuncia;
    String tamanhoString;

    private static final int CAMERA_REQUEST = 123;
    private byte [] imagem = null;

    private Marker currentLocationMarker;
    private LatLng currentLocationLatLong;

    public FragmentDenunciar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_fragment_denunciar, container, false);
        //imageView = (ImageView)mView.findViewById(R.id.botaoCamera);
        float_cam = (FloatingActionButton)mView.findViewById(R.id.float_cam);
        float_gps = (FloatingActionButton)mView.findViewById(R.id.float_local1);
        imgDenuncia = (ImageView)mView.findViewById(R.id.imagemdialog);
        progressBar = (ProgressBar)mView.findViewById(R.id.progressBar);
        float_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("idPrefeitura", Context.MODE_PRIVATE);
                if(preferences.getInt("idprefeitura", 0) != 0){
                    try {
                        denuncias = new HttpServiceListaDenuncasAPI(tokin).execute().get();

                        //Log.i("denuncia: " + tamanhoString);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tamanhoDenuncia = denuncias.size();
                    tamanhoString = tamanhoDenuncia.toString();
                    denunciasFalsas = 0;
                    for(Integer a = 0; a < tamanhoDenuncia; a++){
                        if(denuncias.get(a).getStatus() == 3){
                            denunciasFalsas++;
                        }
                    }

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                    //Toast.makeText(getContext(), "Tamanho: " + tamanhoString + "\nDenuncias falsas: " + denunciasFalsas, Toast.LENGTH_SHORT).show();

                }else{
                    //Snackbar snackbar = Snackbar.make(getView(),"Coloque uma cidade de preferência." , Snackbar.LENGTH_LONG);
                    //snackbar.setAction(R.string.ir_opcoes, this);
                    //.show();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Cidade de preferência");
                    alertDialog.setMessage("Você não selecionou uma prefeitura de preferencia, deseja ir as opções?");
                    alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), Opcoes.class);
                            startActivity(intent);
                        }
                    });

                    alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                }
            }
        });
        float_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();

                startGettingLocations();
                SharedPreferences preferences = getActivity().getSharedPreferences("idPrefeitura", Context.MODE_PRIVATE);
                Log.i("testepref: ", String.valueOf(preferences.getInt("idprefeitura", 0)));
            }
        });

        float_cam.setEnabled(false);
        float_cam.setAlpha(100);
        float_cam.getBackground().setAlpha(100);

        FirebaseApp.initializeApp(getContext());
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful()){
                    String token = task.getResult().getToken();
                    //Toast.makeText(getContext(), "Tokeen1: " + token, Toast.LENGTH_SHORT).show();
                    Log.d("token", token.toString());
                    tokin = token;

                }
                else {
                    Log.w("token", "task" + task.getException());
                }
            }
        });



        return mView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==CAMERA_REQUEST && resultCode== Activity.RESULT_OK){
            photo = (Bitmap)data.getExtras().get("data");
            byte[] img = getBitmapAsbyteArray(photo);
            imagem = img;
            Date currentTime = Calendar.getInstance().getTime();
            String datahora = currentTime.toString();
            denuncia = new Denuncia();
            if(img != null){
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialogview);
                dialog.setTitle("Envio");
                image_enviar = dialog.findViewById(R.id.image_enviar);
                image_enviar.getBackground().setAlpha(0);
                //caso clique e o intent da cidade esteja null, vc pede pra ir nas configurações
                image_enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (denuncia.get_id() == null) {
                            SharedPreferences preferences = getActivity().getSharedPreferences("idPrefeitura", Context.MODE_PRIVATE);
                            obser = editObervacoes.getText().toString();
                            denuncia = new Denuncia();
                            if(novaposicao == true){
                                denuncia.setLatitude(novaposicaoLatLng.latitude);
                                denuncia.setLongitude(novaposicaoLatLng.longitude);
                            }
                            if(novaposicao == false){
                                denuncia.setLatitude(posicao.latitude);
                                denuncia.setLongitude(posicao.longitude);
                            }
                            denuncia.setObservacoes(obser);
                            denuncia.setPrefeitura_id(preferences.getInt("idprefeitura", 0));
                            denuncia.setToken(tokin);
                            denuncia.setUsuario_id(1);
                            denuncia.setImagem(imagem);
                            if(denunciasFalsas >= 3){
                                Toast.makeText(getContext(), "Sua denuncia foi enviada e será analisada, muito obrigado.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                new HttpServicePost(denuncia).execute();
                                Toast.makeText(getContext(), "Sua denuncia foi enviada e será analisada, muito obrigado.", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();
                        }
                    }
                });
                editObervacoes = dialog.findViewById(R.id.observacoes);
                /*FloatingActionButton float_envio = (FloatingActionButton)dialog.findViewById(R.id.float_envio);
                float_envio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/
                testeimagem = dialog.findViewById(R.id.imagemdialog);
                testeimagem.setImageBitmap(photo);
                dialog.show();

            }
        }
    }

    private byte[] getBitmapAsbyteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mMapView = mView.findViewById(R.id.mapViu);
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(getContext());

                dialog2.setTitle("Deseja trocar a licalização?");//
                dialog2.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MIN_TIME_BW_UPDATES = 0;
                        MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

                        mMap.clear();
                        novaposicao = true;
                        novaposicaoLatLng = latLng;
                        markerOptions.position(novaposicaoLatLng);
                        markerOptions.title("Localização Atual");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        mMap.addMarker(markerOptions);
                        float_gps.setEnabled(true);
                        float_gps.setAlpha(255);
                        float_gps.getBackground().setAlpha(255);
                        float_cam.setEnabled(true);
                        float_cam.setAlpha(255);
                        float_cam.getBackground().setAlpha(255);

                    }
                });
                dialog2.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog2.show();
            }
        });

        //LatLng syd = new LatLng(-34, -51);
        //mMap.addMarker(new MarkerOptions().position(syd).title("Marca Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(syd));
        //startGettingLocations();
    }

    private void startGettingLocations() {
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean canGetLocation = true;
        int ALL_PERMISSIONS_RESULT = 101;
        MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
        /*long*/ MIN_TIME_BW_UPDATES = 200;
        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //checa se o gps e a rede estão ativos, se não, pede para o usuario ligar
        if(!isGPS && !isNetwork){
            showSettingAlert();
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (permissionsToRequest.size() > 0){
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                    canGetLocation = false;
                }
            }
        }

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getContext(), "Permissão negada", Toast.LENGTH_SHORT).show();
            return;
        }

        if(canGetLocation){
            if(isGPS){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }
            else if(isNetwork){
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }
            else{
                Toast.makeText(getContext(), "Não foi possivel obter a localizacão", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("GPS desativado");
        alertDialog.setMessage("Ativar GPS?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();
        for(String perm : wanted){
            if (!hasPermission(perm)){
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission){
        if(canAsPermission()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                return(getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAsPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public void onLocationChanged(Location location){
        //mView.setVisibility(View.VISIBLE);
        //alert.dismiss();
        mMap.clear();
        novaposicao = false;
        float_cam.setEnabled(true);
        float_cam.setAlpha(255);
        float_cam.getBackground().setAlpha(255);
        float_gps.setEnabled(false);
        float_gps.setAlpha(100);
        float_gps.getBackground().setAlpha(100);

        if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }
        currentLocationLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptions.position(currentLocationLatLong);
        markerOptions.title("Localização Atual");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentLocationMarker = mMap.addMarker(markerOptions);


        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target(currentLocationLatLong).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        setPosicao(markerOptions.getPosition());
    }

    public LatLng getPosicao() {
        return posicao;
    }

    public void setPosicao(LatLng posicao) {
        this.posicao = posicao;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
