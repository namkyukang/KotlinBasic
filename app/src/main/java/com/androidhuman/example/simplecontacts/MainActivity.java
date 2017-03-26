package com.androidhuman.example.simplecontacts;

import com.androidhuman.example.simplecontacts.model.Person;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvPeople;
    TextView tvEmpty;
    PeopleAdapter peopleAdapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPeople = (RecyclerView) findViewById(R.id.rv_activity_main);
        tvEmpty = (TextView) findViewById(R.id.tv_activity_main);

        peopleAdapter = new PeopleAdapter();

        rvPeople.setLayoutManager(new LinearLayoutManager(this));
        rvPeople.setAdapter(peopleAdapter);

        realm = Realm.getDefaultInstance();     //사용 후 닫아 줘야만 한다.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_activity_main_add == item.getItemId()) {
            startActivityForResult(
                    new Intent(this, EditActivity.class), EditActivity.REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        queryPeople();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode && EditActivity.REQUEST_CODE == requestCode) {
            queryPeople();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            cleanUp();               //realm 닫아줌
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanUp();                  //realm 닫아줌
    }

    private void cleanUp() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    private void queryPeople() {
        realm.where(Person.class).findAllAsync()    //findAll을 하면 blocking call로 불러온다.
                .addChangeListener(new RealmChangeListener<RealmResults<Person>>() {
                    @Override
                    public void onChange(RealmResults<Person> result) {
                        if (result.isLoaded()) {
                            tvEmpty.setVisibility(result.isEmpty() ? View.VISIBLE : View.GONE);

                            peopleAdapter.setPeople(result);
                            peopleAdapter.notifyDataSetChanged();

                            result.removeAllChangeListeners();
                        }
                    }
                });
    }
}
