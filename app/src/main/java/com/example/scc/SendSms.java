package com.example.scc;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendSms extends AppCompatActivity {

    // Add a new variable for the phone number EditText
    private EditText txtPhoneNumber;
    private EditText txtMessage;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge is removed for simplicity with this design, but can be re-added if needed
        setContentView(R.layout.activity_send_sms);

        // Initialize the new EditText
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtMessage = findViewById(R.id.txtMessage);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the number and message from the EditText fields
                String mobileNumber = txtPhoneNumber.getText().toString();
                String message = txtMessage.getText().toString();

                // Basic validation to ensure fields are not empty
                if (TextUtils.isEmpty(mobileNumber)) {
                    Toast.makeText(SendSms.this, "Please enter a phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(SendSms.this, "Please enter a message.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call the send method with the user-provided data
                send(message, mobileNumber);
            }
        });
    }

    public void send(String message, String mobileNumber) {
        // Disable the button to prevent multiple clicks while sending
        btnSend.setEnabled(false);
        btnSend.setText("Sending...");

        SemaphoreSmsSender.sendSms(mobileNumber, message, new SemaphoreSmsSender.SmsSendListener() {
            @Override
            public void onResult(String result) {
                // Re-enable the button and restore its text after getting a result
                btnSend.setEnabled(true);
                btnSend.setText("Send Message");

                Toast.makeText(SendSms.this, result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
