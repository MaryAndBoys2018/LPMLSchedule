package com.example.lpmlschedule.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.lpmlschedule.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * TODO add logging in with user and password, preferable with registration and firestore representation
 */
public class LoginActivity extends AppCompatActivity {
    private static final int GSIn_REQUEST_CODE = 5689;
    SignInButton googleSignInButton;
    FirebaseAuth auth;
    private static final String TAG = "LOGIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        googleSignInButton = findViewById(R.id.button_google_sign_in);
        googleSignInButton.setOnClickListener( v -> {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestProfile()
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .build();  // Створюємо опції для створення Google SignIn Client
            GoogleSignInClient signInClient = GoogleSignIn.getClient(this,gso); // створюємо клієнт з опцій
            Intent signInIntent = signInClient.getSignInIntent(); // дійстаємо інтент на зовнішню актівіті для логіну через гугєл
            startActivityForResult(signInIntent,GSIn_REQUEST_CODE); // запускаємо цю актівіті щоб отримати з неї результат (залогіненого юзера)
        });
    }


    /**
     * This function is the same for all startActivityForResult() in this activity, so check request codes to avoid exceptions and crushes
     * @param requestCode   code of request when launching activity for result
     * @param resultCode    code of result (success or error)
     * @param data    intent containing data that we requested
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        auth = FirebaseAuth.getInstance(); // ініціалізуємо FirebaseAuth
        if (requestCode == GSIn_REQUEST_CODE) { // звіряємо чи requestCode співпадає із requestCode для логіну через гугєл
            GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener(googleSignInAccount -> { //дістаємо юзера з інтенту який нам прийшов
                // (не забуваємо що основна функція інтентів - обмін даними мі актівіті)
                AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null); //дістаємо credential із акаунту для того щоб передати дані про юзера фаєрбейзу
                auth.signInWithCredential(credential)
                        .addOnSuccessListener(this, this::updateUi)
                        .addOnFailureListener(e -> Log.e(TAG, "FAIL", e)); //логінимося і хендлимо ерор
            }).addOnFailureListener(e -> Log.e(TAG, "FAIL", e)); // хендлимо ерор при діставанні юзера з інтенту (зазвичай старі гугл плей сервіси)
        }
    }
    /**
        Function for processing final user info (same for different login options)
        TODO handle adding new user to database
    */
    private void updateUi(AuthResult info) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}