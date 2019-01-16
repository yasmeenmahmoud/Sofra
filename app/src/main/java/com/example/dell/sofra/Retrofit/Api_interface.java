package com.example.dell.sofra.Retrofit;

import android.net.Uri;

import com.example.dell.sofra.Model.AddNewOffer_Response;
import com.example.dell.sofra.Model.AddOrder_Response;
import com.example.dell.sofra.Model.AddProduct_Response;
import com.example.dell.sofra.Model.AddReviw_Response;
import com.example.dell.sofra.Model.Category_Response;
import com.example.dell.sofra.Model.ChangeState_Response;
import com.example.dell.sofra.Model.CityList_Response;
import com.example.dell.sofra.Model.Comission_Response;
import com.example.dell.sofra.Model.ConfirmOrder_Response;
import com.example.dell.sofra.Model.ContactUs_Response;
import com.example.dell.sofra.Model.DeclineOrder_Response;
import com.example.dell.sofra.Model.EditRestaurantInfoResponse;
import com.example.dell.sofra.Model.Login_Response;
import com.example.dell.sofra.Model.MyItems_Response;
import com.example.dell.sofra.Model.MyOffers_Response;
import com.example.dell.sofra.Model.MyOrdersRestaurant_Response;
import com.example.dell.sofra.Model.MyOrders_Response;
import com.example.dell.sofra.Model.Notification_Response;
import com.example.dell.sofra.Model.OffersLists_Response;
import com.example.dell.sofra.Model.PaymentMethod_Response;
import com.example.dell.sofra.Model.RegectOrder_Response;
import com.example.dell.sofra.Model.Region_Response;
import com.example.dell.sofra.Model.RestAcceptOrder_Response;
import com.example.dell.sofra.Model.RestConfirmOrder_Response;
import com.example.dell.sofra.Model.RestauranrLogin_Response;
import com.example.dell.sofra.Model.RestaurantDetails_Response;
import com.example.dell.sofra.Model.RestaurantItems_Response;
import com.example.dell.sofra.Model.RestaurantRegister_Response;
import com.example.dell.sofra.Model.RestaurantReview_Response;
import com.example.dell.sofra.Model.RestaurantsList_Response;
import com.example.dell.sofra.Model.Register_Response;
import com.example.dell.sofra.Model.UserNotification_Response;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api_interface {
    @POST("client/login")
    @FormUrlEncoded
    Call<Login_Response> Login(@Field("email") String email,
                               @Field("password") String password
    );

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestauranrLogin_Response> restaurantLogin(@Field("email") String email,
                                                   @Field("password") String password
    );

    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs_Response> contactUs(@Field("name") String name, @Field("email") String email,
                                       @Field("phone") String phone, @Field("content") String content, @Field("type") String type
    );

    @POST("client/register")
    @FormUrlEncoded
    Call<Register_Response> Register(@FieldMap Map<String, String> querMap);

    @Multipart
    @POST("restaurant/register")
    Call<RestaurantRegister_Response> RestaurantRegister(@Part("name") RequestBody name,
                                                         @Part("email") RequestBody email,
                                                         @Part("password") RequestBody password,
                                                         @Part("password_confirmation") RequestBody confirmpassword,
                                                         @Part("phone") RequestBody phone,
                                                         @Part("address") RequestBody address,
                                                         @Part("whatsapp") RequestBody WhatsApp,
                                                         @Part("region_id") RequestBody region,
                                                         @Part("categories[]") RequestBody category,
                                                         @Part("delivery_period") RequestBody delivery_period,
                                                         @Part("delivery_cost") RequestBody delivery_cost,
                                                         @Part("minimum_charger") RequestBody minimum_charger,
                                                         @Part("availability") RequestBody availability,
                                                         @Part MultipartBody.Part imageFiles);


    @GET("restaurants")
    Call<RestaurantsList_Response> getRestaurantlist(@Query("page") int page);

    @GET("restaurant")
    Call<RestaurantDetails_Response> getRestaurantDetails(@Query("restaurant_id") int id);

    @GET("cities")
    Call<CityList_Response> getCityList();

    @GET("categories")
    Call<Category_Response> getCategories();

    @GET("regions")
    Call<Region_Response> getRegionList(@Query("city_id") int id);

    @GET("items?")
    Call<RestaurantItems_Response> getRestaurantItems(@Query("restaurant_id") int id);

    @GET("restaurant/my-items?")
    Call<MyItems_Response> getMenuItems(@Query("api_token") String api_token);

    @Multipart
    @POST("restaurant/new-item?")
    Call<AddProduct_Response> AddNewProduct(@Part("name") RequestBody name,
                                            @Part("api_token") RequestBody api_token,
                                            @Part("description") RequestBody descripetion,
                                            @Part("price") RequestBody price,
                                            @Part("preparing_time") RequestBody preparingtime,
                                            // @Part MultipartBody.Part imageFiles
                                            @Part("photo") RequestBody photo
    );

    @Multipart
    @POST("restaurant/new-offer?")
    Call<AddNewOffer_Response> AddNewOffer(@Part("name") RequestBody name,
                                           @Part("api_token") RequestBody api_token,
                                           @Part("description") RequestBody descripetion,
                                           @Part("price") RequestBody price,
                                           @Part("starting_at") RequestBody startin,
                                           @Part("ending_at") RequestBody endin,
                                           @Part("photo") RequestBody photo);

    @GET("offers")
    Call<OffersLists_Response> getOffersList();

    @GET("client/my-orders?")
    Call<MyOrders_Response> getOrderssList(@Query("api_token") String api_token);

    @GET("restaurant/my-orders?")
    Call<MyOrdersRestaurant_Response> getrestaurantOrderssList(@Query("api_token") String api_token, @Query("state") String state);

    @GET("restaurant/notifications?api_token=EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv")
    Call<Notification_Response> getNotificationList();

    @GET("restaurant/my-offers?api_token=EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv")
    Call<MyOffers_Response> getMyOffersList();

    @GET("client/notifications?api_token=K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh")
    Call<UserNotification_Response> getUserNotificationList();

    @GET("restaurant/commissions?api_token=EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv")
    Call<Comission_Response> getComissions();

    @GET("restaurant/reviews?")
    Call<RestaurantReview_Response> getRestaurantReviews(@Query("restaurant_id") int id, @Query("api_token") String api_token);

    @POST("client/confirm-order")
    @FormUrlEncoded
    Call<ConfirmOrder_Response> confirmOrder(@Field("order_id") int orderid, @Field("api_token") String apitoken

    );

    @POST("client/decline-order?api_token=K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh")
    @FormUrlEncoded
    Call<DeclineOrder_Response> declineOrder(@Field("order_id") int orderid, @Field("api_token") String apitoken

    );

    @POST("restaurant/accept-order?")
    @FormUrlEncoded
    Call<RestAcceptOrder_Response> acceptOrder(@Field("order_id") int orderid, @Field("api_token") String apitoken

    );

    @POST("restaurant/reject-order?")
    @FormUrlEncoded
    Call<RegectOrder_Response> RejectOrder(@Field("order_id") int orderid, @Field("api_token") String apitoken

    );

    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<RestConfirmOrder_Response> RestConfirmOrder(@Field("order_id") int orderid, @Field("api_token") String apitoken

    );

    @GET("payment-methods")
    Call<PaymentMethod_Response> getpaymentmethods();

    @POST("client/new-order")
    @FormUrlEncoded
    Call<AddOrder_Response> addNewOrder(@Field("name") String name,
                                        @Field("note") String note,
                                        @Field("phone") String phone,
                                        @Field("payment_method_id") int paymentmethodid,
                                        @Field("address") String address,
                                        @Field("restaurant_id") int restaurantid,
                                        @Field("api_token") String apitoken,
                                        @Field("items[][note]") String itemnote,
                                        @Field("items[][item_id]") int itemid,
                                        @Field("items[][quantity]") int itemquentity
    );

    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<AddReviw_Response> addReview(@Field("rate") float rate, @Field("comment") String comment,
                                      @Field("restaurant_id") int restaurantid,
                                      @Field("api_token") String apitoken
    );

    @POST("restaurant/profile")
    @FormUrlEncoded
    Call<EditRestaurantInfoResponse> EditRestaurantInfo(@Field("address") String address,
                                                        @Field("region_id") int regionid,
                                                        @Field("delivery_cost") String deliverycost
                                                        , @Field("minimum_charger") String minimumcharger,
                                                        @Field("availability") String availability,
                                                        @Field("api_token") String apitoken
    );

    @POST("restaurant/change-state")
    @FormUrlEncoded
    Call<ChangeState_Response> changeState(@Field("state") String address,
                                           @Field("api_token") String apitoken
    );
}
