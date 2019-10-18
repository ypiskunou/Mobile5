package mmf.piskunou.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    final static String EXTRA_ANSWER="EXTRA_ANSWER";
    EditText editAnswer;
    TextView givenQuestion;
    String savedEditAnswer;
    String savedGivenQuestion;
// коды состояния надо проверять. Интент - с контекстом связанного активити
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent= getIntent();
        String question = intent.getStringExtra(MainActivity.EXTRA_QUESTION);
        this.givenQuestion = findViewById(R.id.given_question);
        this.givenQuestion.setText(question);
        this.editAnswer = findViewById(R.id.editAnswer);

        if(savedInstanceState != null){
            savedInstanceState.get(savedEditAnswer);
            editAnswer.setText(savedEditAnswer);
        }
        if(savedInstanceState != null){
            savedInstanceState.get(savedGivenQuestion);
            givenQuestion.setText(savedGivenQuestion);
        }
    }

    public void onClickAnswer(View v) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER, this.editAnswer.getText().toString());
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(savedGivenQuestion, givenQuestion.getText().toString());
        outState.putString(savedEditAnswer, editAnswer.getText().toString());
    }
}