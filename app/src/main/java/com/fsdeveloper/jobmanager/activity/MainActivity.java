package com.fsdeveloper.jobmanager.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fsdeveloper.jobmanager.R;
import com.fsdeveloper.jobmanager.bean.Client;
import com.fsdeveloper.jobmanager.bean.JobCategory;
import com.fsdeveloper.jobmanager.bean.Phone;
import com.fsdeveloper.jobmanager.bean.PhoneType;
import com.fsdeveloper.jobmanager.bean.User;
import com.fsdeveloper.jobmanager.dao.ClientDao;
import com.fsdeveloper.jobmanager.dao.JobCategoryDao;
import com.fsdeveloper.jobmanager.dao.Manager;
import com.fsdeveloper.jobmanager.dao.PhoneTypeDao;
import com.fsdeveloper.jobmanager.dao.UserDao;
import com.fsdeveloper.jobmanager.exception.JobManagerException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("USER", "CHEGOU AQUI");
        TextView txt = (TextView) findViewById(R.id.categorias);

//        JobCategoryDao cat = new JobCategoryDao(this);

        User u = new User("AGORA VAI NOVO", "putzzz@mail.com", "156", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            Phone phones = new Phone();
            Manager manager = new Manager(this);

            List<Phone> listPhones = new ArrayList<>();

            PhoneType type = manager.getPhoneType(1);
            PhoneType type2 = manager.getPhoneType(3);
            listPhones.add(new Phone("(083) 3335-2801", type));
            listPhones.add(new Phone("(083) 98770-5202",type2));

            Client client = new Client("Fulano", "dos Santos", "fulano@mail.com", "Rua almirante, 62", 0, 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), listPhones);
            int client_id = manager.insertClient(client);

            PhoneTypeDao types = new PhoneTypeDao(this);

            txt.setText("ID " + client_id + " " + manager.getClient(client_id));

        } catch (JobManagerException e) {
            Log.i("Exceção Job Manager", e.getMessage());
        }

        Resources res = getResources();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
