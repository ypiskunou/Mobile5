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

/**
 * Введите общие стили для элементов управления.
 *  Добавте локализацию - приложение должно поддерживать русский и английский языки,
 * в зависимости от настроек системы.
 *  Добавте разметку для портретного и ландшафтного вида -
 * которые будут применяться при повороте телефона.*/

public class MainActivity extends AppCompatActivity {

    TextView answerView;
    EditText editQuestion;
    final static String EXTRA_QUESTION="EXTRA_QUESTION";
    final int REQUEST_CODE=1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editQuestion = findViewById(R.id.edit_question);
        this.answerView = findViewById(R.id.given_answer);
    }

    public void onClickQuestion(View v) {
        this.intent= new Intent(this, DisplayMessageActivity.class);
        this.intent.putExtra(EXTRA_QUESTION, this.editQuestion.getText().toString());
        startActivityForResult(this.intent, REQUEST_CODE);
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
                    String givenAnswer = data.getStringExtra(DisplayMessageActivity.EXTRA_ANSWER);
                    answerView.setText(givenAnswer);
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