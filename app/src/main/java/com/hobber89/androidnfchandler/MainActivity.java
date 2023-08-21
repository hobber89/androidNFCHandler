package com.hobber89.androidnfchandler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageTextView = findViewById(R.id.messageTextView);
    }

    private void startNFCDetector() {
        try {
            NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
            IntentFilter[] filters = new IntentFilter[]{intentFilter,};
            String[][] techLists = new String[][]{new String[]{android.nfc.tech.NfcV.class.getName()}};

            adapter.enableForegroundDispatch(this, pendingIntent, filters, techLists);
        } catch (Exception error) {
            String errorMessage = getString(R.string.errorMessage) + error.getMessage();
            messageTextView.setText(errorMessage);
        }
    }

    protected void onResume()
    {
        super.onResume();
        startNFCDetector();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        String message = getString(R.string.tagWasDetected);
        messageTextView.setText(message);

        //TODO: handle tag
    }
}