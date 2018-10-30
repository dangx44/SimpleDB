package com.example.android.simpledb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    private GroceryApdater mAdapter;
    private EditText mEditTextName;
    private TextView mTextViewAmount;
    private int mAmount;
    private Button btn_Increment, btn_Decrement, btn_Add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GroceryDBhelper dbHelper = new GroceryDBhelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryApdater(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long)viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        mEditTextName = (EditText)findViewById(R.id.edit_text);
        mTextViewAmount = (TextView)findViewById(R.id.textView_amount);

        btn_Increment = (Button)findViewById(R.id.btn_increase);
        btn_Decrement = (Button)findViewById(R.id.btn_decrease);
        btn_Add = (Button)findViewById(R.id.btn_add);

        btn_Increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });

        btn_Decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void increase(){
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));
    }

    private void decrease(){
        if(mAmount > 0) {
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }
    }

    private void addItem(){
        if(mEditTextName.getText().toString().trim().length() == 0 || mAmount == 0){
            return;
        }
        String name = mEditTextName.getText().toString().trim();
        ContentValues cv = new ContentValues();
        cv.put(GroceryContract.GroceryEntry.COL_NAME, name);
        cv.put(GroceryContract.GroceryEntry.COL_AMOUNT, mAmount);

        mDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        mEditTextName.getText().clear();

        UIUtil.hideKeyboard(this);
    }

    private void removeItem(long id){

        mDatabase.delete(GroceryContract.GroceryEntry.TABLE_NAME,
                GroceryContract.GroceryEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems(){
        return mDatabase.query(
                GroceryContract.GroceryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryContract.GroceryEntry.COL_TIMESTAMP + " DESC"
        );
    }
}
