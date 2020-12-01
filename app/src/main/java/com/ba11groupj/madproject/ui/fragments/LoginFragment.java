package com.ba11groupj.madproject.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ba11groupj.madproject.ui.activities.MainFormActivity;
import com.ba11groupj.madproject.helpers.DataHelper;
import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.models.User;

public class LoginFragment extends Fragment {

    EditText fldUsername, fldPassword;
    Button btnLogin;
    View view;

    void init() {
        fldUsername = view.findViewById(R.id.txtLoginUsername);
        fldPassword = view.findViewById(R.id.txtLoginPass);
        btnLogin = view.findViewById(R.id.btnLogin);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login,container, false);

        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = fldUsername.getText().toString().trim();
                String password = fldPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(getActivity(), "Username must be filled in!", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Password must be filled in!", Toast.LENGTH_SHORT).show();
                } else if (!checkIfRegistered(username, password)) {
                    Toast.makeText(getActivity(), "Username and password not yet registered!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), MainFormActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("userId", getUserId(username, password));
                    intent.putExtras(bundle);

                    startActivity(intent);
                }
            }
        });

        return view;
    }

    private boolean checkIfRegistered(String username, String password) {
        for (User u : DataHelper.arrUser) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private String getUserId(String username, String password) {
        for (User u : DataHelper.arrUser) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u.getUserId();
            }
        }
        return null;
    }
}
