package net.jeffchan.averagetodoapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import net.jeffchan.averagetodoapp.adapters.TodoItemAdapter;
import net.jeffchan.averagetodoapp.fragments.EditItemFragment;
import net.jeffchan.averagetodoapp.R;
import net.jeffchan.averagetodoapp.models.TodoItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements EditItemFragment.EditItemFragmentListener {

    private List<TodoItem> mTodoItems;
    private ArrayAdapter<TodoItem> mTodoItemsAdapter;
    private ListView mListViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoItems = TodoItem.getAll();

        mListViewItems = (ListView) findViewById(R.id.listViewItems);
        mTodoItemsAdapter = new TodoItemAdapter(this, mTodoItems);
        mListViewItems.setAdapter(mTodoItemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View view) {
        EditText editTextNewItem = (EditText) findViewById(R.id.editTextNewItem);
        String itemText = editTextNewItem.getText().toString();

        if (!itemText.isEmpty()) {
            TodoItem todoItem = new TodoItem(itemText);
            todoItem.save();
            mTodoItemsAdapter.add(todoItem); // Why go through the adapter, and not directly modify the ArrayList?
            editTextNewItem.setText("");
        }
    }

    private void setupListViewListener() {
        mListViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TodoItem todoItem = getItem(position);

                if (todoItem != null) {
                    Log.d("debug", todoItem.getId().toString());
                    todoItem.delete();
                    mTodoItems.remove(position);
                }

                mTodoItemsAdapter.notifyDataSetChanged();
                return true; // To denote that we consumed the long click event
            }
        });
        mListViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                TodoItem todoItem = getItem(position);
                EditItemFragment editItemFragment = EditItemFragment.newInstance(todoItem.getTitle(), position);
                editItemFragment.show(fragmentManager, "fragment_edit_item");
            }
        });
    }

    @Override
    public void onFinishEditDialog(String newItemText, int itemPosition) {
        saveEdit(itemPosition, newItemText);
        Toast.makeText(this, "Edit saved", Toast.LENGTH_SHORT).show();
    }

    private TodoItem getItem(int position) {
        return mTodoItems.get(position);
    }

    private void saveEdit(int itemPosition, String newTitle) {
        TodoItem todoItem = getItem(itemPosition);
        todoItem.setTitle(newTitle);
        mTodoItemsAdapter.notifyDataSetChanged();
    }
}
