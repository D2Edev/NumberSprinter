package org.home.d2e.numbersprinter;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.home.d2e.numbersprinter.Core.OnFragmentListener;


public class SignUpFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "TAG_SignUpFragment_";
    OnFragmentListener listener;
    private Button btSignUp;
    private EditText etName;
    private EditText etEmail;
    private EditText etPass;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btSignUp = (Button) view.findViewById(R.id.btSignup);
        etName= (EditText) view.findViewById(R.id.etName);
        etPass= (EditText) view.findViewById(R.id.etPass);
        etEmail= (EditText) view.findViewById(R.id.etEmail);
        btSignUp.setOnClickListener(SignUpFragment.this);
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.btSignup:
        validateCredentials(v);
        break;
    default:
        break;
}
    }

    private void validateCredentials(View v) {
        Editable name = etName.getText();
        Editable pass = etPass.getText();
        Editable email = etEmail.getText();

        if (TextUtils.isEmpty(name)){

            Toast.makeText(v.getContext(),getString(R.string.tEmptyName),Toast.LENGTH_SHORT).show();
            etName.setError(getString(R.string.tError));

        }else if (TextUtils.isEmpty(pass)){

            Toast.makeText(v.getContext(),getString(R.string.tEmptyPass),Toast.LENGTH_SHORT).show();
            etPass.setError(getString(R.string.tError));

        }else if(pass.length()<6){

            Toast.makeText(v.getContext(),getString(R.string.tShortPass),Toast.LENGTH_SHORT).show();
            etPass.setError(getString(R.string.tError));

        }else if (TextUtils.isEmpty(email)){

            Toast.makeText(v.getContext(),getString(R.string.tEmptyEmail),Toast.LENGTH_SHORT).show();
            etEmail.setError(getString(R.string.tError));

        }else{

            Toast.makeText(v.getContext(),getString(R.string.btnSignup)+ " "+ getString(R.string.tOK),Toast.LENGTH_SHORT).show();
            listener.startLoginFragment();

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnFragmentListener)activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }



}
