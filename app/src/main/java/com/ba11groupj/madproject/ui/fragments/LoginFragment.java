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

import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.helpers.DBHelper;
import com.ba11groupj.madproject.ui.activities.MainFormActivity;
import com.ba11groupj.madproject.utils.UserUtils;

public class LoginFragment extends Fragment {

    EditText fldUsername, fldPassword;
    Button btnLogin;
    View view;

    DBHelper database;
    UserUtils userUtils;

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

        view = inflater.inflate(R.layout.fragment_login, container, false);
        database = new DBHelper(this.getContext());
        userUtils = new UserUtils();

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
                } else if (!username.equals("resetdb") && !password.equals("resetdb") && !userUtils.checkIfRegistered(username, password, database)) {
                    Toast.makeText(getActivity(), "Username and password not yet registered!", Toast.LENGTH_SHORT).show();
                } else {
                    if (!username.equals("resetdb") && !password.equals("resetdb")) {
                        Intent intent = new Intent(getActivity(), MainFormActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("userId", userUtils.lookupUserId(username, password, database));
                        intent.putExtras(bundle);

                        startActivity(intent);
                    } else if (username.equals("resetdb") && password.equals("resetdb")) {
                        // utk testing
                        database.resetUserTable();
                        Toast.makeText(getActivity(), "User Table Successfully Reset!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
}
