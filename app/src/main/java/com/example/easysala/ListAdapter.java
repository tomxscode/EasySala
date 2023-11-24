package com.example.easysala;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysala.models.Horario;
import com.example.easysala.models.Implementos;
import com.example.easysala.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Horario> mData;
    private LayoutInflater mInflater;
    private Context context;
    private Fragment fragment;

    public ListAdapter(List<Horario> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList != null ? itemList : new ArrayList<>();
    }
    public interface OnItemClickListener {
        void onItemClick(Horario item);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Horario> items){mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView bloque, dia, sala, disponibilidad;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            bloque = itemView.findViewById(R.id.tipoTextView);
            dia = itemView.findViewById(R.id.edificioTextView);
            sala = itemView.findViewById(R.id.cantidadTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Mostrar el diálogo cuando se hace clic en un elemento del RecyclerView
                    showDialog("Información"); // Puedes pasar cualquier información que desees mostrar en el diálogo
                }
            });
        }

        void bindData(final Horario item) {

            bloque.setText(item.getBloqueHorario().getHoraInicio());
            dia.setText(item.getDiaHorario().getNombre());
            sala.setText(item.getSalaHorario().getNombreSala());
        }

        private void showDialog(String data) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            builder.setTitle(data)
                    .setMessage(" "+ bloque)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acciones al hacer clic en Aceptar
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acciones al hacer clic en Cancelar
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

}

