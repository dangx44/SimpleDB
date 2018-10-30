package com.example.android.simpledb;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GroceryApdater extends RecyclerView.Adapter<GroceryApdater.GroceryViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public GroceryApdater(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView countText;

        public GroceryViewHolder(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.item_name);
            countText = (TextView) itemView.findViewById(R.id.item_amount);
        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grocery_item, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COL_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COL_AMOUNT));
        long id = mCursor.getLong(mCursor.getColumnIndex(GroceryContract.GroceryEntry._ID));

        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;

        if(newCursor != null){
            notifyDataSetChanged();
        }
    }


}
