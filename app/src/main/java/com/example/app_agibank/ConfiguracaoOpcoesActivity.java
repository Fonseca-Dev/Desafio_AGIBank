package com.example.app_agibank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConfiguracaoOpcoesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConfigOpcoesAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracao_opcoes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.opcoes), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_opcoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<OpcaoItem> todasOpcoes = MainActivity.carregarTodasOpcoesSemEditar(this);
        List<OpcaoItem> opcoesEscolhidas = MainActivity.carregarOpcoesEscolhidas(this);

        // Marca quais estão ativos
        for (OpcaoItem op : todasOpcoes) {
            op.setAtivo(opcoesEscolhidas.contains(op));
        }

        adapter = new ConfigOpcoesAdapter(this, todasOpcoes);
        recyclerView.setAdapter(adapter);


        Button btnSalvar = findViewById(R.id.btn_salvar_opcoes);
        btnSalvar.setOnClickListener(v -> {
            MainActivity.salvarOpcoesEscolhidas(this, adapter.getOpcoes());
            finish(); // volta para MainActivity
        });

        ImageButton btnVoltar = findViewById(R.id.Activity_Config_Opcoes_Botao_Voltar);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(ConfiguracaoOpcoesActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // fecha a tela atual pra não acumular no stack
        });

    }
}