package com.example.varzeando.MenuInferior.MenuInferiorView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.varzeando.Home.HomeView.TelaHomeFragment;
import com.example.varzeando.Mapa.MapaView.TelaMapaUsuario;
import com.example.varzeando.Notificacoes.NotificacoesView.TelaNotificacoesFragment;
import com.example.varzeando.Perfil.PerfilView.TelaPerfilFragment;
import com.example.varzeando.R;
import com.example.varzeando.CriarPartida.CriarPartidaView.TelaCriarPartidaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TelaMenuInferior extends AppCompatActivity {

    FloatingActionButton fabMapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_menu_inferior);

        fabMapa = findViewById(R.id.fabMapa);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.coordinatorLayout,
                    new TelaHomeFragment(TelaMenuInferior.this.getApplication(), getApplicationContext())).commit();
        }
        fabMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaMenuInferior.this, TelaMapaUsuario.class);
                startActivity(intent);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.menuHome:
                            selectedFragment = new TelaHomeFragment(TelaMenuInferior.this.getApplication(), getApplicationContext());
                            break;
                        case R.id.menuCriarPartidas:
                            selectedFragment = new TelaCriarPartidaFragment();
                            break;
                        case R.id.menuNotificacoes:
                            selectedFragment = new TelaNotificacoesFragment();
                            break;
                        case R.id.menuPerfil:
                            selectedFragment = new TelaPerfilFragment(TelaMenuInferior.this.getApplication(), getApplicationContext());
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.coordinatorLayout,
                            selectedFragment).commit();
                    return true;
                }
            };
}