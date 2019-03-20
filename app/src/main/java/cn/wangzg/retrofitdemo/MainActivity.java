package cn.wangzg.retrofitdemo;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ViewPager vp_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initView() {
        vp_banner = findViewById(R.id.vp_banner);

    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final IBanner banner = retrofit.create(IBanner.class);
        Call<BannerBean> call = banner.getBanner();
        call.enqueue(new Callback<BannerBean>() {
            @Override
            public void onResponse(Call<BannerBean> call, Response<BannerBean> response) {
                final BannerBean bannerBean = response.body();
                final List<BannerBean.DataBean> data = bannerBean.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        vp_banner.setAdapter(new PagerAdapter() {
                            @Override
                            public int getCount() {
                                System.out.println("-----size="+data.size());
                                return data.size();
                            }

                            @Override
                            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                                return view == o;
                            }

                            @NonNull
                            @Override
                            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                                ImageView view = new ImageView(container.getContext());
                                Glide.with(MainActivity.this)
                                        .load(data.get(position).getImagePath())
                                        .into(view);
                                container.addView(view);
                                return view;
                            }

                            @Override
                            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                                container.removeView((View) object);
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<BannerBean> call, Throwable t) {

            }
        });
    }
}
