package com.example.android.simpledb;

import android.provider.BaseColumns;

public class GroceryContract {
    private GroceryContract() {
    }

    public static final class GroceryEntry implements BaseColumns {
        public static final String TABLE_NAME = "grocerylist";
        public static final String COL_NAME = "name";
        public static final String COL_AMOUNT = "amount";
        public static final String COL_TIMESTAMP = "timestamp";

    }
}
