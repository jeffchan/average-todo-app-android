package net.jeffchan.averagetodoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class EditItemFragment extends DialogFragment {
    private static final String ITEM = "item";
    private static final String ITEM_POSITION = "itemPosition";

    private int mItemPosition;
    private String mItem;

    private EditText mEditText;
    private Button mSaveButton;

    private EditItemFragmentListener mListener;

    public interface EditItemFragmentListener {
        void onFinishEditDialog(String newItem, int itemPosition);
    }

    public EditItemFragment() {
        // Required empty public constructor
    }

    public static EditItemFragment newInstance(String item, int itemPosition) {
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString(ITEM, item);
        args.putInt(ITEM_POSITION, itemPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mItem = getArguments().getString(ITEM);
            mItemPosition = getArguments().getInt(ITEM_POSITION);
        }

        mEditText = (EditText) view.findViewById(R.id.editText);
        mEditText.setText(mItem);
        mEditText.setSelection(mItem.length());

        mSaveButton = (Button) view.findViewById(R.id.buttonSave);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItemText = mEditText.getText().toString();
                mListener.onFinishEditDialog(newItemText, mItemPosition);
                dismiss();
            }
        });

        getDialog().setTitle("Edit Item");
        mEditText.requestFocus();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditItemFragmentListener) {
            mListener = (EditItemFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EditItemFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
