package gr.teiscm.msc.restaurantapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cooper on 19/4/2016.
 */
public class AddDish extends AppCompatActivity {

    Activity act;
    EditText etAddDishName, etAddDishPrice, etAddDishDescription;
    Spinner spAddDishType, spAddDishCategoryA, spAddDishCategoryB;
    FloatingActionButton fabAddDish, fabPickImage;
    ImageView imageView;
    String dishName, dishDescription;
    Double dishPrice;
    int dishType, dishCategoryA, dishCategoryB;
    private static final int PICK_IMAGE = 100;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        act = AddDish.this;

        fillViews();
        fillSpinnerDishType();
        fillSpinnerDishCategoryA();
        fillSpinnerDishCategoryB();




        fabAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostJsonTask().execute();
            }
        });

        fabPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


    }

    private void fillViews() {
        etAddDishName = (EditText) findViewById(R.id.etAddDishName);
        etAddDishPrice = (EditText) findViewById(R.id.etAddDishPrice);
        spAddDishType = (Spinner) findViewById(R.id.spAddDishType);
        spAddDishCategoryA = (Spinner) findViewById(R.id.spAddCategoryA);
        spAddDishCategoryB = (Spinner) findViewById(R.id.spAddCategoryB);
        etAddDishDescription = (EditText) findViewById(R.id.etAddDescription);
        fabAddDish = (FloatingActionButton) findViewById(R.id.fabAddDish);
        fabPickImage = (FloatingActionButton) findViewById(R.id.fabPickImage);
        imageView = (ImageView) findViewById(R.id.imageView);
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
        Spinner sItems = (Spinner) findViewById(R.id.spAddDishType);
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
        Spinner sItems = (Spinner) findViewById(R.id.spAddCategoryA);
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
        Spinner sItems = (Spinner) findViewById(R.id.spAddCategoryB);
        sItems.setAdapter(spinnerDishCategoryAdapter);
    }

    private void getDishItems() {
        dishName = etAddDishName.getText().toString();
        if (etAddDishPrice.getText().toString().equals("")) {
            dishPrice = 0.00;
        } else {
            dishPrice = Double.parseDouble(etAddDishPrice.getText().toString());
        }
        dishType = spAddDishType.getSelectedItemPosition();
        dishCategoryA = spAddDishCategoryA.getSelectedItemPosition();
        dishCategoryB = spAddDishCategoryB.getSelectedItemPosition();
        dishDescription = etAddDishDescription.getText().toString();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);

            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }
    }

    public byte[] convert_bitmap_to_string(Bitmap bitmap)
    {
        ByteArrayOutputStream full_stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, full_stream);
        byte[] full_bytes = full_stream.toByteArray();
        String Str_image = Base64.encodeToString(full_bytes, Base64.DEFAULT);

        return full_bytes;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public class PostJsonTask extends AsyncTask<String, String, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AddDish.this);
            dialog.setMessage(getString(R.string.Loading));
            dialog.setTitle(getString(R.string.Connecting));
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            getDishItems();

            HttpURLConnection conn = null;
            BufferedReader reader = null;

            //Genymotion Ip Address 10.0.3.2
            String postUrl = "http://10.0.3.2:8000/dishes/";

            try {
                URL url = new URL(postUrl);
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("name", dishName);
                jsonParam.put("price", dishPrice);
                jsonParam.put("type", dishType);
                jsonParam.put("categoryA", dishCategoryA);
                jsonParam.put("categoryB", dishCategoryB);
                jsonParam.put("description", dishDescription);
                jsonParam.put("photo", convert_bitmap_to_string(bitmap));


                conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches (false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");
                conn.connect();

                DataOutputStream wr = new DataOutputStream(conn.getOutputStream ());
                wr.writeBytes (jsonParam.toString());
                wr.flush ();
                wr.close ();

                InputStream is = conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();

                return response.toString();


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }
            // return Contents
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.cancel();

            if(result == null) {
                Toast.makeText(act, R.string.ServerError, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(act, R.string.ServerError, Toast.LENGTH_LONG).show();
            }


        }
    }
}
