package max.project.taskmanger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private static final String TAG = "SignInActivity";

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private EditText repeatPasswordEditText;
    private TextView toggleLoginSingUpTextView;
    private Button loginSingUpButton;



    private boolean loginModeActive;

    private FirebaseDatabase database;
    private DatabaseReference usersDatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        usersDatabaseReference = database.getReference().child("users");

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        toggleLoginSingUpTextView = findViewById(R.id.toggleLoginSingUpTextView);
        loginSingUpButton = findViewById(R.id.loginSingUpButton);

        loginSingUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSinUpUser(emailEditText.getText().toString().trim() ,
                        passwordEditText.getText().toString().trim());
            }
        });

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(SignInActivity.this , UserListActivity.class));
        }

    }

    private void loginSinUpUser(String email , String password){

        if(loginModeActive){
            if(passwordEditText.getText().toString().trim().length() < 7){

                Toast.makeText(this , "Пароль не меньше 7 символов" , Toast.LENGTH_SHORT).show();

            }
            else if(emailEditText.getText().toString().trim().equals("")){
                Toast.makeText(this , "Введите мыло" , Toast.LENGTH_SHORT).show();
            }else{

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    Intent intent = new Intent(SignInActivity.this , UserListActivity.class);
                                    intent.putExtra("userName" , nameEditText.getText().toString().trim());
                                    startActivity(intent);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
            }

        }else{

            if(!passwordEditText.getText().toString().trim()
                    .equals(repeatPasswordEditText.getText().toString().trim())){

                Toast.makeText(this , "Пароли не совпдают" , Toast.LENGTH_LONG).show();

            }else if(passwordEditText.getText().toString().trim().length() < 7){

                Toast.makeText(this , "Пароль не меньше 7 символов" , Toast.LENGTH_SHORT).show();

            }
            else if(emailEditText.getText().toString().trim().equals("")){
                Toast.makeText(this , "Введите мыло" , Toast.LENGTH_SHORT).show();
            }else{

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    createUer(user);
                                    Intent intent = new Intent(SignInActivity.this , UserListActivity.class);
                                    intent.putExtra("userName" , nameEditText.getText().toString().trim());
                                    startActivity(intent);
                                    // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
                                }

                                // ...
                            }

                            private void createUer(FirebaseUser firebaseUser) {

                                User user = new User();
                                user.setId(firebaseUser.getUid());
                                user.setEmail(firebaseUser.getEmail());
                                user.setName(nameEditText.getText().toString().trim());
                                usersDatabaseReference.push().setValue(user);
                            }
                        });

            }

        }




    }

    public void toggleLogInMode(View view) {

        if(loginModeActive){

            loginModeActive = false;
            loginSingUpButton.setText("Зарегестрироватся");
            toggleLoginSingUpTextView.setText("Войти");
            repeatPasswordEditText.setVisibility(View.VISIBLE);
            nameEditText.setVisibility(View.VISIBLE);
        }else{
            loginModeActive = true;
            loginSingUpButton.setText("Войти");
            toggleLoginSingUpTextView.setText("Зарегестрироватся");
            repeatPasswordEditText.setVisibility(View.GONE);
            nameEditText.setVisibility(View.GONE);

        }
    }
}