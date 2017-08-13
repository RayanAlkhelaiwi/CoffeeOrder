package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * An app to order a number of coffees to then return a summary of the order.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    /**
     * the onCreate method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activityTitle);
    }

    /**
     * This method is called when the order button is clicked.
     *
     * @param view
     */
    public void submitOrder(View view) {

        //Variables to declare
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.WhippedCreamCheckbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.ChocolateCheckBox);
        EditText nameEditText = (EditText) findViewById(R.id.NameEditText);

        boolean wpIsChecked = whippedCreamCheckBox.isChecked();
        boolean chocoIsChecked = chocolateCheckBox.isChecked();
        String nameString = nameEditText.getText().toString().trim();
        int price = calculatePrice(wpIsChecked, chocoIsChecked);

        //Intent to prompt the app to use an external existed email app to send the summary to.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"rean.alklwiy@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffeeOrderIntentSubject, nameString));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(nameString, wpIsChecked, chocoIsChecked, price));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //Commented out.
//        displayMessage(createOrderSummary(nameString, wpIsChecked, chocoIsChecked, price));

    }

    /**
     * Calculates the price of the order.
     *
     * @param wpIsChecked    to add $1 if the user choose to add Whip Cream.
     * @param chocoIsChecked to add $2 if the user choose to add Chocolate.
     * @return total price of the coffee
     */
    private int calculatePrice(boolean wpIsChecked, boolean chocoIsChecked) {

        int price = 5;

        if (wpIsChecked) price += 1;
        if (chocoIsChecked) price += 2;

        return quantity * price;
    }

    /**
     * Creates a summary of the coffee order
     *
     * @param name           takes the user's input of his/her name.
     * @param wcIsChecked    to check whether a whipped cream is chosen or not.
     * @param chocoIsChecked to check whether a chocolate is chosen or not.
     * @param price          to calculate the price of the coffee ordered, with respect to its quantity.
     * @return a summary of the order, as shown down below.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String createOrderSummary(String name, boolean wcIsChecked, boolean chocoIsChecked, int price) {

        String isWC = " ", isCH = " ";

        if (wcIsChecked) {
            isWC = getString(R.string.yes);
        } else if (!wcIsChecked) {

            isWC = getString(R.string.no);
        }
        if (chocoIsChecked) {
            isCH = getString(R.string.yes);
        } else if (!chocoIsChecked) {

            isCH = getString(R.string.no);
        }

        return getString(R.string.nameCustomerOrder, name) +
                "\n" + getString(R.string.quantityOrder) + " " + quantity +
                "\n" + getString(R.string.wpOrder) + " " + isWC +
                "\n" + getString(R.string.chocoOrder) + " " + isCH +
                "\n" + getString(R.string.totalOrder) + " " + NumberFormat.getCurrencyInstance().format(price) +
                "\n" + getString(R.string.thankYouOrder);
    }

    /**
     * This method is called when the plus button is clicked.
     *
     * @param view
     */
    public void increment(View view) {

        Toast toast;
        Context context = getApplicationContext();
        quantity = quantity + 1;

        if (quantity > 100) {

            quantity = 100;
            toast = Toast.makeText(context, R.string.incToast, Toast.LENGTH_SHORT);
            toast.show();
        }

        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     *
     * @param view
     */
    public void decrement(View view) {

        Toast toast;
        Context context = getApplicationContext();
        quantity = quantity - 1;

        if (quantity < 0) {

            quantity = 0;
            toast = Toast.makeText(context, R.string.decToast, Toast.LENGTH_SHORT);
            toast.show();
        }

        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     *
     * @param number
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given message on the screen.
     *
     * @param message
     */

    //Commented out.
//    private void displayMessage (String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}