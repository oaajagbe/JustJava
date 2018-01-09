/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //shows an error message as toast
            Toast.makeText(this, "You cannot have less than 100 coffee cup", Toast.LENGTH_SHORT).show();
            //exit because there is nothing else to do
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //shows an error message as toast
            Toast.makeText(this, "You cannot have less than 1 coffee cup", Toast.LENGTH_SHORT).show();
            //exit because there is nothing else to do
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //Collects the user's name
        EditText nameTextField = (EditText) findViewById(R.id.name_text_field);
        String nameText = nameTextField.getText().toString();

        //Figures what the user wants
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();


        //Figures what the user wants
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "oaajagbe@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " + nameText);
        intent.putExtra(Intent.EXTRA_TEXT, (createOrderSummary(nameText, price, hasWhippedCream, hasChocolate)));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order
     *
     * @param addWhippedCream is whether or not the user wants topping
     * @param addChocolate is whether or not the user wants topping
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int pricePerCup = 200;

        if (addWhippedCream) {
            pricePerCup+= 50;
        }

        if (addChocolate) {
            pricePerCup+= 100;
        }
        return quantity * pricePerCup; //You may set int price = quantity * pricePerCup and return price
    }

    /**
     * Displays Name, quantity total and text
     * returns it to the submitOrder method
     *
     * @param addName - name of customer
     * @param price - price of the order
     * @param addWhippedCream specifies whether the user has added or not
     * @param addChocolate specifies whether the user has added or not
     *
     * @return text summary
     */
    private String createOrderSummary(String addName, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, addName) +
                "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream) +
                "\n" + getString(R.string.order_summary_chocolate, addChocolate) +
                "\n" + getString(R.string.order_summary_quantity, quantity) +
                "\n\u20A6" + getString(R.string.order_summary_price, "\u20A6" + price) + ".00" + "" +
                "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
