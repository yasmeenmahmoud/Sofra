package com.example.dell.sofra.Restaurants_Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.sofra.Helper.UploadImageToServer;
import com.example.dell.sofra.Model.AddProduct_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;
import static com.example.dell.sofra.Helper.UploadImageToServer.convertToRequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewProduct_Fragment extends Fragment {
    @BindView(R.id.productname)
    EditText productname;
    @BindView(R.id.productdetails)
    EditText productdetails;
    @BindView(R.id.productprice)
    EditText productprice;
    @BindView(R.id.productpreparingtime)
    EditText productpreparingtime;
    @BindView(R.id.productimage)
    ImageView productimage;
    @BindView(R.id.addproduct)
    Button addproduct;
    Unbinder unbinder;
    String mediaPath;
    Uri selectedImage;
    private final int IMG_REQUST = 1;
    Bitmap bitmap;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    private Api_interface apiInterface;
    String product_Name;
    String product_price;
    String product_details;
    String product_preparing;
    UploadImageToServer uploadImageToServer;

    public AddNewProduct_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_product_, container, false);
        unbinder = ButterKnife.bind(this, view);
        uploadImageToServer = new UploadImageToServer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getContext().getPackageName()));
            startActivity(intent);
        }
        apiInterface = Api_Client.getClient().create(Api_interface.class);
        openGallary();
        addNewProdct();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addNewProdct() {
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_Name = productname.getText().toString();
                product_price = productprice.getText().toString();
                product_details = productdetails.getText().toString();
                product_preparing = productpreparingtime.getText().toString();
                addProductss(product_Name, product_details, product_price, product_preparing, mediaPath);
                simpleProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void openGallary() {
        productimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);

            }
        });
    }

    public void addProductss(String ProductName, String descripe, String Product_price, String Product_preparingtime, String path) {
        File file = new File(path);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        Call<AddProduct_Response> call = apiInterface.AddNewProduct(convertToRequestBody(ProductName)
                , convertToRequestBody("LcqskfJ0UNJLGx1MdQCMkKqUlBkVvBw26HY1aUhOitu5TGh0gCUEBa0XaVm6")
                , convertToRequestBody(descripe), convertToRequestBody(Product_price), convertToRequestBody(Product_preparingtime)
                , mFile);

        call.enqueue(new Callback<AddProduct_Response>() {
            @Override
            public void onResponse(Call<AddProduct_Response> call, Response<AddProduct_Response> response) {
                AddProduct_Response addProduct_response = response.body();
                if (addProduct_response != null) {
                    Log.i("myrespondmessage", addProduct_response.getMsg());
                    simpleProgressBar.setVisibility(View.GONE);
                }
                Toast.makeText(getContext(), "product added successfullly", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AddProduct_Response> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        TheMainActivity theMainActivity=(TheMainActivity)getActivity();
        theMainActivity.getSupportActionBar().setTitle("اضف منتج جديدا");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "Permission has been denied");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                selectedImage = data.getData();
                mediaPath = uploadImageToServer.getRealPathFromURIPath(selectedImage, getContext());
                Toast.makeText(getContext(), mediaPath, Toast.LENGTH_SHORT).show();
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                productimage.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getContext(), "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

}