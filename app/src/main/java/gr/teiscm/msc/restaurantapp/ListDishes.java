package gr.teiscm.msc.restaurantapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListDishes extends AppCompatActivity {

    ListView lvDishes;
    ArrayList<Dish> dishList;
    DishAdapter adapter;
    Activity act;
//    EditText search;

    //Filters
    String filterDishName;
    Double filterDishPrice = 0.00;
    int filterDishType, filterDishCategoryA, filterDishCategoryB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dishes);
        lvDishes = (ListView) findViewById(R.id.lvDishes);
//        search = (EditText) findViewById(R.id.SearchView);

        act = ListDishes.this;

        new GetJsonTask().execute();
    }

    public void getFiltersFromUser() {
        filterDishName = getIntent().getExtras().getString(SearchDishes.KEY_FILTERDISHNAME);
        filterDishPrice = getIntent().getExtras().getDouble(SearchDishes.KEY_FILTERDISHPRICE);
        filterDishType = getIntent().getExtras().getInt(SearchDishes.KEY_FILTERDISHTYPE);
        filterDishCategoryA = getIntent().getExtras().getInt(SearchDishes.KEY_FILTERDISHCATEGORYA);
        filterDishCategoryB = getIntent().getExtras().getInt(SearchDishes.KEY_FILTERDISHCATEGORYB);
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = act.getAssets().open("dishes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public class GetJsonTask extends AsyncTask<String, String, ArrayList<Dish>> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ListDishes.this);
            dialog.setMessage(getString(R.string.Loading));
            dialog.setTitle(getString(R.string.Connecting));
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected ArrayList<Dish> doInBackground(String... params) {

            getFiltersFromUser();

            HttpURLConnection conn = null;
            BufferedReader reader = null;

            //Genymotion Ip Address 10.0.3.2
            String getUrl = "http://10.0.3.2:8000/dishes/?format=json";

            if (filterDishName != null && !filterDishName.equalsIgnoreCase(""))
                getUrl += "&name=" + filterDishName;

            if (filterDishPrice != 0.00)
                getUrl += "&price=" + filterDishPrice;

            if (filterDishType != 0)
                getUrl += "&type=" + filterDishType;

            if (filterDishCategoryA != 0)
                getUrl += "&categoryA=" + filterDishCategoryA;

            if (filterDishCategoryB != 0)
                getUrl += "&categoryB=" + filterDishCategoryB;


            try {
                URL url = new URL(getUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream is = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                //read data line-by-line
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + '\n');
                }

                String finalJson = buffer.toString();

                //Read json with title
//                JSONObject obj = new JSONObject(finalJson);
//                JSONArray m_jArry = obj.getJSONArray("dishes");


                //Read json without title
                JSONArray m_jArry = new JSONArray(finalJson);

                dishList = new ArrayList<>();
                adapter = new DishAdapter(getApplicationContext(), R.layout.row, dishList);

                for (int i = 0; i < m_jArry.length(); i++) {
                    JSONObject object = m_jArry.getJSONObject(i);

                    Dish dish = new Dish();
                    dish.setDishPk(object.getInt(SearchDishes.KEY_FILTERDISHPK));
                    dish.setDishName(object.getString(SearchDishes.KEY_FILTERDISHNAME));
                    dish.setDishPrice(object.getDouble(SearchDishes.KEY_FILTERDISHPRICE));
                    dish.setDishType(object.getInt(SearchDishes.KEY_FILTERDISHTYPE));
                    dish.setDishCategoryA(object.getInt(SearchDishes.KEY_FILTERDISHCATEGORYA));
                    dish.setDishCategoryB(object.getInt(SearchDishes.KEY_FILTERDISHCATEGORYB));
                    dish.setDishImage(object.getString(SearchDishes.KEY_FILTERDISHIMAGE));
                    dish.setDishDescription(object.getString(SearchDishes.KEY_FILTERDISHDESCRIPTION));
                    dishList.add(dish);
                }


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
            return dishList;
        }

        @Override
        protected void onPostExecute(ArrayList<Dish> result) {
            super.onPostExecute(result);

            if (result == null) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), getString(R.string.NoDishes), Toast.LENGTH_SHORT).show();
            } else {

                adapter.notifyDataSetChanged();

                lvDishes.setAdapter(adapter);

                dialog.cancel();

//                search.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        if (count < before) {
//                            // We're deleting char so we need to reset the adapter data
//                            adapter.resetData();
//                        }
//
//                        adapter.getFilter().filter(s.toString());
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });


                lvDishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Dish dish = (Dish) parent.getItemAtPosition(position);

                        Intent i = new Intent(ListDishes.this, DetailsDish.class);
                        //Send Dish data to the next activity
                        //TODO
                        i.putExtra("Dish", dish);
                        startActivity(i);

                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}




