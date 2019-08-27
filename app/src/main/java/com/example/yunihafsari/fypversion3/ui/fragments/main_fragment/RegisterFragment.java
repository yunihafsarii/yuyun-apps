package com.example.yunihafsari.fypversion3.ui.fragments.main_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yunihafsari.fypversion3.MainActivity;
import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.firebase.register.RegisterContract;
import com.example.yunihafsari.fypversion3.firebase.register.RegisterPresenter;
import com.example.yunihafsari.fypversion3.firebase.users.add.AddUserContract;
import com.example.yunihafsari.fypversion3.firebase.users.add.AddUserPresenter;
import com.example.yunihafsari.fypversion3.utils.Constants;
import com.example.yunihafsari.fypversion3.utils.SharedPrefUtil;
import com.example.yunihafsari.fypversion3.utils.Validation;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener, RegisterContract.View, AddUserContract.View {

    private static final String TAG = RegisterFragment.class.getSimpleName();

    private RegisterPresenter registerPresenter;
    private AddUserPresenter addUserPresenter;

    private EditText txtEmail, txtPassword, username, phone_number;
    private Button btnRegister;

    private ProgressDialog progressDialog;

    public static RegisterFragment newInstance(){
        Bundle args = new Bundle();
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(args);
        return registerFragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_register, container, false);

        txtEmail = (EditText) fragmentView.findViewById(R.id.edit_text_email_id);
        txtPassword = (EditText) fragmentView.findViewById(R.id.edit_text_password);
        username = (EditText) fragmentView.findViewById(R.id.username);
        phone_number = (EditText) fragmentView.findViewById(R.id.phone_number);
        btnRegister = (Button) fragmentView.findViewById(R.id.button_register);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerPresenter = new RegisterPresenter(this);
        addUserPresenter = new AddUserPresenter(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch(viewId){
            case R.id.button_register:
                onRegister(v);
                break;
        }
    }

    private void onRegister(View v) {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String get_username = username.getText().toString();
        String get_number = phone_number.getText().toString();


        /***********************************************************************************************************
         * this is validation
         * 1. must fill in all the field
         * 2. validation for email - must follow email format
         * 3. validation for password - no validation (only required validation in website mostly)
         * 4. validation for phone number - must number (XXX-XXXXXXX) 3 digit follow by 7 digit
         * 5. validation for user name - no validation
        * **********************************************************************************************************/

        Validation validation = new Validation();
        boolean email_validation = validation.email_validation(email);
        boolean phone_number_validation = validation.phone_number_validation(get_number);

        // validation
        if(txtEmail.getText().toString().length()==0 &&
                txtPassword.getText().toString().length()==0 &&
                username.getText().toString().length() == 0 &&
                phone_number.getText().toString().length() == 0){
            // email
            txtEmail.setError("please fill in all the fields");
            txtEmail.requestFocus();
            // password
            txtPassword.setError("please fill in all the fields");
            txtPassword.requestFocus();
            // username
            username.setError("please fill in all the fields");
            username.requestFocus();
            // phone number
            phone_number.setError("please fill in all the fields");
            phone_number.requestFocus();
        }else if(txtEmail.getText().toString().length()==0){
            txtEmail.setError("email not entered");
            txtEmail.requestFocus();
        }else if(txtPassword.getText().toString().length()==0){
            txtPassword.setError("password not entered");
            txtPassword.requestFocus();
        }else if(username.getText().toString().length()==0){
            username.setError("username not entered");
            username.requestFocus();
        }else if(phone_number.getText().toString().length()==0){
            phone_number.setError("password not entered");
            phone_number.requestFocus();
        }else if(!email_validation){
            txtEmail.setError("Invalid Email");
            txtEmail.requestFocus();
        }else if(!phone_number_validation){
            phone_number.setError("must be XXX-XXXXXXX");
            phone_number.requestFocus();
        }else{
            // validation success
            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getContext());
            sharedPrefUtil.saveString(Constants.USER_USERNAME, get_username);
            sharedPrefUtil.saveString(Constants.PHONE_NUMBER, get_number);

            registerPresenter.register(getActivity(), email, password);
            progressDialog.show();

        }



    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        progressDialog.setMessage(getString(R.string.adding_user_to_db));
        Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
        addUserPresenter.addUser(getActivity().getApplicationContext(), firebaseUser);
    }

    @Override
    public void onRegistrationFailure(String message) {
        progressDialog.dismiss();
        progressDialog.setMessage(getString(R.string.please_wait));
        Log.e(TAG, "registration failed "+message);
        Toast.makeText(getActivity(), "Registration failed "+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addUserSuccess(String message) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        //UserListingActivity.startActivity(getActivity(), Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void addUserFailure(String message) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
