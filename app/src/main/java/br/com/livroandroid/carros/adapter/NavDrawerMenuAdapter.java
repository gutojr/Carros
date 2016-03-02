package br.com.livroandroid.carros.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.WearableListView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import br.com.livroandroid.carros.R;

/**
 * Created by Gustavo on 27/02/2016.
 */
public class NavDrawerMenuAdapter extends BaseAdapter {
    protected static final String TAG = "livroandroid";
    private final List<NavDrawerMenuItem> list;
    private final Context context;
    private LayoutInflater inflater;

    public NavDrawerMenuAdapter(Context context, List<NavDrawerMenuItem> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // Cria o ViewHolder
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_nav_drawer, parent, false);
            convertView.setTag(holder);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
        } else {
            // Reaproveita o ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }
        // Atualiza a view
        NavDrawerMenuItem item = list.get(position);
        holder.text.setText(item.title);
        holder.img.setImageResource(item.img);
        if (item.selected) {
            // Configura o fundo cinza do item selecionado
            convertView.setBackgroundResource(R.drawable.seletor_nav_drawer_selected);
            holder.text.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
        } else {
            convertView.setBackgroundResource(R.drawable.seletor_nav_drawer);
            holder.text.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        return convertView;
    }

    public void setSelected(int position, boolean selected) {
        clearSelected();
        list.get(position).selected = selected;
        notifyDataSetChanged();
    }

    public void clearSelected() {
        if (list != null) {
            for (NavDrawerMenuItem item : list) {
                item.selected = false;
            }
            notifyDataSetChanged();
        }
    }

    // Design Patter "ViewHolder"
    static class ViewHolder {
        TextView text;
        ImageView img;
    }
}
