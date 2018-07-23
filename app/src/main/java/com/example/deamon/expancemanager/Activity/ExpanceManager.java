package com.example.deamon.expancemanager.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deamon.expancemanager.Classes.Transaction;
import com.example.deamon.expancemanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.deamon.expancemanager.R.*;

public class ExpanceManager extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    ImageView imageView_imageProfile;
    TextView textview_useremail;
    List<Transaction> transactionList;
    LinearLayout transactionContainer;
    TextView totalAmtText;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_expance_manager);

        transactionList = new ArrayList<Transaction>();

        transactionContainer = findViewById(id.fiveTransactions);

        mDatabase = FirebaseDatabase.getInstance().getReference("/transactions");

        getTransaction();

        imageView_imageProfile = (ImageView) findViewById(id.imageview_userphoto);
        totalAmtText = (TextView) findViewById(id.totalAmt);
        textview_useremail = (TextView) findViewById(id.textview_useremail);

        toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();

    }
    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.addMoney:
                        Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ExpanceManager.this, AddMoneyActivity.class);
                            startActivity(intent    );
                        break;

                    case R.id.profile:
                       Toast.makeText(getApplicationContext(), "Profile",Toast.LENGTH_SHORT).show();
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView textview_email = (TextView)header.findViewById(id.textview_useremail);
        textview_email.setText("up.prajapat@gmail.com");
        drawerLayout = (DrawerLayout)findViewById(id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, string.drawer_open, string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void getTransaction() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                transactionList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Transaction transaction = postSnapshot.getValue(Transaction.class);
                    //adding artist to the list
                    transactionList.add(transaction);
                }

                Log.d("netsmertia", transactionList.toString());
                setupFiveTransactions();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupFiveTransactions() {
        LayoutInflater inflater = LayoutInflater.from(this);
        TextView category, amount, date;
        int totalAmt = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            totalAmt += transactionList.get(i).getAmount();
            Log.d("netsmertia", "item " + transactionList.get(i).getCategory());
            View v = inflater.inflate(layout.transaction_row, null, false);
            category = v.findViewById(id.categoryText);
            amount = v.findViewById(id.amountText);
            date = v.findViewById(id.dateText);
            final Transaction t = transactionList.get(i);
            category.setText(t.getCategory());
            amount.setText("" + t.getAmount());
            date.setText(t.getDate());
            transactionContainer.addView(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExpanceManager.this, UpdateTransaction.class);

                    intent.putExtra("tId", t.getId());
                    intent.putExtra("category", t.getCategory());
                    intent.putExtra("amount", t.getAmount());
                    intent.putExtra("date", t.getDate());
                    startActivity(intent);
                }
            });
        }

        totalAmtText.setText("" + totalAmt);

    }
}
