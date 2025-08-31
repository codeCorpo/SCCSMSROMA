package com.example.scc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast; // Import Toast

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendSms extends AppCompatActivity {

    private EditText txtMessage;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_sms);
        txtMessage = findViewById(R.id.txtMessage);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtMessage.getText().toString();
                String mobileNumber = "+639912543805";
                send(message, mobileNumber);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void send(String message, String mobileNumber) {
        // Call the updated sendSms method, providing a new listener
        SemaphoreSmsSender.sendSms(mobileNumber, message, new SemaphoreSmsSender.SmsSendListener() {
            @Override
            public void onResult(String result) {
                // This code runs on the UI thread after the background task is complete
                // We will display the result in a Toast message
                Toast.makeText(SendSms.this, result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
