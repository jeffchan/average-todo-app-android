package net.jeffchan.averagetodoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_ITEM_REQUEST_CODE = 0;

    private ArrayList<String> mItems;
    private ArrayAdapter<String> mItemsAdapter;
    private ListView mListViewItems;

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

        if (!itemText.isEmpty()) {
            mItemsAdapter.add(itemText); // Why go through the adapter, and not directly modify the ArrayList?
            editTextNewItem.setText("");
            writeItems();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ITEM_REQUEST_CODE && resultCode == RESULT_OK) {
            int itemPosition = data.getIntExtra("itemPosition", 0);
            String newItemText = data.getStringExtra("newItemText");
            saveEdit(itemPosition, newItemText);
            Toast.makeText(this, "Edit saved", Toast.LENGTH_SHORT).show();
        }
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
        mListViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", getItem(position));
                intent.putExtra("itemPosition", position);
                startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
            }
        });
    }

    private String getItem(int position) {
        return mItems.get(position);
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

    private void saveEdit(int itemPosition, String newItemText) {
        mItems.set(itemPosition, newItemText);
        mItemsAdapter.notifyDataSetChanged();
        writeItems();
    }
}
