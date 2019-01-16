package com.example.dell.sofra.View;

import com.example.dell.sofra.Model.LoginData_Model;
import com.example.dell.sofra.Model.Login_Data;
import com.example.dell.sofra.Model.RestaurantLogin_Datam;

public interface Login_View {
    void Error();
    void Success();
    void apitoken(LoginData_Model loginData_model);
    void r_apitoken(RestaurantLogin_Datam restaurantLogin_datam);


}
