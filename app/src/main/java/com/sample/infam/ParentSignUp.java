package com.sample.infam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.sample.infam.model.ParentModel;

public class ParentSignUp extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 10;
    private EditText email;
    private EditText name;
    private EditText password;
    private Button signup;
    private String splash_background;
    private ImageView profile;
    private Uri imageUri; //업로드 할 때  값을 담아준다.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_sign_up);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        splash_background = mFirebaseRemoteConfig.getString(getString(R.string.rc_color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //status바
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }

        profile = (ImageView) findViewById(R.id.signup_imageview);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        email = (EditText) findViewById(R.id.signup_edt_email);
        name = (EditText) findViewById(R.id.signup_edt_name);
        password = (EditText) findViewById(R.id.signup_edt_password);

        signup = (Button) findViewById(R.id.signup_btn_signup);
        signup.setBackgroundColor(Color.parseColor(splash_background));

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString() == null || name.getText().toString() == null || password.getText().toString() == null) {
                    return;
                }

                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(ParentSignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) { //회원가입 완료시
                                final String uid = task.getResult().getUser().getUid();

                                FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        @SuppressWarnings("VisibleForTests")
                                        Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                                        while(!imageUrl.isComplete());

                                        ParentModel parModel = new ParentModel();
                                        parModel.ParentName = name.getText().toString();
                                        parModel.profileImageUrlp = imageUrl.getResult().toString();

                                        FirebaseDatabase.getInstance().getReference().child("parentsId").child(uid).setValue(parModel);


                                    }
                                });

                            }
                        });


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            profile.setImageURI(data.getData()); // 가운데 뷰를 바꿈
            imageUri = data.getData();// 이미지 경로 원본
        }
    }
}