package com.example.dell.sofra;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.sofra.Client_Fragments.AddNewOrder_Fragment;
import com.example.dell.sofra.Client_Fragments.Home;
import com.example.dell.sofra.Client_Fragments.RestaurantDetails_fragment;
import com.example.dell.sofra.Helper.SharedPereferenceClass;
import com.example.dell.sofra.Model.LoginData_Model;
import com.example.dell.sofra.Model.Login_Data;
import com.example.dell.sofra.Model.RestaurantLogin_Datam;
import com.example.dell.sofra.Presenter.UserLogin_Presenter;
import com.example.dell.sofra.Restaurants_Fragments.TheMainActivity;
import com.example.dell.sofra.View.Login_View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static com.example.dell.sofra.Helper.SharedPereferenceClass.API_TOKEN;
import static com.example.dell.sofra.Helper.SharedPereferenceClass.SELL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_Fragment extends Fragment implements Login_View {
    View view;
    Button register, register2, login;
    EditText email, password;
    UserLogin_Presenter userLogin_presenter;
    LoginData_Model loginDataModel;
    Login_Data login_data;
    String token;
    String r_token;
    SharedPereferenceClass sharedPereferenceClass;
    TextView textView;
    @BindView(R.id.simpleProgressBar)
    ProgressBar simpleProgressBar;
    Unbinder unbinder;

    public Login_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in_, container, false);
        login = view.findViewById(R.id.login_btn);
        register = view.findViewById(R.id.register_btn);
        register2 = view.findViewById(R.id.register2_btn);
        email = view.findViewById(R.id.l_email_edt);
        password = view.findViewById(R.id.l_password_edt);
        userLogin_presenter = new UserLogin_Presenter(getContext(), this);
        sharedPereferenceClass = new SharedPereferenceClass();
        loginDataModel = new LoginData_Model();
        login_data = new Login_Data();
        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            register.setVisibility(GONE);
            register2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment2(new Register_Fragment());
                }
            });
            sharedPereferenceClass.getStoredKey(getContext(),"restaurant_islogged");
            restaurantsignIn();

        } else {
            register2.setVisibility(GONE);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(new Register_Fragment());
                }
            });
            signIn();
            sharedPereferenceClass.getStoredKey(getContext(), "clientislogged");

        }
        //put line under textbox
        textView = (TextView) view.findViewById(R.id.forgetpassword);
        textUnderLine();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void Error() {
        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            Toast.makeText(getContext(), "restaurantlogin failed, please signin before", Toast.LENGTH_SHORT).show();
            loadFragment2(new Register_Fragment());
        } else {
            Toast.makeText(getContext(), "login failed, please signin before", Toast.LENGTH_SHORT).show();
            loadFragment(new Register_Fragment());
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            TheMainActivity theMainActivity = (TheMainActivity) getActivity();
        theMainActivity. getSupportActionBar().setTitle("تسجيل الدخول");}
        else { Home home = (Home) getActivity();
            home. getSupportActionBar().setTitle("تسجيل الدخول");}

    }
    @Override
    public void Success() {
        if (sharedPereferenceClass.getStoredKey(getActivity(), SELL).equals(SELL)) {
            Toast.makeText(getContext(), "id " + sharedPereferenceClass.getintStoredKey(getContext(), "MYRESTAURANTID"), Toast.LENGTH_SHORT).show();
            loadFragment2(new RestaurantDetails_fragment());
            sharedPereferenceClass.storeKey(getContext(), "restaurant_islogged", "true");

        } else {
            Toast.makeText(getContext(), "login success", Toast.LENGTH_SHORT).show();
            loadFragment(new AddNewOrder_Fragment());
            sharedPereferenceClass.storeKey(getContext(), "clientislogged", "true");
        }
    }

    @Override
    public void apitoken(LoginData_Model loginData_model) {
        token = loginData_model.getApiToken();
        sharedPereferenceClass.storeKey(getActivity(), API_TOKEN, token);
        Toast.makeText(getContext(), token, Toast.LENGTH_LONG).show();

    }

    @Override
    public void r_apitoken(RestaurantLogin_Datam restaurantLogin_datam) {
        r_token = restaurantLogin_datam.getApiToken();
        sharedPereferenceClass.storeKey(getActivity(), "RESTAURANT_TOKEN", r_token);
        //  Toast.makeText(getContext(), r_token, Toast.LENGTH_LONG).show();

    }

    public void signIn() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_email = email.getText().toString();
                String m_password = password.getText().toString();
                userLogin_presenter.Login(m_email, m_password);
                simpleProgressBar.setVisibility(View.VISIBLE);
                //   }
            }
        });
    }

    public void restaurantsignIn() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m_email = email.getText().toString();
                String m_password = password.getText().toString();
//                if (m_email.equals(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANT_email")) &&
//                        m_password.equals(sharedPereferenceClass.getStoredKey(getContext(), "RESTAURANT_password"))) {
                    userLogin_presenter.RestaurantLogin(m_email, m_password);
                    simpleProgressBar.setVisibility(View.VISIBLE);
               // }

            }
        });

    }

    private void textUnderLine() {
        SpannableString content = new SpannableString("هل نسيت كلمة المرور؟");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }

    public Fragment loadFragment2(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.f2Content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return fragment;

    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
