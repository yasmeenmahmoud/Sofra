package com.example.dell.sofra.Restaurants_Fragments;


import android.Manifest;
import android.app.DatePickerDialog;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dell.sofra.Helper.UploadImageToServer;
import com.example.dell.sofra.Model.AddNewOffer_Response;
import com.example.dell.sofra.R;
import com.example.dell.sofra.Retrofit.Api_Client;
import com.example.dell.sofra.Retrofit.Api_interface;

import java.io.File;
import java.util.Calendar;

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
public class AddNewOffer_Fragment extends Fragment {
    View view;
    EditText timefrom, timeto;
    @BindView(R.id.offername)
    EditText offername;
    @BindView(R.id.offerdetails)
    EditText offerdetails;
    @BindView(R.id.offerprice)
    EditText offerprice;
    @BindView(R.id.offerimage)
    ImageView offerimage;
    @BindView(R.id.addoffer)
    Button addoffer;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    private Api_interface apiInterface;
    Unbinder unbinder;
    String offer_Name;
    String offer_price;
    String offer_details;
    String startin;
    String endat;
    String mediaPath;
    Uri selectedImage;
    Bitmap bitmap;
    UploadImageToServer uploadImageToServer;

    public AddNewOffer_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_offer_, container, false);
        timefrom = view.findViewById(R.id.timefrom_edt);
        timefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcalender();
            }
        });
        timeto = view.findViewById(R.id.timeto_edt);
        timeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showcalendertimeto();
            }
        });
        uploadImageToServer = new UploadImageToServer();
        unbinder = ButterKnife.bind(this, view);
        //permi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getContext().getPackageName()));
            startActivity(intent);
        }
        openGallary();
        addOffer();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TheMainActivity theMainActivity=(TheMainActivity)getActivity();
        theMainActivity.getSupportActionBar().setTitle("اضف عرضا جديدا");
    }

    public void showcalender() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =
                        String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth);
                timefrom.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }

    public void showcalendertimeto() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =
                        String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth);
                timeto.setText(date);
            }
        }, yy, mm, dd);
        datePicker.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void addOffer() {
        addoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer_Name = offername.getText().toString();
                offer_price = offerprice.getText().toString();
                offer_details = offerdetails.getText().toString();
                startin = timefrom.getText().toString();
                endat = timeto.getText().toString();
                simpleProgressBar.setVisibility(View.VISIBLE);
                addNewOffer(offer_Name, offer_details, offer_price, startin, endat, mediaPath);
            }
        });
    }

    private void openGallary() {
        offerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);

            }
        });
    }

    public void addNewOffer(String offerName, String offer_descripe, String offer_price, String statring_at, String ending_at, String path) {
        File file = new File(path);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        apiInterface = Api_Client.getClient().create(Api_interface.class);
        Call<AddNewOffer_Response> call = apiInterface.AddNewOffer(convertToRequestBody(offerName)
                , convertToRequestBody("EuqQtEiKiG4OfshU49UltxUnvySicD3T1eW4BBjdjIlMqyGJPlYauzTOH0lv")
                , convertToRequestBody(offer_descripe), convertToRequestBody(offer_price), convertToRequestBody(statring_at),
                convertToRequestBody(ending_at), mFile);

        call.enqueue(new Callback<AddNewOffer_Response>() {
            @Override
            public void onResponse(Call<AddNewOffer_Response> call, Response<AddNewOffer_Response> response) {
                AddNewOffer_Response addNewOffer_response = response.body();
                if (addNewOffer_response != null) {
                    Log.i("myofferrespondmessage", addNewOffer_response.getMsg());
                    Toast.makeText(getContext(), addNewOffer_response.getMsg(), Toast.LENGTH_SHORT).show();
simpleProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AddNewOffer_Response> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                offerimage.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getContext(), "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

}
