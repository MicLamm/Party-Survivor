package uqac.dim.partysurvivor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomGridAdapterMenuAlcool extends BaseAdapter {

    private List<Alcool> listData;
    private List<AlcoolMenu> listDataMenu;
    private LayoutInflater layoutInflater;
    private Context context;
    private String type;

    public CustomGridAdapterMenuAlcool(Context aContext,  List<Alcool> listData, List<AlcoolMenu> listDataMenu, String type) {
        this.context = aContext;
        this.listData = listData;
        this.listDataMenu = listDataMenu;
        this.type = type;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        if(type.equals("menu")){
            return listDataMenu.size();
        }
        else{
            return listData.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(type.equals("menu")){
            return listDataMenu.get(position);
        }
        else{
            return listData.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        //Database database = new Database(new ArrayList<Alcool>());
        ViewHolder holder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_item_layout_alcool, null);
            holder = new ViewHolder();
            holder.alcoolView = (ImageView) convertView.findViewById(R.id.imageView_alcool);
            holder.alcoolNameView = (TextView) convertView.findViewById(R.id.textView_alcoolName);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        if(type.equals("menu")){
            AlcoolMenu alcoolmenu = this.listDataMenu.get(position);
            holder.alcoolNameView.setText(alcoolmenu.getCategoryName());

            Glide.with(context).load(alcoolmenu.getImageUrl()).into(holder.alcoolView);

        }
        else {
            Alcool alcool = this.listData.get(position);
            holder.alcoolNameView.setText(alcool.getNom());

            Glide.with(context).load(alcool.getImageUrl()).into(holder.alcoolView);
        }



        return convertView;
    }

    //Ancien moyen d'appeler les images en local
    /*private int getMipmapResIdByName(String resName) {

        String pkgName = context.getPackageName();

        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        Log.i("CustomGridView", "Res Name: "+ resName + "==> Res ID = "+ resID);
        return resID;
    }*/


    static class ViewHolder {
        public ImageView imageView;
        ImageView alcoolView;
        TextView alcoolNameView;

        static {

        }





    }
}
