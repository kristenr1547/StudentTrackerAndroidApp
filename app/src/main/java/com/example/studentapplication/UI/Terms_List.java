package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.Term;
import com.example.studentapplication.R;

import java.util.List;

public class Terms_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Term> terms = repo.getAllTerms();
        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);
        }

        //inflate the menu and adds items to action bar if it is present
        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu_termslist, menu);
            return true;
        }
        public boolean onOptionsItemSelected(MenuItem item){
            switch(item.getItemId()){
                case android.R.id.home:
                    this.finish();
                    return true;
            }
                    return super.onOptionsItemSelected(item);

        }


    public void addTerm(View view) {
        //go to add term page
        Intent i = new Intent(this, AddTerm.class);
        startActivity(i);

    }
}