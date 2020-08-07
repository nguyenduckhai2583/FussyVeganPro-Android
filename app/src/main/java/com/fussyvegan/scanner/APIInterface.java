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

    @GET("get_product.php?api_key=45090dcae2aYMK")
    Call<ResourceProductAirline> getProductAirline(@Query("airline") String typeAirline);

    @GET("get_products.php?api_key=45090dcae2aYMK")
    Call<ResourceLocationAirport> getLocationAirport(@Query("search") String search, @Query("airport_code") String airportCode);

    @GET("get_products.php?api_key=45090dcae2aYMK")
    Call<ResourceResort> getResorts(@Query("search") String search, @Query("country") String country);
}
