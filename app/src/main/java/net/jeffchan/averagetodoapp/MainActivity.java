package net.jeffchan.averagetodoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> mItems;
    ArrayAdapter<String> mItemsAdapter;
    ListView mListViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewItems = (ListView) findViewById(R.id.listViewItems);

        readItems();

        mItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
        mListViewItems.setAdapter(mItemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View view) {
        EditText editTextNewItem = (EditText) findViewById(R.id.editTextNewItem);
        String itemText = editTextNewItem.getText().toString();
        mItemsAdapter.add(itemText); // Why go through the adapter, and not directly modify the ArrayList?
        editTextNewItem.setText("");

        writeItems();
    }

    private void setupListViewListener() {
        mListViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mItems.remove(position);
                mItemsAdapter.notifyDataSetChanged();
                writeItems();
                return true; // To denote that we consumed the long click event
            }
        });
    }

    private void readItems() {
        File todoFile = getFile();
        try {
            mItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            mItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File todoFile = getFile();
        try {
            FileUtils.writeLines(todoFile, mItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {
        File filesDir = getFilesDir();
        return new File(filesDir, "todo.txt");
    }
}
