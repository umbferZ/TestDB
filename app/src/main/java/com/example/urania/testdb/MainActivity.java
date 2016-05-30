package com.example.urania.testdb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    EditText username, password, name;
    ScrollView scrollView;

    VivzDatabaseAdapter vivzHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         /*
          * Example code by @vivz
          */

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        name = (EditText) findViewById(R.id.et_name);
        scrollView = (ScrollView) findViewById(R.id.sv_allData);
        vivzHelper = new VivzDatabaseAdapter(this);

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

    public void adduser(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        long id = vivzHelper.insertData(user, pass);
        if (id < 0) {
            MessageDebug.message(this, "Unsuccessful!");
        } else {
            MessageDebug.message(this, "Successfully! Insert a row. id:" + id);
            username.setText("");
            password.setText("");
        }

    }

    public void getDetails(View view) {
        MessageDebug.message(this, "getDetails");
        String s1 = name.getText().toString();
        String psw = vivzHelper.getData(s1);
        name.setText("");
        MessageDebug.message(this, psw);
    }

    public void update(View view) {
        if (name.getText().toString().length() > 0) {
            String nameText = name.getText().toString();
            String oldName = nameText.substring(0, nameText.indexOf(" "));
            String newName = nameText.substring(nameText.indexOf(" ") + 1);
            int rows = vivzHelper.updateData(oldName, newName);
            name.setText("");
            MessageDebug.message(this, "updated " + rows + " row(s)");
        } else {
            MessageDebug.message(this, "Please insert a name");
        }

    }

    public void delete(View view) {
        if (name.getText().toString().length() > 0) {
            int rows = vivzHelper.deleteRow(name.getText().toString());
            name.setText("");
            MessageDebug.message(this, "delete " + rows + " row(s)");
        } else {
            MessageDebug.message(this, "Please insert a name");
        }
    }

    public void viewAll(View view) {
        String all = vivzHelper.getAllData();
        MessageDebug.message(this, all);

    }
}
