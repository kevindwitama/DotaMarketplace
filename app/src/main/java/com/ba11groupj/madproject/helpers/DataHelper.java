package com.ba11groupj.madproject.helpers;

import com.ba11groupj.madproject.models.Transaction;

import java.util.ArrayList;

// fungsi class ini cuma sebagai hack biar recyclerview di menu history bisa auto refresh

public class   DataHelper {

    public static ArrayList<Transaction> arrTrans = new ArrayList<>();

    public static void updateArray(Transaction trans) {
        for (int i = 0; i < arrTrans.size(); i++) {
            if (arrTrans.get(i).getUserId() == (trans.getUserId())) {
                arrTrans.set(i, trans);
            }
        }
    }
}

