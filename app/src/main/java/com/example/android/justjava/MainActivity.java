package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
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
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;
    int pricePerCup=5;


    public void increment(View view){

        if(quantity==100)
        {
            Toast.makeText(this , "You can not order more coffee" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity =quantity+ 1;
        displayQuanitity(quantity);
    }

    public void decrement(View view){
        if(quantity==1)
        {
            Toast.makeText(this , "Order atleast 1 cup!" , Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        displayQuanitity(quantity);
    }

    public int calculatePrice(boolean addWhippedCream , boolean addChocolate){
        int basePrice = 5;

        if(addWhippedCream)
        {
            basePrice+= 1;
        }
        if(addChocolate)
        {
            basePrice+= 2;
        }



        return basePrice*quantity;
    }

    public String createOrderSummery(int price, boolean addWhippedCream , boolean chocolateCheckBox , String Name){
        String priceMessage ="Name : " + Name;
        priceMessage+= "\nAdd whipped cream? " + addWhippedCream;
        priceMessage+= "\nAdd Chocolate? " + chocolateCheckBox;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage =  priceMessage +"\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank You";
        return priceMessage;
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();



        EditText nameField = (EditText)findViewById(R.id.name_feild);
        String name = nameField.getText().toString();


        int price = calculatePrice(hasWhippedCream , hasChocolate);
        String priceMessage = createOrderSummery(price,hasWhippedCream , hasChocolate , name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order Summery for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuanitity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}