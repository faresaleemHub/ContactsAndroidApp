package com.example.listviewandrecyclerview.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listviewandrecyclerview.R;
import com.example.listviewandrecyclerview.models.Person;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Person> arrayList;
    Activity activity;
    PopupMenu popupMenu;
    Snackbar snackbar;

    public Adapter(ArrayList<Person> arrayList, Activity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_view, null, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person1 = arrayList.get(position);
        holder.person_name_tv.setText(person1.getName());
        holder.person_phone_tv.setText(person1.getPhone());
        holder.img.setImageResource(person1.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu = new PopupMenu(activity, holder.itemView);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onLongClick(View view) {
                popupMenu = new PopupMenu(activity, view);
                activity.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                displayMenuIcons();
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id==R.id.call){
                            Intent i=new Intent(Intent.ACTION_DIAL);
                            i.setData(Uri.parse("tel:"+person1.getPhone()));
                            activity.startActivity(i);
                        }else if (menuItem.getItemId() == R.id.delete) {
                            arrayList.remove(position);
                            notifyDataSetChanged();
                        }
                        return true;
                    }
                });
                return true;// means: the action finished
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null)
            return arrayList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView person_name_tv, person_phone_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.person_img);
            person_name_tv = itemView.findViewById(R.id.person_name_tv);
            person_phone_tv = itemView.findViewById(R.id.person_phone_tv);

        }
    }

    public void displayMenuIcons() {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(
                            menuPopupHelper
                                    .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        }catch (Exception e){}
    }
}
