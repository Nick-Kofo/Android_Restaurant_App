package gr.teiscm.msc.restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cooper on 11/3/2016.
 */
public class SearchDishes extends AppCompatActivity {

    public static String KEY_FILTERDISHNAME = "name", KEY_FILTERDISHPRICE = "price", KEY_FILTERDISHTYPE = "type",
            KEY_FILTERDISHCATEGORYA = "categoryA", KEY_FILTERDISHCATEGORYB = "categoryB", KEY_FILTERDISHIMAGE = "photo", KEY_FILTERDISHDESCRIPTION = "description", KEY_FILTERDISHPK = "pk";

    Activity act;

    EditText etDishName, etDishPrice;
    Spinner spDishType, spDishCategoryA, spDishCategoryB;
    FloatingActionButton fabSearch;
//    FloatingActionButton fabAdd;

    String filterDishName;
    Double filterDishPrice;
    int filterDishType, filterDishCategoryA, filterDishCategoryB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_dishes);
        act = SearchDishes.this;

        fillViews();
        fillSpinnerDishType();
        fillSpinnerDishCategoryA();
        fillSpinnerDishCategoryB();
        getUserFilters();
//        addDish();

    }

    private void fillViews() {
        etDishName = (EditText) findViewById(R.id.etDishName);
        etDishPrice = (EditText) findViewById(R.id.etDishPrice);
        spDishType = (Spinner) findViewById(R.id.spDishType);
        spDishCategoryA = (Spinner) findViewById(R.id.spCategoryA);
        spDishCategoryB = (Spinner) findViewById(R.id.spCategoryB);
        fabSearch = (FloatingActionButton) findViewById(R.id.fabSearch);
//        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
    }

    private void fillSpinnerDishType() {
        List<String> spinnerDishType = new ArrayList<>();
        spinnerDishType.add(getString(R.string.DishType));
        spinnerDishType.add(getString(R.string.Appetizer));
        spinnerDishType.add(getString(R.string.Main));
        spinnerDishType.add(getString(R.string.Dessert));

        ArrayAdapter<String> spinnerDishTypeAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerDishType);

        spinnerDishTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spDishType);
        sItems.setAdapter(spinnerDishTypeAdapter);
    }

    private void fillSpinnerDishCategoryA() {
        List<String> spinnerDishCategory = new ArrayList<>();
        spinnerDishCategory.add(getString(R.string.DishCategoryA));
        spinnerDishCategory.add(getString(R.string.Meat));
        spinnerDishCategory.add(getString(R.string.Stew));
        spinnerDishCategory.add(getString(R.string.Soup));
        spinnerDishCategory.add(getString(R.string.SeaFood));
        spinnerDishCategory.add(getString(R.string.Salad));

        ArrayAdapter<String> spinnerDishCategoryAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerDishCategory);

        spinnerDishCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spCategoryA);
        sItems.setAdapter(spinnerDishCategoryAdapter);
    }

    private void fillSpinnerDishCategoryB() {
        List<String> spinnerDishCategory = new ArrayList<>();
        spinnerDishCategory.add(getString(R.string.DishCategoryB));
        spinnerDishCategory.add(getString(R.string.Frozen));
        spinnerDishCategory.add(getString(R.string.Vegetarian));
        spinnerDishCategory.add(getString(R.string.Savory));
        spinnerDishCategory.add(getString(R.string.Sweet));

        ArrayAdapter<String> spinnerDishCategoryAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerDishCategory);

        spinnerDishCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spCategoryB);
        sItems.setAdapter(spinnerDishCategoryAdapter);
    }

    private void getUserFilters() {
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get filters from user
                filterDishName = etDishName.getText().toString();
                if (etDishPrice.getText().toString().equals("")) {
                    filterDishPrice = 0.00;
                } else {
                    filterDishPrice = Double.parseDouble(etDishPrice.getText().toString());
                }
                filterDishType = spDishType.getSelectedItemPosition();
                filterDishCategoryA = spDishCategoryA.getSelectedItemPosition();
                filterDishCategoryB = spDishCategoryB.getSelectedItemPosition();

                //Send filters to next activity
                Intent i = new Intent(SearchDishes.this, ListDishes.class);
                Bundle extras = new Bundle();
                extras.putString(KEY_FILTERDISHNAME, filterDishName);
                extras.putDouble(KEY_FILTERDISHPRICE, filterDishPrice);
                extras.putInt(KEY_FILTERDISHTYPE, filterDishType);
                extras.putInt(KEY_FILTERDISHCATEGORYA, filterDishCategoryA);
                extras.putInt(KEY_FILTERDISHCATEGORYB, filterDishCategoryB);

                i.putExtras(extras);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
    }

//    private void addDish() {
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(act, AdminLogin.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
