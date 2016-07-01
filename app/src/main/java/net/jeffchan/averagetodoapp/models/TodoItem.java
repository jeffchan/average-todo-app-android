package net.jeffchan.averagetodoapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "TodoItems")
public class TodoItem extends Model {
    @Column(name = "title")
    private String title;

    @Column(name = "timestamp")
    private Date timestamp;

    public TodoItem() {
        super();
    }

    public TodoItem(String title) {
        super();
        this.title = title;
        this.timestamp = new Date();
    }

    public String getTitle() {
        return title;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public TodoItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public static List<TodoItem> getAll() {
        return new Select()
                .from(TodoItem.class)
                .orderBy("timestamp ASC")
                .execute();
    }
}
