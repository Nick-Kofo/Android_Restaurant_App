package gr.teiscm.msc.restaurantapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Cooper on 12/3/2016.
 */
public class DetailsDish extends AppCompatActivity {

    ImageView detailImage;
    TextView detailName, detailType, detailPrice, detailDescription;
    FloatingActionButton fabWebsite;

    String dishName, dishImage, dishDescription;
    int dishPk, dishType;
    Double dishPrice;
    Dish dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        fillViews();

        //Get Dish
        Intent i = getIntent();
        dish = (Dish) i.getSerializableExtra("Dish");
        getDishItems();
        giveValuesToViews();
    }

    private void fillViews() {
        detailImage = (ImageView) findViewById(R.id.ivDetailImage);
        detailName = (TextView) findViewById(R.id.tvDetailName);
        detailType = (TextView) findViewById(R.id.tvDetailType);
        detailPrice = (TextView) findViewById(R.id.tvDetailPrice);
        detailDescription = (TextView) findViewById(R.id.tvDetailDescription);
        fabWebsite = (FloatingActionButton) findViewById(R.id.fabWebsite);
    }

    private void getDishItems() {
        dishPk = dish.getDishPk();
        dishImage = dish.getDishImage();
        dishName = dish.getDishName();
        dishType = dish.getDishType();
        dishPrice = dish.getDishPrice();
        dishDescription = dish.getDishDescription();
    }

    private void giveValuesToViews() {
        Picasso.with(DetailsDish.this).load(dishImage).into(detailImage);
        detailName.setText(dishName);
        if (dishType == 1) {
            detailType.setText(R.string.Appetizer);
        } else if (dishType == 2) {
            detailType.setText(R.string.Main);
        } else {
            detailType.setText(R.string.Dessert);
        }
        detailPrice.setText(dishPrice.toString());
        detailDescription.setText(dishDescription);
    }

    public void detailWebsite(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://10.0.3.2:8000/dish/" + dishPk + "/"));
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
