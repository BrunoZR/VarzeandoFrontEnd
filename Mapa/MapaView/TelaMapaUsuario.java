package com.example.varzeando.Mapa.MapaView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.varzeando.Adapters.RecyclerViewAdapterQuadras;
import com.example.varzeando.Adapters.RecyclerViewAdapterQuadrasProximas;
import com.example.varzeando.CadastroQuadra.CadastroQuadraView.CadastroQuadra;
import com.example.varzeando.ClassesSuporte.Permissoes;
import com.example.varzeando.DetalhesPartida.DetalhesPartida;
import com.example.varzeando.DetalhesQuadra.DetalhesQuadraView.DetalhesQuadra;
import com.example.varzeando.Home.HomeController.HomeController;
import com.example.varzeando.Models.Quadra;
import com.example.varzeando.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TelaMapaUsuario extends FragmentActivity implements OnMapReadyCallback {

    //Definindo os atributos da widget
    private SearchView sv_buscarQuadrasMapa;
    private RecyclerView rv_quadrasProximasMapa;
    private RecyclerViewAdapterQuadrasProximas recyclerViewAdapterQuadrasProximasMapa;
    private RecyclerViewAdapterQuadras.RecyclerViewClickListener listenerMapa;
    private Double latitudeMapa;
    private Double longitudeMapa;
    private HomeController homeController;
    private Bundle extras;
    private FloatingActionButton fab_cadastrarQuadra;

    //Cria????o de um mapa do Google Maps
    private GoogleMap mMap;

    //Cria????o de um array de string com todas as permiss??es
    private String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    //Criar um locationManager e um locationListener que ir?? controlar sempre que o usu??rio mudar a localiza????o
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ArrayList<Quadra> listaQuadrasProximas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_mapa);
        homeController = new HomeController(getApplication());
        fab_cadastrarQuadra = findViewById(R.id.fab_cadastrarQuadra);

        extras = getIntent().getExtras();
        if(extras != null){
            latitudeMapa = extras.getDouble("latitudeMapa");
            longitudeMapa = extras.getDouble("longitudeMapa");
        }

        fab_cadastrarQuadra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaMapaUsuario.this, CadastroQuadra.class);
                startActivity(intent);
            }
        });

        //Configura????o do SearchView do mapa
        sv_buscarQuadrasMapa = findViewById(R.id.sv_buscarQuadrasMapa);
        sv_buscarQuadrasMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_buscarQuadrasMapa.setIconified(false);
            }
        });

        //Configurar recycler view
        rv_quadrasProximasMapa = findViewById(R.id.rv_quadrasProximasMapa);

        //Validar permiss??es do usu??rio no aplicativo
        Permissoes.validarPermissoes(permissoes, TelaMapaUsuario.this, 1);

        //Obtem um SupportMapFragment que ?? notificado quando o mapa est?? pronto para uso
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initRecyclerViewQuadrasProximasMapa(Double latitude, Double longitude) {
        setOnClickListenerQuadrasProximas();
        LinearLayoutManager layoutManager = new LinearLayoutManager(TelaMapaUsuario.this, LinearLayoutManager.HORIZONTAL, false);
        rv_quadrasProximasMapa.setLayoutManager(layoutManager);
        recyclerViewAdapterQuadrasProximasMapa = new RecyclerViewAdapterQuadrasProximas(getApplication(), listenerMapa);
        rv_quadrasProximasMapa.setAdapter(recyclerViewAdapterQuadrasProximasMapa);
        homeController.loadQuadrasProximas(recyclerViewAdapterQuadrasProximasMapa, latitude, longitude);
    }

    public void setListaQuadrasProximasMapa(ArrayList<Quadra> quadras){
        this.listaQuadrasProximas = quadras;
    }

    private void setOnClickListenerQuadrasProximas(){
        listenerMapa = new RecyclerViewAdapterQuadras.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplication(), DetalhesPartida.class);
                intent.putExtra("nome", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getNomeQuadra());
                intent.putExtra("id", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getId());
                intent.putExtra("descricao", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getDescricaoQuadra());
                intent.putExtra("endereco", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getEnderecoQuadra());
                intent.putExtra("imagem", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getImagemQuadra());
                intent.putExtra("media", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getAvaliacao());
                intent.putExtra("quantidade", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getQuantidade());
                intent.putExtra("latitude", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getLatitude());
                intent.putExtra("longitude", recyclerViewAdapterQuadrasProximasMapa.listaQuadrasProximas.get(position).getLongitude());
                startActivity(intent);
            }
        };
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Criar um objeto respons??vel por gerenciar a localiza????o do usu??rio
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                //Recuperar a latitude e longitude do usuario
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();


                //Limpar os pinos do mapa antes da localiza????o mudar
                mMap.clear();

                //Definir a localiza????o do usu??rio atrav??s da latitude e longitude passada
                LatLng localUsuario = new LatLng(latitude, longitude);
                initRecyclerViewQuadrasProximasMapa(latitude, longitude);

                //Adicionar um marcador na posi????o do usu??rio e definir onde ficar?? a camera e o zoom dela
                mMap.addMarker(new MarkerOptions().position(localUsuario).title("Minha localiza????o"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 15));

                BitmapDescriptor iconVarzeando = BitmapDescriptorFactory.fromResource(R.drawable.logo_varzeando);

//                for(int i = 0; i < listaQuadrasProximas.size(); i++){
//                    mMap.addMarker(new MarkerOptions().position(new LatLng(listaQuadrasProximas.get(i).getLatitude(), listaQuadrasProximas.get(i).getLongitude())).title(listaQuadrasProximas.get(i).getNomeQuadra()).icon(iconVarzeando));
//                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        //Chechar se a permiss??o foi concedida e controlar o request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000,
                    250,
                    locationListener
            );
            return;
        }
    }

    //Controlar as permiss??es do usu??rio
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Criar um for each para cada uma das permiss??es dentro da permiss??o resultado e avalia se ela foi negada ou concedida
        for (int permissaoResultado : grantResults) {
            if (permissaoResultado == PackageManager.PERMISSION_DENIED) {
                //Chama o m??dodo alertaValidacaoPermissao que cria um dialog para avisar o usu??rio que ele precisa aceitar
                alertaValidacaoPermissao();
            } else if (permissaoResultado == PackageManager.PERMISSION_GRANTED) {
                //Recuperar localiza????o do usu??rio atrav??s do locationManager
                //?? necess??rio passar 4 par??metros
                /*
                - 1) Provedor da localiza????o
                - 2)Tempo m??nimo entre atualiza????es de localiza????o (em milissegundos)
                - 3) Dist??ncia m??nima entre as atualiza????es de localiza????o (metros)
                - 4) Location listener (para recebermos as atualiza????es rotineiramente)
                 */

                //Chechar se a permiss??o foi concedida e controlar o request location updates
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            10000,
                            250,
                            locationListener
                    );
                    return;
                }
            }
        }
    }
    //M??todo para criar um AlertDialog quando o usu??rio recusa as permiss??es
    private void alertaValidacaoPermissao(){
        //Definir os atributos do builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TelaMapaUsuario.this);
        builder.setTitle(R.string.permissoes_negadas);
        builder.setMessage(R.string.aceitar_prtmissoes);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        //Exibir o builder
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}