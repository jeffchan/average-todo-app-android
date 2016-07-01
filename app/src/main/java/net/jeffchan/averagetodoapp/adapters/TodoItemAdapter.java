package net.jeffchan.averagetodoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.jeffchan.averagetodoapp.R;
import net.jeffchan.averagetodoapp.models.TodoItem;

import java.util.List;

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {

    public TodoItemAdapter(Context context, List<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem todoItem = getItem(position);

        // View recycling
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }

        TextView itemTitleTextView = (TextView) convertView.findViewById(R.id.itemTitle);
        itemTitleTextView.setText(todoItem.getTitle());

        return convertView;
    }
}
