package com.example.app_agibank;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OpcoesAdapter extends RecyclerView.Adapter<OpcoesAdapter.ViewHolder> {

    private Context context;
    private List<OpcaoItem> opcoes;
    private List<OpcaoItem> opcoesFiltradas;

    public OpcoesAdapter(Context context, List<OpcaoItem> opcoes) {
        this.context = context;
        this.opcoes = opcoes;
        this.opcoesFiltradas = new ArrayList<>(opcoes);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.modelo_botao_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OpcaoItem item = opcoesFiltradas.get(position);

        holder.btnOpcao.setImageResource(item.getIcone());
        holder.tvOpcao.setText(item.getNome());

        holder.cardOpcao.setOnClickListener(v -> {
            if ("Editar Opções".equals(item.getNome())) {
                Intent intent = new Intent(context, ConfiguracaoOpcoesActivity.class);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Clicou em " + item.getNome(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return opcoesFiltradas.size();
    }

    // 🔎 Filtro para busca
    public void filtrar(String texto) {
        opcoesFiltradas.clear();
        if (texto.isEmpty()) {
            opcoesFiltradas.addAll(opcoes);
        } else {
            for (OpcaoItem op : opcoes) {
                if (op.getNome().toLowerCase().contains(texto.toLowerCase())) {
                    opcoesFiltradas.add(op);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnOpcao;
        TextView tvOpcao;
        View cardOpcao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnOpcao = itemView.findViewById(R.id.Modelo_Botao_Grid_Background);
            tvOpcao = itemView.findViewById(R.id.Modelo_Botao_Grid_Titulo_Inferior);
            cardOpcao = itemView.findViewById(R.id.card_opcao);
        }
    }
}
