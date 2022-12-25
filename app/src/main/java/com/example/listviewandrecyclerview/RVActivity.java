package com.example.listviewandrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.listviewandrecyclerview.adapter.Adapter;
import com.example.listviewandrecyclerview.models.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RVActivity extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<Person> arrayList;
    Adapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvactivity);

        setTitle("Contacts");
        init();

        prepareData();
        adapter= new Adapter(arrayList,this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(RVActivity.this,
                LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RVActivity.this, "Add new Contact", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init(){
        rv=findViewById(R.id.rv);
        fab = findViewById(R.id.fab);
    }

    public void prepareData(){
        arrayList = new ArrayList<>();

        Person person1=new Person("Ahmed","059059059",R.drawable.a);
        Person person2=new Person("Mohammed","05920209",R.drawable.b);
        Person person3=new Person("Mahmoud","059252051",R.drawable.c);
        Person person4=new Person("Eesa","059963852",R.drawable.d);
        Person person5=new Person("Anas","0597410963",R.drawable.e);
        Person person6=new Person("Yousef","059463741",R.drawable.j);
        Person person7=new Person("Ibrahim","059456321",R.drawable.g);
        Person person8=new Person("Mousa","059456555",R.drawable.h);
        Person person9=new Person("Hazem","059111222",R.drawable.i);

        arrayList.add(person1);
        arrayList.add(person2);
        arrayList.add(person3);
        arrayList.add(person4);
        arrayList.add(person5);
        arrayList.add(person6);
        arrayList.add(person7);
        arrayList.add(person8);
        arrayList.add(person9);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.close){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}