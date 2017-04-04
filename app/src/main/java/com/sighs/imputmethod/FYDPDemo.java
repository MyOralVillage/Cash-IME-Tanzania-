package com.sighs.imputmethod;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;

/**
 * Created by stuart on 3/19/17.
 */

public class FYDPDemo extends AppCompatActivity {
    private final static String LOGKEY = "SWOOSH_FYDP_DEMO";
    private TextView txtEmail;
    private TextView txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fydp_demo);
        txtEmail = (TextView) findViewById(R.id.demo_recipient);
        txtValue = (TextView) findViewById(R.id.demo_amount);
        Button btnSend = (Button) findViewById(R.id.demo_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder msg = new StringBuilder();
                msg.append("Sending ");
                msg.append(txtValue.getText());
                msg.append(" to ");
                msg.append(txtEmail.getText());
                message(msg.toString());
                txtEmail.setText("");
                txtValue.setText("");
                // sendEmail(txtEmail.getText().toString(), txtValue.getText().toString());
            }
        });
    }

    private void message(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void sendEmail(String to, String value) {
        Log.i(LOGKEY, "Email Sent");

        if(!isEmailValid(to)) {
            Toast.makeText(this, "Please Enter a Valid Email",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String[] TO = {to};
        String[] CC = {};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Thanks for Testing the Cash Keyboard");
        StringBuilder body = new StringBuilder();
        body.append("Thank you for sending money using the Cash Keyboard. You sent ");
        body.append(value);
        body.append("Tsh.\n");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body.toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i(LOGKEY, "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(FYDPDemo.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
