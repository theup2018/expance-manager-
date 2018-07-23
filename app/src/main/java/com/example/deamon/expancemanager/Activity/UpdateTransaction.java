package com.example.deamon.expancemanager.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.deamon.expancemanager.Classes.Transaction;
import com.example.deamon.expancemanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateTransaction extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Transaction t;
    TextView categoryText, dateText;
    EditText amountEdit;
    Button updateButton;

    Calendar calendar;

    String dateInText, category, id;
    int year, month, day;


    private DatabaseReference mDatabase;

    int amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_transaction);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        calendar = Calendar.getInstance();

        id =  data.getString("tId");
        amount = data.getInt("amount", 0);
        category = data.getString("category", "Others");
        dateInText = data.getString("date");

        categoryText = findViewById(R.id.editCategoryChooser);
        amountEdit = findViewById(R.id.editTextAmount);
        dateText = findViewById(R.id.editDatePicker);
        updateButton = findViewById(R.id.updateBtn);


        categoryText.setHint(category);
        amountEdit.setText("" + amount);
        dateText.setHint(dateInText);


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formator = new SimpleDateFormat("dd MMM YYYY");
                    updateDate(calendar);
                    Log.d("nets", "month: " + month);
                    DatePickerDialog dialog = new DatePickerDialog(UpdateTransaction.this,
                            UpdateTransaction.this, year, month, day);
                    dialog.show();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("/transactions/"+id);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                mDatabase.setValue(new Transaction(id, amount, category, date));
            }
        });
    }

    private void updateDate(Calendar c) {

        String[] parts = dateInText.split("/");
        this.year = Integer.parseInt(parts[2]);
        this.month = Integer.parseInt(parts[1]);
        this.day = Integer.parseInt(parts[0]);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
        dateInText = day + "/" + month + "/" + year;
        Log.d("netsmertia", dateInText);
    }
}
