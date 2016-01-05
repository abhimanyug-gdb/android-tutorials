package com.gdb.listtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<ListContent> mainList = new ArrayList<>();
    private ArrayList<ListContent> filteredList = new ArrayList<>();
    private ListView listViewMainList;
    private ContentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listViewMainList = (ListView) findViewById(R.id.list_view_main_list);
        filteredList = new ArrayList<>(mainList);
        adapter = new ContentAdapter(MainActivity.this, filteredList);
        listViewMainList.setAdapter(adapter);

        final EditText editTextAddItem = (EditText) findViewById(R.id.edit_text_add_item);
        final EditText editTextFilterList = (EditText) findViewById(R.id.edit_text_filter_list);

        editTextFilterList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s);
                if (filteredList.size() > 0)
                {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Button buttonAdd = (Button) findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextAddItem.getText().toString();
                editTextAddItem.setText("");
                ListContent listContent = new ListContent();
                listContent.setTitle(title);
                mainList.add(listContent);
                filteredList.clear();
                filteredList.addAll(mainList);
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
                if (mPosition == -1) {
                    Toast.makeText(getApplicationContext(), "Given Item does not exists!!!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mainList.remove(mPosition);
                    filteredList.clear();
                    filteredList.addAll(mainList);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        listViewMainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete entry!!!")
                        .setMessage("Are you sure you want to delelte this entry?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int key = mainList.indexOf(filteredList.get(position));
                                mainList.remove(key);
                                filteredList.clear();
                                filteredList.addAll(mainList);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();

                return true;
            }
        });
    }

    private void filter(CharSequence constraint) {
        filteredList.clear();
        for (int i=0; i < mainList.size(); i++) {
            String name = mainList.get(i).getTitle();
            if (name.toLowerCase().contains(constraint.toString())) {
                filteredList.add(mainList.get(i));
            }
        }
        for (int i=0; i < filteredList.size(); i++)
            Log.d("Filter Result : ",filteredList.get(i).getTitle().toString());
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