package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int price = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText name = (EditText) findViewById(R.id.name);
        String myName = name.getText().toString();
        CheckBox addCream = (CheckBox) findViewById(R.id.addCream_checkbox);
        boolean hasWhippedCream = addCream.isChecked();
        CheckBox addChocolate = (CheckBox) findViewById(R.id.addChocolate_checkbox);
        boolean hasChocolate = addChocolate.isChecked();
        price = calculatePrice(hasWhippedCream, hasChocolate);
        String message = createOrderSummary(myName, hasWhippedCream, hasChocolate, price);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:zielony.kapturek4@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + myName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * Calculates the price of the order.
     *

     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int pricePerCup = 10;
        if (hasWhippedCream){
            pricePerCup = pricePerCup + 1;
        }
        if (hasChocolate){
            pricePerCup = pricePerCup + 2;
        }
        return quantity * pricePerCup;
    }

    public void increment(View view) {
        if(quantity == 10){
            Toast.makeText(this, "You cannot have more than 10 coffees", Toast.LENGTH_SHORT).show();
            return;
        }else {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }else {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);

    }

    private String createOrderSummary(String myName, boolean hasWhippedCream, boolean hasChocolate, int price) {


        String orderMessage = getString(R.string.order_summary_name) + myName + "\n" + getString(R.string.order_summary_whipped_cream) + hasWhippedCream + "\n" + getString(R.string.order_summary_whipped_cream) + hasChocolate + "\n" + getString(R.string.order_summary_quantity) + quantity + "\n" + getString(R.string.order_summary_price) + price + "zł" + "\n" + getString(R.string.thank_you);
        return orderMessage;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the order summary on the screen.
     */

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}
