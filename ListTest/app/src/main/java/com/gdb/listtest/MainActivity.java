package com.gdb.listtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<ListContent> mainList = new ArrayList<ListContent>();
    ListView listViewMainList;
    private ContentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listViewMainList = (ListView) findViewById(R.id.list_view_main_list);
        ListContent listContent = new ListContent();
        listContent.setTitle("Default");
        mainList.add(listContent);
        adapter = new ContentAdapter(MainActivity.this, mainList);
        listViewMainList.setAdapter(adapter);

        final EditText editTextAddItem = (EditText) findViewById(R.id.edit_text_add_item);

        Button buttonAdd = (Button) findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextAddItem.getText().toString();
                editTextAddItem.setText("");
                ListContent listContent = new ListContent();
                listContent.setTitle(title);
                mainList.add(listContent);
                adapter.notifyDataSetChanged();

            }
        });

        Button buttonFindNRemove = (Button) findViewById(R.id.button_find_n_remove);

        buttonFindNRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = editTextAddItem.getText().toString();
                editTextAddItem.setText("");
                ListContent listContent = new ListContent();
                listContent.setTitle(key);
                int mPosition = mainList.indexOf(listContent);
                if (mPosition == -1){
                    Toast.makeText(getApplicationContext(), "Given Item does not exists!!!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mainList.remove(mPosition);
                    adapter.notifyDataSetChanged();
                }
            }
        });
/*
        editTextAddItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
