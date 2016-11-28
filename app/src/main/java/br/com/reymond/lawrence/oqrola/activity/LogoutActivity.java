package br.com.reymond.lawrence.oqrola.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.reymond.lawrence.oqrola.R;

import static br.com.reymond.lawrence.oqrola.activity.LoginActivity.mAuth;


/**
 * Created by ian on 28-Nov-16.
 */

public class LogoutActivity extends AppCompatActivity {

   // private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        mAuth.signOut();
        Toast.makeText(this, "Signed Out: ", Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",0);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }
}
