package com.example.lab4database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class MyDBHandler extends SQLiteOpenHelper {
    //defining the schema
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PRODUCTNAME = "productname";
    private static final String COLUMN_PRICE = "price";

    public MyDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //CREATE TABLE TABLE_PRODUCTS (COLUMN_ID INTEGER PRIMARY KEY, COLUMN_PRODUCTNAME TEXT,
        //COLUMN_PRICE DOUBLE)
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PRODUCTNAME + " TEXT,"
                + COLUMN_PRICE + " DOUBLE" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    //deletes old tables and creates a new one
    //change table by incrementing the database version number

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();

        //creating an empty set of values
        ContentValues values = new ContentValues();

        //add values to the set
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_PRICE, product.getPrice());

        //insert the set into the products table and close

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public Product findProduct(String productname){
        SQLiteDatabase db = this.getWritableDatabase();

        //run a query to find the product with the specified product name
        // SELECT * FROM TABLE_PRODUCTS WHERE COLUMN_PRODUCTNAME = "productname"

        String query = "SELECT * FROM " + TABLE_PRODUCTS
                + " WHERE " + COLUMN_PRODUCTNAME
                + " = \"" + productname + "\"";
        //passing the query
        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        //moves cursor to the first row
        if (cursor.moveToFirst()){
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setPrice(Double.parseDouble(cursor.getString(2)));
            cursor.close();
        }else{
            product=null;
        }
        db.close();

        //we return the first product in the query result with the pseciified product name
        //or null if there is no product with that name
        return product;
    }

    //delete from basebase
    public boolean deleteProduct(String productname){
        boolean result = false;
        SQLiteDatabase db= this.getWritableDatabase();

        //run a query to find the product with the specified name, then delete
        //SELECT * FROM TABLE_PRODUCTS WHERE COLUMN_PRODUCTNAME = "productname"
        String query = "SELECT * FROM " + TABLE_PRODUCTS
                + " WHERE " + COLUMN_PRODUCTNAME
                + " = \"" + productname + "\"";
        //passing the query
        Cursor cursor = db.rawQuery(query, null);

        //moves cursor to the first row
        //this deletes the first occurence of the product with the specified none
        if (cursor.moveToFirst()){
            String idStr = cursor.getString(0);
            db.delete(TABLE_PRODUCTS,COLUMN_ID + " = " + idStr, null);
            cursor.close();
            result=true;
        }
        db.close();

        //if product is deleted this returns true
        return result;
    }

    //read all from table
    public ArrayList<Product> readProducts(){
        SQLiteDatabase db = this.getReadableDatabase();

        //passing the query
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

        //create arraylist for our products
        ArrayList<Product> productArrayList = new ArrayList<>();

        //while there are products in our table, keep moving to the next product
        //we add the product id, name, and price for each new element in the arraylist
        //column 0 is product id, column 1 is the product name, column 2 is product price in our table

        if (cursorProducts.moveToFirst()){
            do{
                productArrayList.add(new Product(cursorProducts.getInt(0),
                        cursorProducts.getString(1),
                        cursorProducts.getDouble(2)));
            }while (cursorProducts.moveToNext());
        }
        cursorProducts.close();
        return productArrayList;
    }

}

