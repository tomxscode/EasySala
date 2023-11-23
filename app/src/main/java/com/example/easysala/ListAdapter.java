package com.example.easysala;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysala.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    private Fragment fragment;

    public ListAdapter(List<ListElement> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList != null ? itemList : new ArrayList<>();
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

    public void setItems(List<ListElement> items){mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView tipo, edificio, estado;


        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            tipo = itemView.findViewById(R.id.tipoTextView);
            edificio = itemView.findViewById(R.id.edificioTextView);
            estado = itemView.findViewById(R.id.cantidadTextView);
        }

        void bindData(final ListElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            tipo.setText(item.getTipo());
            edificio.setText(item.getEdificio());
            estado.setText(item.getEstado());
        }
    }
}

