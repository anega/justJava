/*
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increase(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 cup of coffey.", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrease(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than 1 cup of coffey.", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.is_whipped_сream);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        EditText nameText = (EditText) findViewById(R.id.user_name);
        String userName = nameText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, userName);
        String subjectMessage = getString(R.string.order_summary_email_subject, userName);
        composeEmail(subjectMessage, priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }

        return basePrice * quantity;
    }

    /**
     * Creates summary for client order
     *
     * @param price           total price or clients order
     * @param hasWhippedCream is whipped cream added
     * @param hasChocolate    is chocolate added
     * @param userName        entered username
     * @return string with order summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String userName) {
        String summary = getString(R.string.order_summary_name, userName) + "\n";
        summary += getString(R.string.order_summary_whipped_cream, hasWhippedCream) + "\n";
        summary += getString(R.string.order_summary_chocolate, hasChocolate) + "\n";
        summary += getString(R.string.order_summary_quantity, quantity) + "\n";
        summary += getString(R.string.order_summary_price, price) + "\n";
        summary += getString(R.string.thank_you);
        return summary;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method sends an intent to mail app with the given info.
     */
    public void composeEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}