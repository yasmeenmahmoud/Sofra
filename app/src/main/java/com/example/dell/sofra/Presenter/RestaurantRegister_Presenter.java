package com.example.dell.sofra.Presenter;


public class RestaurantRegister_Presenter {

//    UserRegister_View userRegister_view;
//    Context context;
//
//    public RestaurantRegister_Presenter(Context context, UserRegister_View userRegister_view) {
//        this.userRegister_view = userRegister_view;
//        this.context = context;
//    }

//    public void RestaurantRegister(String restaurntname, String email, String password, String confirmpassword, String phone, String reginid, String address
//            , String categories, String delivery_period, String delivery_cost, String minimum_charger, String availability
//    ) {
//
//        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
//        Call<RestaurantRegister_Response> call = apiInterface.RestaurantRegister(convertToRequestBody(restaurntname), convertToRequestBody(confirmpassword)
//                , convertToRequestBody(email), convertToRequestBody(phone), convertToRequestBody(password), convertToRequestBody(address)
//                , convertToRequestBody(reginid), convertToRequestBody(categories), convertToRequestBody(delivery_period)
//                , convertToRequestBody(delivery_cost), convertToRequestBody(availability), convertToRequestBody(minimum_charger) , getImageToUpload(Path , "photo"));
//        call.enqueue(new Callback<RestaurantRegister_Response>() {
//            @Override
//            public void onResponse(Call<RestaurantRegister_Response> call, Response<RestaurantRegister_Response> response) {
//                RestaurantRegister_Response restaurantRegister_response = response.body();
//                // try {
//                if (restaurantRegister_response != null) {
////                        Log.i("MyResponse", restaurantRegister_response.getData().getApiToken());
//                    Log.i("myrespondmessage", restaurantRegister_response.getMsg());
//                    //    Log.i("mynamerespondmessage", restaurantRegister_response.getData().getData().getName());
//
//                    userRegister_view.Success();
//                }
//                //  } catch (Exception ex) {
//
//                //  }
//            }
//
//            @Override
//            public void onFailure(Call<RestaurantRegister_Response> call, Throwable t) {
//                userRegister_view.Error();
//            }
//        });
//    }
//    public static MultipartBody.Part getImageToUpload(String pathImageFile , String Key ) {
//
//        File file = new File(pathImageFile);
//
//        RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);
//
//        return Imagebody;
//    }
//
//    public static RequestBody convertToRequestBody(String part) {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
//        return requestBody;
//    }
}
