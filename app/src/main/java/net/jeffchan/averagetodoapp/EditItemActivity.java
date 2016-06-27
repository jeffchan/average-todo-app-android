package net.jeffchan.averagetodoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {

    private int mItemPosition;
    private String mItem;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mItem = getIntent().getStringExtra("item");
        mItemPosition = getIntent().getIntExtra("itemPosition", 0);

        mEditText = (EditText) findViewById(R.id.editText);
        mEditText.setText(mItem);
        mEditText.setSelection(mItem.length());
    }

    public void onClickSave(View view) {
        String newItemText = mEditText.getText().toString();
        Intent data = new Intent();
        data.putExtra("newItemText", newItemText);
        data.putExtra("itemPosition", mItemPosition);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onClickCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
