package com.example.carbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Car_Booking extends AppCompatActivity {
    EditText name,phone,nic,address,dropaddress,date,days,amount;
    Button book;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_car__booking);

        name = findViewById(R.id.edit_name);
        phone = findViewById(R.id.edit_number);
        nic = findViewById(R.id.edit_nic);
        address = findViewById(R.id.edit_address);
        dropaddress = findViewById(R.id.edit_drop_address);
        date = findViewById(R.id.edit_date);
        days = findViewById(R.id.edit_days);
        amount = findViewById(R.id.edit_amount);

        book = findViewById(R.id.btn_book);

        db = FirebaseFirestore.getInstance();

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String PhoneNumber = phone.getText().toString().trim();
                String NIC = nic.getText().toString().trim();
                String Address = address.getText().toString().trim();
                String DropAddress = dropaddress.getText().toString().trim();
                String Date = date.getText().toString().trim();
                String Days = days.getText().toString().trim();
                String Amount = amount.getText().toString().trim();

                if(Name.isEmpty()||PhoneNumber.isEmpty()|| NIC.isEmpty()||Address.isEmpty()||DropAddress.isEmpty()||Date.isEmpty()||Days.isEmpty()||Amount.isEmpty()){
                    name.setError("Enter a valid username");
                    phone.setError("Enter a valid card number");
                    nic.setError("Enter a valid CVV");
                    address.setError("Card Expired");
                    dropaddress.setError("Enter a valid amount");
                    date.setError("Enter a valid amount");
                    days.setError("Enter a valid amount");
                    amount.setError("Enter a valid amount");
                    return;
                }

                Intent intent = new Intent(Car_Booking.this, Home.class);
                startActivity(intent);

                uploadData(Name,PhoneNumber,NIC,Address,DropAddress,Date,Days,Amount);
            }
        });
    }

    private void uploadData(String Name,String PhoneNumber,String NIC,String Address,String DropAddress,String Date,String Days,String Amount) {

        String id = UUID.randomUUID().toString();

        Map<String,Object> book = new HashMap<>();
        book.put("ID",id);
        book.put("Name",Name);
        book.put("PhoneNumber",PhoneNumber);
        book.put("NIC",NIC);
        book.put("Address",Address);
        book.put("DropAddress",DropAddress);
        book.put("Date",Date);
        book.put("Days",Days);
        book.put("Amount",Amount);

        db.collection("Bookings").document(id).set(book)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Car_Booking.this,"Booking Details added",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Car_Booking.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
