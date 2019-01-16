package com.example.dell.sofra.Restaurants_Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Client_Fragments.RestaurantDetails_fragment;
import com.example.dell.sofra.Model.Category_Response;
import com.example.dell.sofra.Model.RestaurantRegister_Response;
import com.example.dell.sofra.Presenter.RestaurantRegister_Presenter;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Helper.UploadImageToServer;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;
import static com.example.dell.sofra.Register_Fragment.region_response;
import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;
import static com.example.dell.sofra.Helper.UploadImageToServer.convertToRequestBody;
import static com.example.dell.sofra.Helper.UploadImageToServer.getImageToUpload;


/**
 * A simple {@link Fragment} subclass.
 */
public class FollowRegister_Fragment extends Fragment {
    Button login;
    View view;
    public static final int GET_FROM_GALLERY = 3;
    RestaurantRegister_Presenter restaurantRegister_presenter;
    SharedPereferenceClass sharedPereferenceClass;
    @BindView(R.id.minimumcharge)
    EditText minimumcharge;
    @BindView(R.id.deliverycost)
    EditText deliverycost;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.watsapp)
    EditText watsapp;
    ImageView restaurantphoto;
    Unbinder unbinder;
    String email;
    String password;
    String confirmpassword;
    String name;
    String city;
    String region;
    @BindView(R.id.categories)
    Spinner categoriesspinner;
    //    @BindView(R.id.available)
//    EditText available;
    Uri selectedImage;
    String Path;
    @BindView(R.id.deliveryperiod)
    EditText deliveryperiod;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    @BindView(R.id.switch1)
    Switch switch1;
    //    @BindView(R.id.categoriesbutton)
//    Button categoriesbutton;
    private String wats_app;
    Bitmap bitmap;
    String m_minimumcharge;
    String delivery_cost;
    String categoriesss;
    String delivery_period;
    String availability;
    ArrayList<AlbumFile> ImagesFiles;
    UploadImageToServer uploadImageToServer;
    private Category_Response category_response;
    AlertDialog.Builder builder;
    ArrayList<String> categorynamename;

    public FollowRegister_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_follow_register_, container, false);
        unbinder = ButterKnife.bind(this, view);
        builder = new AlertDialog.Builder(getContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getContext().getPackageName()));
            startActivity(intent);
        }
        login = view.findViewById(R.id.login_btn);
        restaurantphoto = view.findViewById(R.id.restaurantphoto);
        uploadImageToServer = new UploadImageToServer();
        sharedPereferenceClass = new SharedPereferenceClass();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            email = bundle.getString("email");
            password = bundle.getString("password");
            confirmpassword = bundle.getString("confirmpassword");
            city = bundle.getString("city");
            region = bundle.getString("region");
        }

        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    simpleProgressBar.setVisibility(View.VISIBLE);
                    m_minimumcharge = minimumcharge.getText().toString();
                    delivery_cost = deliverycost.getText().toString();
                    wats_app = watsapp.getText().toString();
                    String _phone = phone.getText().toString();
                    categoriesss = categoriesspinner.getSelectedItem().toString();
                    delivery_period = deliveryperiod.getText().toString();
                    RestaurantRegister(name, email, password, confirmpassword, _phone, city, region,
                            wats_app, categoriesss, delivery_period, delivery_cost, m_minimumcharge, availability, Path);
                }
            });
        }
//        categoriesbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //   gecategory();
//              //  getcat();
//            }
//        });
        openGallary();
        categorySpinner();
        //  getcategory();
        // categorySpinnerssdsd();
        return view;
    }

    public void RestaurantRegister(String restaurntname, String email, String password, String confirmpassword, String phone
            , String reginid, String address, String watsapp, String categories, String delivery_period, String delivery_cost,
                                   String minimum_charger
            , String availability, String path) {

        int categories_Id = 0;

        for (int i = 0; i < category_response.getData().size(); i++) {
            if (category_response.getData().get(i).getName().equals(categories)) {
                categories_Id = category_response.getData().get(i).getId();
                break;
            }
        }

        int region_id = 0;
        for (int i = 0; i < region_response.getData().getData().size(); i++) {
            if (region_response.getData().getData().get(i).getName().equals(reginid)) {
                region_id = region_response.getData().getData().get(i).getId();
                break;
            }
        }
        path = uploadImageToServer.getRealPathFromURIPath(selectedImage, getContext());
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<RestaurantRegister_Response> call = apiInterface.RestaurantRegister(convertToRequestBody(restaurntname)
                , convertToRequestBody(email), convertToRequestBody(password), convertToRequestBody(confirmpassword)
                , convertToRequestBody(phone), convertToRequestBody(address), convertToRequestBody(watsapp)
                , convertToRequestBody(String.valueOf(region_id)), convertToRequestBody(String.valueOf(categories_Id)), convertToRequestBody(delivery_period)
                , convertToRequestBody(delivery_cost), convertToRequestBody(minimum_charger)
                , convertToRequestBody(availability), getImageToUpload(path, "photo"));
        call.enqueue(new Callback<RestaurantRegister_Response>() {
            @Override
            public void onResponse(Call<RestaurantRegister_Response> call, Response<RestaurantRegister_Response> response) {
                RestaurantRegister_Response restaurantRegister_response = response.body();
                if (restaurantRegister_response != null) {
                    Log.i("myrespondmessage", restaurantRegister_response.getMsg());
                    int myrestaurant_id = restaurantRegister_response.getData().getData().getId();
                    sharedPereferenceClass.storeKey(getContext(), "MYRESTAURANTID", myrestaurant_id);
                    saveRestaurantRegistered();
                    Toast.makeText(getContext(), "register successfully", Toast.LENGTH_SHORT).show();
                    sharedPereferenceClass.storeKey(getContext(), "restaurant_islogged", "true");
                }
            }

            @Override
            public void onFailure(Call<RestaurantRegister_Response> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void openGallary() {
        restaurantphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addImage();
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI), 0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                selectedImage = data.getData();
                Path = uploadImageToServer.getRealPathFromURIPath(selectedImage, getContext());
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                restaurantphoto.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getContext(), "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void saveRestaurantRegistered() {
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTNAME", name);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANT_email", email);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANT_password", password);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTCATEGORY", categoriesss);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTMINIMAMCHARGE", m_minimumcharge);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTDELIVERYCOST", delivery_cost);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTCITY", city);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTADDRESS", region);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANTAVAILABILITY", availability);
        sharedPereferenceClass.storeKey(getContext(), "RESTAURANT_IMAGE", String.valueOf(selectedImage));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.f2Content, new RestaurantDetails_fragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void categorySpinner() {
        Api_interface apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<Category_Response> call = apiInterface.getCategories();
        call.enqueue(new Callback<Category_Response>() {
            @Override
            public void onResponse(Call<Category_Response> call, Response<Category_Response> response) {
                category_response = response.body();
                categorynamename = new ArrayList<>();
                final ArrayList<Integer> categoryidid = new ArrayList<>();
                categorynamename.add("اختر.... ");
                for (int i = 0; i < category_response.getData().size(); i++) {
                    categorynamename.add(category_response.getData().get(i).getName());
                    Log.i("City", category_response.getData().get(i).getName());
                    categoryidid.add(category_response.getData().get(i).getId());
                }
                categoriesspinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, categorynamename));
                categoriesspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long Id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Category_Response> call, Throwable t) {
            }
        });

    }

    @OnClick(R.id.switch1)
    public void onViewClicked() {
        if (switch1.isChecked()) {
            availability = "open";
        } else {
            availability = "close";

        }
    }


//    private void gecategory() {
//
//        // String array for alert dialog multi choice items
////                String[] colors = new String[]{
////                        "Red",
////                        "Green",
////                        "Blue",
////                        "Purple",
////                        "Olive"
////                };
////
////                // Boolean array for initial selected items
////                final boolean[] checkedColors = new boolean[]{
////                        false, // Red
////                        true, // Green
////                        false, // Blue
////                        true, // Purple
////                        false // Olive
////
////                };
//      //  String cattegoryarr;
//        String[] cattegoryarr = new String[category_response.getData().size()];
//        final List<Boolean> list = new ArrayList<Boolean>(Arrays.asList(new Boolean[category_response.getData().size()]));
//        final boolean[] arr = new boolean[category_response.getData().size()];
//        Collections.fill(list, Boolean.FALSE);
//
//        // Convert the color array to list
//        //   final List<String> colorsList = Arrays.asList(colors);
//        builder.setMultiChoiceItems(cattegoryarr, arr, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//
//                // Update the current focused item's checked status
//                arr[which] = isChecked;
//
//                // Get the current focused item
//                String currentItem = categorynamename.get(which);
//
//                // Notify the current action
//                Toast.makeText(getContext(),
//                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Specify the dialog is not cancelable
//        builder.setCancelable(false);
//
//        // Set a title for alert dialog
//        builder.setTitle("Your preferred colors?");
//
//        // Set the positive/yes button click listener
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Do something when click positive button
////                        tv.setText("Your preferred colors..... \n");
////                        for (int i = 0; i < checkedColors.length; i++) {
////                            boolean checked = checkedColors[i];
////                            if (checked) {
////                                tv.setText(tv.getText() + colorsList.get(i) + "\n");
////                            }
//                // }
//            }
//        });
//
//        // Set the negative/no button click listener
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Do something when click the negative button
//            }
//        });
//
//        // Set the neutral/cancel button click listener
//        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Do something when click the neutral button
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        // Display the alert dialog on interface
//        dialog.show();
//    }

}
