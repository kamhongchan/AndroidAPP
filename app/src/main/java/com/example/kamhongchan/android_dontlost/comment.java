package com.example.kamhongchan.android_dontlost;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamhongchan on 2017/12/10.
 */

public class comment extends Fragment {
    private static final String TAG = "comment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comment,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("反映與建議");
        Button button = (Button) view.findViewById(R.id.bt_submitcomment);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date dt=new Date();
                String time=sdf.format(dt);

                EditText et_comment = (EditText) view.findViewById(R.id.et_comment);
                EditText et_phone = (EditText) view.findViewById(R.id.et_phone);
                EditText et_email = (EditText) view.findViewById(R.id.et_email);

                String content = et_comment.getText().toString();
                String phone = et_phone.getText().toString();
                String email = et_email.getText().toString();

                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> comment = new HashMap<>();
                comment.put("時間", time);
                comment.put("電話", phone);
                comment.put("電子郵箱",email);
                comment.put("內容", content);


                // Add a new document with a generated ID
                db.collection("comment")
                        .add(comment)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });



            }
        });
    }


}
