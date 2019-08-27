package com.example.yunihafsari.fypversion3.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yunihafsari on 15/05/2017.
 */

public class Validation {

    public Validation(){

    }

    public boolean email_validation(String email){
        boolean status = false;

        //String email_pattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        String email_pattern =   "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches()){
            status = true;
        }else{
            status = false;
        }

        return status;
    }

    public boolean phone_number_validation(String phone_number){
        boolean status = false;
        Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
        Matcher matcher = pattern.matcher(phone_number);

        if(matcher.matches()){
            status = true;
        }else{
            status = false;
        }

        return status;
    }


}
