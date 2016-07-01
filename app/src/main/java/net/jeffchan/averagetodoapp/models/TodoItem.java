package net.jeffchan.averagetodoapp.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name = "TodoItems")
public class TodoItem extends Model {
    public enum Status {
        INCOMPLETE, COMPLETED;
    }

    @Column(name = "title")
    private String title;

    @Column(name = "timestamp")
    private Date timestamp;

//    @Column(name = "status")
    private int status;

    public TodoItem() {
        super();
    }

    public TodoItem(String title) {
        super();
        this.title = title;
        this.timestamp = new Date();
//        this.status = Status.INCOMPLETE.ordinal();
    }

    public String getTitle() {
        return title;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() { return status; }

    public TodoItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public TodoItem setStatus(Status status) {
        this.status = status.ordinal();
        return this;
    }

    public static List<TodoItem> getAll() {
        return new Select()
                .from(TodoItem.class)
                .orderBy("timestamp ASC")
                .execute();
    }
}
