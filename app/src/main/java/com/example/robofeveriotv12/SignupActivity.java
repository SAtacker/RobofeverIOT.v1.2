package com.example.robofeveriotv12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;


@SuppressLint("Registered")
public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    Button myButton;
    EditText user;
    EditText pass;
    TextView login;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_signup);

        myButton= (Button)findViewById(R.id.button);
        user= (EditText)findViewById(R.id.user);
        pass=(EditText)findViewById(R.id.pass);
        login=(TextView)findViewById(R.id.login);
        progressDialog= new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        /*if(firebaseAuth.getCurrentUser()!=null){
            finish();

            startActivity(new Intent(getApplicationContext(),CoolerActivity.class));

        }*/
        myButton.setOnClickListener(this);
        login.setOnClickListener(this);





    }
    private void registerUser(){
        String email = user.getText().toString().trim();
        String password= pass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT ).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        validateEmailAddress(user);

        if(validateEmailAddress(user)==true){
            progressDialog.setMessage("Registering...");
            progressDialog.show();
        }

        String pass1 = pass.getText().toString();
        if(TextUtils.isEmpty(pass1) || pass1.length() < 6)
        {
            pass.setError("You must have atleast 6 characters in your password");
            return;
        }





        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Registered Successfully", Toast.LENGTH_SHORT).show();

                            finish();
                            startActivity(new Intent(getApplicationContext(),ModeActivity.class));

                        }

                        else{
                            Toast.makeText(SignupActivity.this,"Could not register...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateEmailAddress(EditText user){
        String emailInput = user.getText().toString();

        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(this, "Email Validated Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            return false;

        }

    }


    @Override
    public void onClick(View v) {
        if (v==myButton){
            registerUser();
        }
        if(v==login){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

    }


}



