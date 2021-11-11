package com.example.recherchealcool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomGridAdapteur2 extends BaseAdapter {

    private List<Alcool> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomGridAdapteur2(Context aContext,  List<Alcool> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_item_layout, null);
            holder = new ViewHolder();
            holder.alcoolView = (ImageView) convertView.findViewById(R.id.imageView_alcool);
            holder.alcoolNameView = (TextView) convertView.findViewById(R.id.textView_alcoolName);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        Alcool alcool = this.listData.get(position);
        holder.alcoolNameView.setText(alcool.getNom());

        int imageId = this.getMipmapResIdByName(alcool.getImageName());

        holder.alcoolView.setImageResource(imageId);

        return convertView;
    }

    private int getMipmapResIdByName(String resName) {

        String pkgName = context.getPackageName();

        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        Log.i("CustomGridView", "Res Name: "+ resName + "==> Res ID = "+ resID);
        return resID;
    }


    static class ViewHolder {
        ImageView alcoolView;
        TextView alcoolNameView;

    }
}
