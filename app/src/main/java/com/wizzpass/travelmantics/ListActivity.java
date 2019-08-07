package com.wizzpass.travelmantics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ((item.getItemId())) {

            case R.id.insert_menu:

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu:
                signOut();
                FirebaseUtil.detachListener();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUtil.openFbReference("traveldeals",this);
        RecyclerView rvDeals = findViewById(R.id.rvDeals);
        final DealAdapter adapter = new DealAdapter();
        rvDeals.setAdapter(adapter);
        LinearLayoutManager dealsLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvDeals.setLayoutManager(dealsLayoutManager);

        FirebaseUtil.attachListener();
    }

    public void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Logout","User Logged Out");
                        FirebaseUtil.attachListener();
                    }

                });
    }
}
