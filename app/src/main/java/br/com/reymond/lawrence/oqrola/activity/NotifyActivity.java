package br.com.reymond.lawrence.oqrola.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.reymond.lawrence.oqrola.R;

/**
 * Created by ian on 26-Oct-16.
 */

public class NotifyActivity extends AppCompatActivity{

    public  static String NOTIFY_ACTIVITY = "br.com.reymond.lawrence.oqrola.NotifyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

}
