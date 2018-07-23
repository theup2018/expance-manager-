package com.example.deamon.expancemanager.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.deamon.expancemanager.Classes.Transaction;
import com.example.deamon.expancemanager.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddMoneyActivity extends AppCompatActivity
    implements DatePickerDialog.OnDateSetListener {


    Calendar calendar;

    EditText amount;
    TextView chooseCatagory;
    TextView datePicker;
    Button saveBtn;
    String selecedCatagory;
    int year, month, day;
    String dateText;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        calendar = Calendar.getInstance();
        setupDate(calendar);

        mDatabase = FirebaseDatabase.getInstance().getReference("/transactions");

        chooseCatagory = (TextView) findViewById(R.id.textview_selectCatagories);
        chooseCatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(AddMoneyActivity.this, chooseCatagory);
                popupMenu.getMenuInflater().inflate(R.menu.choose_catagory, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.food:
                                // do your code
                                selecedCatagory = (String) item.getTitle();
                                chooseCatagory.setText(selecedCatagory);

                            case R.id.shoping:
                                // do your code
                                selecedCatagory = (String) item.getTitle();
                                chooseCatagory.setText(selecedCatagory);
                            case R.id.travelling:

                            case R.id.cloths:
                                // do your code
                                selecedCatagory = (String) item.getTitle();
                                chooseCatagory.setText(selecedCatagory);
                            case R.id.other:
                                // do your code
                                selecedCatagory = (String) item.getTitle();
                                chooseCatagory.setText(selecedCatagory);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        datePicker = (TextView) findViewById(R.id.datePicker);
        datePicker.setHint(dateText);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(AddMoneyActivity.this,
                            AddMoneyActivity.this, year, month,day);
                dialog.show();
            }
        });


        saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();
            }
        });

        amount = (EditText) this.findViewById(R.id.amount);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
        setupDate(calendar);
        this.datePicker.setHint(this.dateText);
    }
    private void setupDate(Calendar calendar) {
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.dateText = new SimpleDateFormat("dd/MM/YYYY").format(calendar.getTime());
    }

    private void saveTransaction() {
        saveBtn.setText("Saving..");
        saveBtn.setEnabled(false);
        String id = mDatabase.push().getKey();
        Transaction transaction = new Transaction(id,
                Integer.parseInt(amount.getText().toString()),
                selecedCatagory,
                dateText );

        mDatabase.child(id).setValue(transaction);
        amount.setText("");

        Intent intent = new Intent(this, ExpanceManager.class);
        startActivity(intent);
    }
}
