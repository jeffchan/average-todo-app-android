package net.jeffchan.averagetodoapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.jeffchan.averagetodoapp.R;
import net.jeffchan.averagetodoapp.adapters.TodoItemAdapter;
import net.jeffchan.averagetodoapp.fragments.EditItemFragment;
import net.jeffchan.averagetodoapp.models.TodoItem;
import net.jeffchan.averagetodoapp.utils.ItemClickSupport;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity implements EditItemFragment.EditItemFragmentListener {

    private List<TodoItem> mTodoItems;
    private RecyclerView.Adapter mTodoItemsAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoItems = TodoItem.getAll();

        mRecyclerView = (RecyclerView) findViewById(R.id.listViewItems);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mTodoItemsAdapter = new TodoItemAdapter(this, mTodoItems);
        mRecyclerView.setAdapter(mTodoItemsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setupListViewListener();
    }

    public void onAddItem(View view) {
        EditText editTextNewItem = (EditText) findViewById(R.id.editTextNewItem);
        String itemText = editTextNewItem.getText().toString();

        if (!itemText.isEmpty()) {
            TodoItem todoItem = new TodoItem(itemText);
            todoItem.save();
            mTodoItems.add(todoItem);
            int newPosition = mTodoItems.size() - 1;
            mTodoItemsAdapter.notifyItemInserted(newPosition);
            mRecyclerView.scrollToPosition(mTodoItemsAdapter.getItemCount() - 1);
            editTextNewItem.setText("");
        }
    }

    private void setupListViewListener() {

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                TodoItem todoItem = getItem(position);
                EditItemFragment editItemFragment = EditItemFragment.newInstance(todoItem.getTitle(), position);
                editItemFragment.show(fragmentManager, "fragment_edit_item");
            }
        });

        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                TodoItem todoItem = getItem(position);

                todoItem.delete();
                mTodoItems.remove(position);

                mTodoItemsAdapter.notifyItemRemoved(position);
                return true; // To denote that we consumed the long click event
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                TodoItem todoItem = getItem(position);

                todoItem.delete();
                mTodoItems.remove(position);

                mTodoItemsAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
        mTodoItemsAdapter.notifyItemChanged(itemPosition);
    }
}
