package com.fussyvegan.scanner;

import com.fussyvegan.scanner.model.Resource;
import com.fussyvegan.scanner.model.ResourceLocationAirport;
import com.fussyvegan.scanner.model.ResourceProductAirline;
import com.fussyvegan.scanner.model.ResourceResort;
import com.fussyvegan.scanner.model.accountFlow.Email;
import com.fussyvegan.scanner.model.accountFlow.ForgotPassResult;
import com.fussyvegan.scanner.model.accountFlow.PostReviewResult;
import com.fussyvegan.scanner.model.accountFlow.RequestChangePassword;
import com.fussyvegan.scanner.model.accountFlow.RequestLogin;
import com.fussyvegan.scanner.model.accountFlow.RequestRegister;
import com.fussyvegan.scanner.model.accountFlow.ReviewProduct;
import com.fussyvegan.scanner.model.accountFlow.Reviews;
import com.fussyvegan.scanner.model.accountFlow.UpdateReviewProduct;
import com.fussyvegan.scanner.model.accountFlow.UserAccount;
import com.fussyvegan.scanner.model.favorite.CreateGroupRequest;
import com.fussyvegan.scanner.model.favorite.CreateListResponse;
import com.fussyvegan.scanner.model.favorite.FavoriteListResponse;
import com.fussyvegan.scanner.model.favorite.FavoriteType;
import com.fussyvegan.scanner.model.favorite.ListFavoriteResponse;
import com.fussyvegan.scanner.model.restaurant.RestaurantResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("get_product.php?")
    Call<Resource> doGetResponseByName(@Query("api_key") String api_key, @Query("name") String name);

    @GET("get_product.php?")
    Call<Resource> doGetResponseBySearchNoS(@Query("api_key") String api_key, @Query("search") String search);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearch(@Query("api_key") String api_key, @Query("search") String search);

    @GET("get_product.php?")
    Call<Resource> doGetResponseByBarcode(@Query("api_key") String api_key, @Query("barcode") String barcode);

    @GET("get_products.php?")
    Call<Resource> doGetResponseByManufacturer(@Query("api_key") String api_key, @Query("man_info") String man_info);

    @GET("get_products.php?")
    Call<Resource> doGetResponseByKeyword(@Query("api_key") String api_key, @Query("keywords") String keyword);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySpecial(@Query("api_key") String api_key, @Query("special_title") String special);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchOnlyVegan(@Query("api_key") String api_key, @Query("search") String search, @Query("vegan_status") String veganStatus);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchPalmFree(@Query("api_key") String api_key, @Query("search") String search, @Query("vegan_status") String veganStatus, @Query("palm") String Palm);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchGlutenFree(@Query("api_key") String api_key, @Query("search") String search, @Query("vegan_status") String veganStatus, @Query("gluten") String gluten);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchNutFree(@Query("api_key") String api_key, @Query("search") String search, @Query("vegan_status") String veganStatus, @Query("nut") String nut);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchSoyFree(@Query("api_key") String api_key, @Query("search") String search, @Query("vegan_status") String veganStatus, @Query("soy") String soy);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchVeganCompany(@Query("api_key") String api_key, @Query("search") String search, @Query("vegan_status") String veganStatus, @Query("company_status") String Manvegan);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchFastFoodOnlyVegan(@Query("api_key") String api_key, @Query("search") String search, @Query("vegan_status") String veganStatus, @Query("country") String barcode);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchFastFoodOnlyVeganName(@Query("api_key") String api_key, @Query("search") String search, @Query("vegan_status") String veganStatus, @Query("country") String barcode, @Query("name") String name);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchFastFood(@Query("api_key") String api_key, @Query("search") String search, @Query("country") String barcode);

    @GET("get_products.php?")
    Call<Resource> doGetResponseBySearchFastFoodName(@Query("api_key") String api_key, @Query("search") String search, @Query("country") String barcode, @Query("name") String name);

    @POST("login.php?api_key=45090dcae2aYMK")
    Call<UserAccount> requestUserLogin(@Body RequestLogin requestLogin);

    @POST("register.php?api_key=45090dcae2aYMK")
    Call<UserAccount> requestRegister(@Body RequestRegister requestRegister);

    @POST("reset_password.php?api_key=45090dcae2aYMK")
    Call<ForgotPassResult> requestForgotPassword(@Body Email email);

    @POST("post_rating.php?api_key=45090dcae2aYMK")
    Call<PostReviewResult> postReviewProduct(@Header("access-token") String token,
                                             @Body ReviewProduct reviewProduct);

    @GET("get_rating.php?api_key=45090dcae2aYMK")
    Call<Reviews> getReviewProduct(@Header("access-token") String token,
                                   @Query("ratingable_id") int idProduct,
                                   @Query("ratingable_type") int typeProduct);


    @POST("update_rating.php?api_key=45090dcae2aYMK")
    Call<PostReviewResult> updateReviewProduct(@Header("access-token") String token,
                                               @Body UpdateReviewProduct reviewProduct);

    @POST("change_password.php?api_key=45090dcae2aYMK")
    Call<UserAccount> requestChangePassword(@Header("access-token") String token,
                                            @Body RequestChangePassword requestChangePassword);

    @GET("get_restaurants_paginate.php?api_key=45090dcae2aYMK")
    Call<RestaurantResponse> getRestaurant(@Query("search") String search, @Query("country") String country, @Query("region") String region, @Query("page") int page);

    @GET("get_product.php?api_key=45090dcae2aYMK")
    Call<ResourceProductAirline> getProductAirline(@Query("airline") String typeAirline);

    @GET("get_products.php?api_key=45090dcae2aYMK")
    Call<ResourceLocationAirport> getLocationAirport(@Query("search") String search, @Query("airport_code") String airportCode);

    @GET("get_products.php?api_key=45090dcae2aYMK")
    Call<ResourceResort> getResorts(@Query("search") String  search,@Query("keywords") String keywords, @Query("country") String country);

    @GET("get_restaurants_paginate.php?api_key=45090dcae2aYMK")
    Call<RestaurantResponse> getRestaurantByFilter(@Query("search") String search, @Query("country") String country, @Query("region") String region, @Query("page") int page,
                                                   @Query("distance") int distance, @Query("lat") double lat, @Query("lng") double lng,
                                                   @Query("cuisine") String cuisine);

    @GET("get_group.php?api_key=45090dcae2aYMK")
    Call<ListFavoriteResponse> getListGroup(@Header("access-token") String token);

    @POST("add_group.php?api_key=45090dcae2aYMK")
    Call<CreateListResponse> addGroupFavorite(@Header("access-token") String token, @Body CreateGroupRequest request);

    @GET("delete_group.php?api_key=45090dcae2aYMK")
    Call<CreateListResponse> removeGroup(@Header("access-token") String token, @Query("group_id") int group_id);

    @POST("add_favorite.php?api_key=45090dcae2aYMK")
    Call<CreateListResponse> addFavorite(@Header("access-token") String token, @Body FavoriteType favoriteType);

    @GET("get_favorite.php?api_key=45090dcae2aYMK")
    Call<FavoriteListResponse> getFavoriteList(@Header("access-token") String token, @Query("group_id") int group_id);

    @GET("delete_favorite.php?api_key=45090dcae2aYMK")
    Call<CreateListResponse> deleteFavorite(@Header("access-token") String token, @Query("favorite_id") int favorite_id);


    @GET("get_products_paginate.php?api_key=45090dcae2aYMK")
    Call<Resource> getProductsPaginate(@Query("search") String keyword, @Query("vegan_status") String vegan_status,
                                       @Query("page") int page, @Query("palm") String palm,
                                       @Query("gluten") String gluten, @Query("nut") String nut, @Query("soy") String soy
    );


}
