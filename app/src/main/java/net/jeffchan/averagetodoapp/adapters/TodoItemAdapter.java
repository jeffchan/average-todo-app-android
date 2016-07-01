package net.jeffchan.averagetodoapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.jeffchan.averagetodoapp.R;
import net.jeffchan.averagetodoapp.models.TodoItem;

import java.util.List;

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder> {

    private Context mContext;
    private List<TodoItem> mTodoItems;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView itemTitleTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            itemTitleTextView = (TextView) itemView.findViewById(R.id.itemTitle);
        }
    }

    public TodoItemAdapter(Context context, List<TodoItem> todoItems) {
        mTodoItems = todoItems;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TodoItem todoItem = mTodoItems.get(position);
        viewHolder.itemTitleTextView.setText(todoItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return mTodoItems.size();
    }
}
