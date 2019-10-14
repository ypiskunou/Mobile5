package mmf.piskunou.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView answerView;
    EditText editQuestion;
    Button btnQuestion;
    final static String EXTRA_QUESTION="EXTRA_QUESTION";
    final int REQUEST_CODE=1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editQuestion = findViewById(R.id.edit_question);
        answerView = findViewById(R.id.given_answer);
        btnQuestion = findViewById(R.id.button_question);
        btnQuestion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        intent= new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_QUESTION, editQuestion.getText().toString());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // запишем в лог значения requestCode и resultCode
        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        // если пришло ОК
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    String given_answer = data.getStringExtra(DisplayMessageActivity.EXTRA_ANSWER);
                    answerView.setText(given_answer);
                    break;
            }
            // если вернулось не ОК
        } else {
            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
        if (data == null){
            System.out.println(" No info returned \n");
        }
    }
}