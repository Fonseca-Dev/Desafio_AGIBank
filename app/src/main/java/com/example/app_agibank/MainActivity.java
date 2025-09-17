package com.example.app_agibank;

import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<OpcaoItem> todasOpcoes;
    private OpcoesAdapter adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lista completa de opções
        todasOpcoes = carregarTodasOpcoes(this);

        // Configura RecyclerView
        recyclerView = findViewById(R.id.Main_Activity_Grid_Opcoes);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // 3 colunas

        List<OpcaoItem> opcoesEscolhidas = carregarOpcoesEscolhidas(this);
        adapter = new OpcoesAdapter(this, opcoesEscolhidas);
        recyclerView.setAdapter(adapter);

        // Configura SearchView
        searchView = findViewById(R.id.Main_Activity_Search_View);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // 🔙 Se apagou o texto -> volta para o menu principal (opções escolhidas)
                    List<OpcaoItem> opcoesEscolhidas = carregarOpcoesEscolhidas(MainActivity.this);
                    adapter = new OpcoesAdapter(MainActivity.this, opcoesEscolhidas);
                    recyclerView.setAdapter(adapter);
                } else {
                    // 🔍 Se digitou algo -> filtra em todas as opções
                    List<OpcaoItem> filtradas = new ArrayList<>();
                    for (OpcaoItem op : todasOpcoes) {
                        if (op.getNome().toLowerCase().contains(newText.toLowerCase())) {
                            filtradas.add(op);
                        }
                    }
                    adapter = new OpcoesAdapter(MainActivity.this, filtradas);
                    recyclerView.setAdapter(adapter);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Atualiza RecyclerView quando voltar da configuração
        List<OpcaoItem> opcoesEscolhidas = carregarOpcoesEscolhidas(this);
        adapter = new OpcoesAdapter(this, opcoesEscolhidas);
        recyclerView.setAdapter(adapter);
    }

    // ===============================
    // Funções de salvar/carregar opções
    // ===============================
    public static void salvarOpcoesEscolhidas(Context context, List<OpcaoItem> opcoes) {
        StringBuilder sb = new StringBuilder();
        for (OpcaoItem op : opcoes) {
            if (op.isAtivo()) {
                sb.append(op.getNome()).append(",");
            }
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        context.getSharedPreferences("opcoes_usuario", Context.MODE_PRIVATE)
                .edit()
                .putString("opcoes_selecionadas", sb.toString())
                .apply();
    }

    public static List<OpcaoItem> carregarTodasOpcoes(Context context) {
        List<OpcaoItem> todasOpcoes = new ArrayList<>();
        todasOpcoes.add(new OpcaoItem("Crédito do Trabalhador", R.drawable.ic_credito_trabalhador));
        todasOpcoes.add(new OpcaoItem("Portabilidade Crédito do Trabalhador", R.drawable.ic_portabilidade_credito));
        todasOpcoes.add(new OpcaoItem("Empréstimo FGTS", R.drawable.ic_emprestimo_fgts));
        todasOpcoes.add(new OpcaoItem("Consignado INSS", R.drawable.ic_consignado_inss));
        todasOpcoes.add(new OpcaoItem("Portabilidade Consignado INSS", R.drawable.ic_portabilidade_consignado_inss));
        todasOpcoes.add(new OpcaoItem("Antecipação 13º INSS", R.drawable.ic_antecipacao_13));
        todasOpcoes.add(new OpcaoItem("Empréstimo Pessoal INSS", R.drawable.ic_emprestimo_pessoal_inss));
        todasOpcoes.add(new OpcaoItem("Trazer salário INSS", R.drawable.ic_trazer_salario_inss));
        todasOpcoes.add(new OpcaoItem("Minhas propostas", R.drawable.ic_minhas_propostas));
        todasOpcoes.add(new OpcaoItem("Pagar com QR Code", R.drawable.ic_pagar_qr_code));
        todasOpcoes.add(new OpcaoItem("Agendamentos", R.drawable.ic_agendamentos));
        todasOpcoes.add(new OpcaoItem("Agenda de Boletos (DDA)", R.drawable.ic_agenda_boletos_dda));
        todasOpcoes.add(new OpcaoItem("Recarga Celular", R.drawable.ic_recarga_celular));
        todasOpcoes.add(new OpcaoItem("Gift Card", R.drawable.ic_gift_card));

        todasOpcoes.add(new OpcaoItem("Pix Copia e Cola", R.drawable.ic_pix_copia_cola));
        todasOpcoes.add(new OpcaoItem("Pagar conta", R.drawable.ic_pagar_conta));
        todasOpcoes.add(new OpcaoItem("Cobrar com Pix", R.drawable.ic_cobra_com_pix));
        todasOpcoes.add(new OpcaoItem("Seguros", R.drawable.ic_seguros));
        todasOpcoes.add(new OpcaoItem("Editar Opções", R.drawable.ic_editar)); // 🔒 sempre presente
        return todasOpcoes;
    }

    public static List<OpcaoItem> carregarOpcoesEscolhidas(Context context) {
        List<OpcaoItem> todas = carregarTodasOpcoes(context);
        String opcoesSalvas = context.getSharedPreferences("opcoes_usuario", Context.MODE_PRIVATE)
                .getString("opcoes_selecionadas", "");

        List<OpcaoItem> opcoesEscolhidas = new ArrayList<>();

        if (!opcoesSalvas.isEmpty()) {
            // Carrega opções salvas
            String[] nomes = opcoesSalvas.split(",");
            for (String nome : nomes) {
                for (OpcaoItem op : todas) {
                    if (op.getNome().equals(nome)) {
                        op.setAtivo(true);
                        opcoesEscolhidas.add(op);
                        break;
                    }
                }
            }
        } else {
            // Primeira inicialização: pega as 3 primeiras opções automaticamente
            for (int i = 0; i < 14 && i < todas.size(); i++) {
                OpcaoItem op = todas.get(i);
                op.setAtivo(true);
                opcoesEscolhidas.add(op);
            }
        }

        // 🔒 Garante que o botão "Editar" sempre vai estar no fim
        for (OpcaoItem op : todas) {
            if (op.getNome().equals("Editar Opções")) {
                opcoesEscolhidas.add(op);
                break;
            }
        }

        return opcoesEscolhidas;
    }



    public static List<OpcaoItem> carregarTodasOpcoesSemEditar(Context context) {
        List<OpcaoItem> todas = carregarTodasOpcoes(context);
        List<OpcaoItem> semEditar = new ArrayList<>();

        for (OpcaoItem op : todas) {
            if (!"Editar Opções".equals(op.getNome())) {
                semEditar.add(op);
            }
        }

        return semEditar;
    }
}
