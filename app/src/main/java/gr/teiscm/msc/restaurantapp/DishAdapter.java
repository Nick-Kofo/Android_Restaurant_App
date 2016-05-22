package gr.teiscm.msc.restaurantapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by Cooper on 12/3/2016.
 */
public class DishAdapter extends ArrayAdapter<Dish> implements Filterable {

    ArrayList<Dish> dishList;
    ArrayList<Dish> origDishList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    private Filter dishFilter;

    public DishAdapter(Context context, int resource, ArrayList<Dish> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        dishList = objects;
        origDishList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.ivDishImage = (ImageView) v.findViewById(R.id.ivDishImage);
            holder.tvDishName = (TextView) v.findViewById(R.id.tvDishName);
            holder.tvDishType = (TextView) v.findViewById(R.id.tvDishType);
            holder.tvDishPrice = (TextView) v.findViewById(R.id.tvDishPrice);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Picasso.with(v.getContext()).load(dishList.get(position).getDishImage()).into(holder.ivDishImage);

        holder.tvDishName.setText(dishList.get(position).getDishName());
        //Dish Type Name acording to id
        if (dishList.get(position).getDishType() == 1) {
            holder.tvDishType.setText(R.string.Appetizer);
        } else if (dishList.get(position).getDishType() == 2) {
            holder.tvDishType.setText(R.string.Main);
        } else {
            holder.tvDishType.setText(R.string.Dessert);
        }
        holder.tvDishPrice.setText(dishList.get(position).getDishPrice().toString());

        return v;
    }

    static class ViewHolder {
        public ImageView ivDishImage;
        public TextView tvDishName;
        public TextView tvDishType;
        public TextView tvDishPrice;

    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    public void resetData() {
        dishList = origDishList;
    }

    @Override
    public Filter getFilter() {
        if (dishFilter == null)
            dishFilter = new DishFilter();

        return dishFilter;
    }


    private class DishFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origDishList;
                results.count = origDishList.size();
            } else {
                // We perform filtering operation
                List<Dish> nDishList = new ArrayList<Dish>();

                for (Dish d : dishList) {
                    if (d.getDishName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nDishList.add(d);
                }

                results.values = nDishList;
                results.count = nDishList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                dishList = (ArrayList<Dish>) results.values;
                notifyDataSetChanged();
            }

        }

    }
}
