package cn.wangzg.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Time: 2019/3/19
 * Author: wangzhiguo
 * Description: 功能描述
 */
public interface IBanner {
    //https://www.wanandroid.com/banner/json
    @GET("banner/json")
    Call<BannerBean> getBanner();

}
