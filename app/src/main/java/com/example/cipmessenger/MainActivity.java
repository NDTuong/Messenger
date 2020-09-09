package com.example.cipmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        FloatingActionButton btnNewMess = findViewById(R.id.new_mess);
        TextView tvNotVerified = findViewById(R.id.tv_notverified);
        Button btnVerify = findViewById(R.id.btn_resendcode);
        Button btnLogOut = findViewById(R.id.btn_logout);

        // Check xác thực mail chưa?
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user.isEmailVerified()) {
            btnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), MainActivity.class));
                }
            });
        }
        if (!user.isEmailVerified()) {
            btnVerify.setVisibility(View.VISIBLE);
            tvNotVerified.setVisibility(View.VISIBLE);
            btnNewMess.setVisibility(View.INVISIBLE);

            btnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Verification Email Has Been Sent.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "Email not sent." + e.getMessage());
                        }
                    });
                }
            });

            btnNewMess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(v.getContext(), SendMess.class));
                }
            });
        }
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(v.getContext(), Login.class));
            }
        });

        //Làm việc với Database
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("User");
    }
}


//
//        btnNewMess.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SendMess.class);
//                startActivity(intent);
//            }
//        });

//        SearchView searchView = (SearchView)findViewById(R.id.searchView);
//        searchView.setOnClickListener(new SearchView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Search.class);
//                startActivity(intent);
//            }
//        });

//
//        btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(MainActivity.this, Login.class);
//                startActivity(intent);
//            }
//        });

//            String[] myDataset = {"Tuong1","Tuong2","Tuong3","Tuong4","Tuong5"};
//        listView = findViewById(R.id.list_message);
//
//        MessageAdapter adapter = new MessageAdapter(this, items);
//        listView.setAdapter(adapter);
//  }

//    public void Logout(View view) {
//        FirebaseAuth.getInstance().signOut(); //Đăng xuất
//        startActivity(new Intent(getApplicationContext(),Login.class));
//        finish();