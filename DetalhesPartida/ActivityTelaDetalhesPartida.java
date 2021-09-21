package com.example.varzeando.DetalhesPartida;
import com.example.varzeando.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class ActivityTelaDetalhesPartida extends AppCompatActivity {

    CardView confirmados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_detalhes_partida);

        confirmados = findViewById(R.id.cardView2);

        final FragmentManager fm = getSupportFragmentManager();
        final TelaConfirmados tc = new TelaConfirmados();

        confirmados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tc.show(fm, "Confirmados");
            }
        });
    }
}


