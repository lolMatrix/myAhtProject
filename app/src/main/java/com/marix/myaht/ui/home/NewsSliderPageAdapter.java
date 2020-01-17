package com.marix.myaht.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.marix.myaht.NewsActivity;
import com.marix.myaht.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class NewsSliderPageAdapter extends PagerAdapter {

    private JSONArray news = new JSONArray();
    private Context context;
    private final String TAG = "MyAht";
    private FragmentTransaction transaction;

    public NewsSliderPageAdapter(Context c, FragmentTransaction f){
        context = c;
        transaction = f;
    }

    @Override
    public int getCount() {
        return news.length();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        TextView title;
        ImageView image;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.slider_layout, container, false);
        try {
        title = (TextView) itemView.findViewById(R.id.sliderTitle);
        title.setText(news.getJSONObject(position).getJSONObject("title").getString("rendered"));
        image = (ImageView) itemView.findViewById(R.id.sliderImage);

            Picasso.get().load(news.getJSONObject(position).getString("jetpack_featured_media_url"))
                    .into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent intent = new Intent(context, NewsActivity.class);
                        intent.putExtra("object", news.getJSONObject(position).toString());
                        context.startActivity(intent);
                        Toast.makeText(context, "Пiшов нахуй, ты, " + news.getJSONObject(position).getJSONObject("title").getString("rendered"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((CardView) object);
    }

    public void updateArray(JSONArray a){
        news = a;
        notifyDataSetChanged();
    }

}
