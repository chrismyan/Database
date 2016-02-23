package com.ebookfrenzy.database;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {
    TextView idView;
    EditText productBox;
    EditText quantityBox;
    Button btnUpdate;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        quantityBox =
                (EditText) findViewById(R.id.productQuantity);
        btnUpdate = (Button)  findViewById(R.id.button4);
        btnUpdate.setEnabled(false);
        dbHandler = new MyDBHandler(this, null, null, 1);


    }

    public void newProduct (View view) {
        if(isValid()) {
            int quantity =
                    Integer.parseInt(quantityBox.getText().toString());

            Product product =
                    new Product(productBox.getText().toString(), quantity);

            dbHandler.addProduct(product);
            productBox.setText("");
            quantityBox.setText("");
        }
    }

    public void updateProducts(View view) {
        if(isValid()) {
            int quantity =
                    Integer.parseInt(quantityBox.getText().toString());
            Product product = new Product(productBox.getText().toString(), quantity);
            dbHandler.UpdateProduct(product);            productBox.setText("");
            quantityBox.setText("");
            btnUpdate.setEnabled(false);
        }
    }

    public void lookupProduct (View view) {

        Product product =
                dbHandler.findProduct(productBox.getText().toString());

        btnUpdate.setEnabled(false);
        if (product != null) {
            idView.setText(String.valueOf(product.getID()));

            quantityBox.setText(String.valueOf(product.getQuantity()));
            btnUpdate.setEnabled(true);
        } else {
            idView.setText("No Match Found");
        }
    }

    public void removeProduct (View view) {

        boolean result = dbHandler.deleteProduct(
                productBox.getText().toString());

        if (result)
        {
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }

    public void clearProducts(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.onUpgrade(dbHandler.getWritableDatabase(), 1, 1);
        this.idView.setText("");
        this.productBox.setText("");
        this.quantityBox.setText("");
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isValid() {
        boolean valid = false;
        try {
           Integer.parseInt(quantityBox.getText().toString());
            if(productBox.getText().toString().equals("")) {
                return false;
            }
            valid = true;
        } catch (NumberFormatException ex) {
            Log.w("NumberFormatException", ex.toString());
        } catch (Exception ex) {
            Log.w("Exception", ex.toString());
        }
        return valid;
    }
}
