package com.company.pellet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private EditText product;
    private EditText wrapping;
    private EditText fr;
    private EditText destination;
    private EditText distance;
    private EditText oneKmCost;
    private EditText weight;
    private EditText buyPrice;
    private EditText margin;
    private EditText expenses;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        product = (EditText) findViewById(R.id.productEditText);
        wrapping = (EditText) findViewById(R.id.wrappingEditText);
        fr = (EditText) findViewById(R.id.fromEditText);
        destination = (EditText) findViewById(R.id.toEditText);
        distance = (EditText) findViewById(R.id.distanceEditText);
        oneKmCost = (EditText) findViewById(R.id.oneKmEditText);
        weight = (EditText) findViewById(R.id.weightEditText);
        buyPrice = (EditText) findViewById(R.id.buyEditText);
        margin = (EditText) findViewById(R.id.marginEditText);
        expenses = (EditText) findViewById(R.id.expEditText);
        button = (Button) findViewById(R.id.button);

        fillDataOnStart();

        Colorize colorize = new Colorize();
        colorize.colorMain(product, wrapping, fr, destination, distance, oneKmCost, weight, buyPrice, margin, expenses,
                getResources(), getWindow(), button);
    }

    public void onResulBtnClick(View view) {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);

        Checker check = new Checker();
        check.checkFilling(product, wrapping, fr, destination, distance, oneKmCost, weight, buyPrice, margin, expenses);

        intent.putExtra("product", product.getText().toString());
        intent.putExtra("wrapping", wrapping.getText().toString());
        intent.putExtra("fr", fr.getText().toString());
        intent.putExtra("destination", destination.getText().toString());
        intent.putExtra("distance", Float.valueOf(distance.getText().toString()));
        intent.putExtra("oneKmCost", Float.valueOf(oneKmCost.getText().toString()));
        intent.putExtra("weight", Float.valueOf(weight.getText().toString()));
        intent.putExtra("buyPrice", Float.valueOf(buyPrice.getText().toString()));
        intent.putExtra("margin", Integer.valueOf(margin.getText().toString()));
        intent.putExtra("expenses", Float.valueOf(expenses.getText().toString()));

        if (!check.error) {
            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Weight must be > 0", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Операции для выбранного пункта меню
        switch (item.getItemId()) {
            case R.id.searchItem: {
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intentSearch, 0);
                return true;
            }
            case R.id.preferencesItem: {
                Intent intentPreferences = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivityForResult(intentPreferences, 1);
                return true;
            }
            case R.id.clearItem: {
                clearValues();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearValues() {
        product.setText("");
        wrapping.setText("");
        fr.setText("");
        destination.setText("");
        distance.setText("");
        oneKmCost.setText("");
        weight.setText("");
        buyPrice.setText("");
        margin.setText("");
        expenses.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String id = data.getStringExtra("item_id");
                fillDataOnSearch(id);
            }
        }
    }

    private void fillDataOnSearch(String id) {
        DataBase db = new DataBase(getApplicationContext());
        ArrayList<SelectedProduct> selectedProduct = db.selectProduct(id);

        product.setText(selectedProduct.get(0).productName);
        wrapping.setText(selectedProduct.get(0).wrapping);
        fr.setText(selectedProduct.get(0).fr);
        destination.setText(selectedProduct.get(0).destination);
        distance.setText(selectedProduct.get(0).distance);
        oneKmCost.setText(selectedProduct.get(0).oneKmCost);
        weight.setText(selectedProduct.get(0).weight);
        buyPrice.setText(selectedProduct.get(0).buyPrice);
        margin.setText(selectedProduct.get(0).margin);
        expenses.setText(selectedProduct.get(0).expenses);
    }

    private void fillDataOnStart() {
        DataBase db = new DataBase(getApplicationContext());
        ArrayList<SelectedProduct> selectedProduct = db.selectProduct(db.getLastId());

        if (selectedProduct.size() > 0) {
            product.setText(selectedProduct.get(0).productName);
            wrapping.setText(selectedProduct.get(0).wrapping);
            fr.setText(selectedProduct.get(0).fr);
            destination.setText(selectedProduct.get(0).destination);
            distance.setText(selectedProduct.get(0).distance);
            oneKmCost.setText(selectedProduct.get(0).oneKmCost);
            weight.setText(selectedProduct.get(0).weight);
            buyPrice.setText(selectedProduct.get(0).buyPrice);
            margin.setText(selectedProduct.get(0).margin);
            expenses.setText(selectedProduct.get(0).expenses);
        }
    }

}