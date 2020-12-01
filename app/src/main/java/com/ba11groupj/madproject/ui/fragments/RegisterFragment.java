package com.ba11groupj.madproject.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ba11groupj.madproject.helpers.DBHelper;
import com.ba11groupj.madproject.ui.activities.MainActivity;
import com.ba11groupj.madproject.R;
import com.ba11groupj.madproject.models.User;

public class RegisterFragment extends Fragment {

    EditText fldFullname, fldUsername, fldPassword, fldRePass, fldPhoneNum;
    RadioGroup rdGender;
    Button btnRegister;
    CheckBox chkTerms;

    MainActivity mainAct = ((MainActivity) getActivity());
    DBHelper database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container, false);
        database = new DBHelper(this.getContext());

        fldFullname = view.findViewById(R.id.txtName);
        fldUsername = view.findViewById(R.id.txtRegUsername);
        fldPassword = view.findViewById(R.id.txtRegPassword);
        fldRePass = view.findViewById(R.id.txtConfPass);
        fldPhoneNum = view.findViewById(R.id.txtPhoneNum);
        rdGender = view.findViewById(R.id.rdGender);
        btnRegister = view.findViewById(R.id.btnRegister);
        chkTerms = view.findViewById(R.id.chkTerms);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fldFullname.getText().toString().trim();
                String username = fldUsername.getText().toString().trim();
                String password = fldPassword.getText().toString().trim();
                String confPass = fldRePass.getText().toString().trim();
                String phoneNum = fldPhoneNum.getText().toString().trim();

                if (fullName.isEmpty()) {
                    Toast.makeText(getActivity(), "Name must be filled in!", Toast.LENGTH_SHORT).show();
                } else if (!fullName.contains(" ")) {
                    Toast.makeText(getActivity(), "Full Name must consist of two words!", Toast.LENGTH_LONG).show();
                } else if (username.isEmpty()) {
                    Toast.makeText(getActivity(), "Username must be filled in!", Toast.LENGTH_SHORT).show();
                } else if (isUserRegistered(username)) {
                    Toast.makeText(getActivity(), "Username already registered!", Toast.LENGTH_SHORT).show();
                } else if (username.length() < 5 || username.length() > 25) {
                    Toast.makeText(getActivity(), "Username must be between 5 and 25 characters!", Toast.LENGTH_LONG).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), "Password must be filled in!", Toast.LENGTH_SHORT).show();
                }  else if (!validatePass(password)) {
                    Toast.makeText(getActivity(), "Password must contain uppercase, lowercase, and numeric characters!", Toast.LENGTH_LONG).show();
                } else if (password.length() > 15) {
                    Toast.makeText(getActivity(), "Password must be less than 15 characters!", Toast.LENGTH_SHORT).show();
                } else if (!confPass.equals(password)) {
                    Toast.makeText(getActivity(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                } else if (!phoneNum.startsWith("+62")) {
                    Toast.makeText(getActivity(), "Phone number must start with +62!", Toast.LENGTH_SHORT).show();
                } else if (!validatePhoneNum(phoneNum)) {
                    Toast.makeText(getActivity(), "Phone number is not numeric!", Toast.LENGTH_SHORT).show();
                } else if (phoneNum.length() < 12) {
                    Toast.makeText(getActivity(), "Phone number must be 12 digits or more!", Toast.LENGTH_LONG).show();
                } else if (rdGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(), "Gender must be selected!", Toast.LENGTH_SHORT).show();
                } else if (!chkTerms.isChecked()) {
                    Toast.makeText(getActivity(), "Agreement must be checked!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Successfully registered!", Toast.LENGTH_LONG).show();

                    database.insertNewUser(username, fullName, password, phoneNum, isMale(rdGender), 0);
                    mainAct.getTabHost().setCurrentTab(0);
                }
            }
        });

        return view;
    }

    private boolean isUserRegistered(String username) {
        for (User u : database.fetchUsers()) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private boolean validatePass(String password) {
        boolean containsUpper = false;
        boolean containsSpecial = false;
        boolean containsNum = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                containsUpper = true;
            } else if (!Character.isLetterOrDigit(c)) {
                containsSpecial = true;
            } else if (Character.isDigit(c)) {
                containsNum = true;
            }
        }

        if (containsUpper && containsSpecial && containsNum) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validatePhoneNum(String phoneNum) {
        boolean containsLetter = false;
        int cntCharSpecials = 0;


        for (int i = 0; i < phoneNum.length(); i++) {
            if (Character.isLetter(phoneNum.charAt(i))) {
                containsLetter = true;
            } else if (!Character.isLetterOrDigit(phoneNum.charAt(i))) {
                cntCharSpecials++;
            }
        }

        if (phoneNum.trim().charAt(0) == '+') {
            cntCharSpecials = cntCharSpecials - 1;
        }

        if (containsLetter || cntCharSpecials != 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isMale(RadioGroup radioGroup) {
        if (rdGender.getCheckedRadioButtonId() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
