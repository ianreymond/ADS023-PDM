package br.com.reymond.lawrence.oqrola.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.reymond.lawrence.oqrola.R;

import static br.com.reymond.lawrence.oqrola.R.id.psw_id;

/**
 * Created by ian on 25-Nov-16.
 */

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuthx;
    private FirebaseAuth.AuthStateListener mAuthListenerx;

    private SharedPreferences prefs;
    private EditText txtUser;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstancesState){
        super .onCreate(savedInstancesState);
        setContentView(R.layout.sign_up);

        prefs = getSharedPreferences(LoginActivity.APP_PREFS, MODE_PRIVATE);

        //final EditText name = (EditText) findViewById(R.id.name_id);
        //final EditText email = (EditText) findViewById(R.id.email_id);
        //final EditText psw = (EditText) findViewById(psw_id);

        txtUser = (EditText) findViewById(R.id.email_id);
        txtPassword = (EditText) findViewById(R.id.psw_id);




        Button btnSign_in = (Button) findViewById(R.id.btnSign_in);
        btnSign_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mAuthx = FirebaseAuth.getInstance();

                mAuthx.signInWithEmailAndPassword(txtUser.getText().toString(), txtPassword.getText().toString())
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    mAuthx.createUserWithEmailAndPassword(txtUser.getText().toString(), txtPassword.getText().toString())
                                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(SignUp.this, R.string.error_login, Toast.LENGTH_LONG).show();
                                                    } else {
                                                        FirebaseUser user = task.getResult().getUser();
                                                        if (user != null) {
                                                            Intent it = new Intent(SignUp.this, MainActivity.class);
                                                            startActivity(it);
                                                            createNotification();
                                                            String username = txtUser.getEditableText().toString();
                                                            String psw = txtPassword.getEditableText().toString();
                                                            SharedPreferences.Editor editor = prefs.edit();
                                                            editor.putString(LoginActivity.USERNAME_KEY, username);
                                                            editor.putString(LoginActivity.PASSWORD_KEY, psw);
                                                            finish();
                                                        }
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(SignUp.this, R.string.error_login, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        });

    }

    public void createNotification(){

        Intent intent = new Intent(this, SignUp.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent ,0);

        Notification newNoti = new Notification.Builder(this)
                .setContentTitle("Cadastro Realizado")
                .setContentText("Bem Vindo")
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setAutoCancel(true)
                .build();

        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        newNoti.flags |= Notification.FLAG_AUTO_CANCEL;
        nManager.notify(0,newNoti);

    }
}

