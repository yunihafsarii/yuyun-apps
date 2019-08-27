package com.example.yunihafsari.fypversion3.ui.fragments.main_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.MainActivity;
import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.firebase.login.LoginContract;
import com.example.yunihafsari.fypversion3.firebase.login.LoginPresenter;
import com.example.yunihafsari.fypversion3.ui.activities.RegisterActivity;
import com.example.yunihafsari.fypversion3.utils.Validation;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, LoginContract.View{

    private LoginPresenter loginPresenter;

    private EditText email, password;
    private Button login, register;

    private ProgressDialog progressDialog;

    public static LoginFragment newInstance(){
        Bundle args = new Bundle();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(args);
        return loginFragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);

        email = (EditText) fragmentView.findViewById(R.id.edit_text_email_id);
        password = (EditText) fragmentView.findViewById(R.id.edit_text_password);
        login = (Button) fragmentView.findViewById(R.id.button_login);
        register = (Button) fragmentView.findViewById(R.id.button_register);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginPresenter = new LoginPresenter(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        // set dummy credential
        //email.setText("test@test.com");
        //password.setText("123456");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId){
            case R.id.button_login:
                onLogin(v);
                break;
            case R.id.button_register:
                onRegister(v);
                break;
        }
    }

    private void onRegister(View v) {
        RegisterActivity.startActivity(getActivity());
    }

    private void onLogin(View v) {
        String email2 = email.getText().toString();
        String password2 = password.getText().toString();

        /***********************************************************************************************************
         * this is validation
         * 1. must fill in all the field
         * 2. validation for email - must follow email format
         * 3. validation for password - no validation (only required validation in website mostly)
         * **********************************************************************************************************/

        if(input_validation(email2)){
            // allow user to login
            loginPresenter.Login(getActivity(), email2, password2);
            progressDialog.show();

        }



    }

    private boolean input_validation(String email_input){
        boolean status = false;

        Validation validation = new Validation();
        boolean email_validation = validation.email_validation(email_input);

        if(email.getText().toString().length()==0 && password.getText().toString().length()==0){
            email.setError("email and password not entered");
            email.requestFocus();
            password.setError("email and password not entered");
            password.requestFocus();
            status = false;
        }else if(password.getText().toString().length()==0){
            password.setError("password not entered");
            password.requestFocus();
            status = false;
        }else if(email.getText().toString().length()==0){
            email.setError("email not entered");
            email.requestFocus();
        }else if(!email_validation){
            email.setError("Invalid email");
            email.requestFocus();
            status = false;
        }else{
            status = true;
        }

        return status;
    }

    @Override
    public void onLoginSuccess(String message) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();
        // this one will be changed
        //startActivity(new Intent(getActivity(), MainActivity.class));
        MainActivity.startActivity(getActivity(), Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onLoginFailure(String message) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), "ERROR "+message, Toast.LENGTH_SHORT).show();
    }

}
