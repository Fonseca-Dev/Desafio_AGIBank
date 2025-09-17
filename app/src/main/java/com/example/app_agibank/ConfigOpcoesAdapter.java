package com.example.app_agibank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ConfigOpcoesAdapter extends RecyclerView.Adapter<ConfigOpcoesAdapter.ViewHolder> {

    private final List<OpcaoItem> opcoes;
    private final Context context;

    public ConfigOpcoesAdapter(Context context, List<OpcaoItem> opcoes) {
        this.context = context;
        this.opcoes = opcoes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_config_opcao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OpcaoItem opcao = opcoes.get(position);
        holder.tvNome.setText(opcao.getNome());
        holder.imgOpcao.setImageResource(opcao.getIcone());
        holder.cbAtivo.setChecked(opcao.isAtivo());

        holder.cbAtivo.setOnCheckedChangeListener((buttonView, isChecked) -> opcao.setAtivo(isChecked));
    }

    @Override
    public int getItemCount() {
        return opcoes.size();
    }


    public List<OpcaoItem> getOpcoes() {
        return opcoes;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOpcao;
        TextView tvNome;
        CheckBox cbAtivo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOpcao = itemView.findViewById(R.id.img_opcao);
            tvNome = itemView.findViewById(R.id.tv_nome_opcao);
            cbAtivo = itemView.findViewById(R.id.cb_ativo);
        }
    }
}

