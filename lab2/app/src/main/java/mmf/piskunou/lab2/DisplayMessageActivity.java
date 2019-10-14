package mmf.piskunou.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity implements View.OnClickListener{

    final static String EXTRA_ANSWER="EXTRA_ANSWER";
    EditText editAnswer;
    TextView givenQuestion;
    Button btnAnswer;
// коды состояния надо проверять. Интент - с контекстом связанного активити
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent= getIntent();
        String question = intent.getStringExtra(MainActivity.EXTRA_QUESTION);
        givenQuestion = findViewById(R.id.given_question);
        givenQuestion.setText(question);
        editAnswer = findViewById(R.id.editAnswer);
        btnAnswer = findViewById(R.id.button_answer);
        btnAnswer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER, editAnswer.getText().toString());
        setResult(RESULT_OK, intent);
        finish();

    }
}