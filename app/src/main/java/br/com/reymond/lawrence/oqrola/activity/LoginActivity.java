package br.com.reymond.lawrence.oqrola.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.RemoteMessage;

import br.com.reymond.lawrence.oqrola.R;

/**
 * Created by ian on 24-Oct-16.
 */


public class LoginActivity extends AppCompatActivity {
    private EditText txtUser;
    private EditText txtPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPassword = (EditText) findViewById(R.id.txtPassword);



        mAuth = FirebaseAuth.getInstance();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(txtUser.getText().toString(), txtPassword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();
                                } else {
                                    FirebaseUser user = task.getResult().getUser();
                                    if (user != null) {
                                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(it);
                                        //Intent i = new Intent(LoginActivity.this, NotifyActivity.class);
                                        //startActivity(i);




                                        finish();
                                    }
                                }
                            }
                        });
            }
        });

        Button btnSign_in = (Button) findViewById(R.id.btnSign_in);
        btnSign_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(txtUser.getText().toString(), txtPassword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    mAuth.createUserWithEmailAndPassword(txtUser.getText().toString(), txtPassword.getText().toString())
                                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (!task.isSuccessful()) {
                                                        Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();
                                                    } else {
                                                        FirebaseUser user = task.getResult().getUser();
                                                        if (user != null) {
                                                            Intent it = new Intent(LoginActivity.this, MainActivity.class);
                                                            startActivity(it);
                                                            createNotification();
                                                            finish();
                                                        }
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public void createNotification(){

        Intent intent = new Intent(this, LoginActivity.class);
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