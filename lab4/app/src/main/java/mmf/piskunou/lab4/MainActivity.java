package mmf.piskunou.lab4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String NUMBERS_KEY = "number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);


        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("MainActivity"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputNumber = findViewById(R.id.inputNumber);
                int number = Integer.valueOf(inputNumber.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MainIntentService.class);
                intent.putExtra(NUMBERS_KEY, number);
                startService(intent);
                button.setEnabled(false);
            }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            int amount = intent.getIntExtra(MainIntentService.AMOUNT_KEY, 0);
            String primeNumbers = intent.getStringExtra(MainIntentService.PRIME_NUMBERS_STRING_KEY);
            final TextView receiveAmount = findViewById(R.id.receiveAmount);
            receiveAmount.setText(String.valueOf(amount));
            final TextView receivePrimeNumbers = findViewById(R.id.receivePrimeNumbers);
            receivePrimeNumbers.setText(primeNumbers);
            final Button button = findViewById(R.id.button);
            button.setEnabled(true);
        }
    };
}
