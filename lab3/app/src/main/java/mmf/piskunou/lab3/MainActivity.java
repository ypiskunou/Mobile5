package mmf.piskunou.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView answerView;
    EditText editQuestion;
    String savedAnswerView;
    String savedEditQuestion;

    final static String EXTRA_QUESTION="EXTRA_QUESTION";
    final int REQUEST_CODE=1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editQuestion = findViewById(R.id.edit_question);
        this.answerView = findViewById(R.id.given_answer);

        if(savedInstanceState != null){
            savedInstanceState.get(savedEditQuestion);
            editQuestion.setText(savedEditQuestion);
        }
        if(savedInstanceState != null){
            savedInstanceState.get(savedAnswerView);
            answerView.setText(savedAnswerView);
        }
    }

    public void onClickQuestion(View v) {
        this.intent= new Intent(this, DisplayMessageActivity.class);
        this.intent.putExtra(EXTRA_QUESTION, this.editQuestion.getText().toString());
        startActivityForResult(this.intent, REQUEST_CODE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(savedAnswerView, answerView.getText().toString());
        outState.putString(savedEditQuestion, editQuestion.getText().toString());
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

/**
 * Введите общие стили для элементов управления.
 *  Добавте локализацию - приложение должно поддерживать русский и английский языки,
 * в зависимости от настроек системы.
 *  Добавте разметку для портретного и ландшафтного вида -
 * которые будут применяться при повороте телефона.
 *
 * 1. Обьедените то что можно переиспользовать в фрагменты. Например у вас в 2х activity
 * есть поле ввода и кнопка - имеет смысл сделать это 1 фрагментом, и переиспользовать его.
 *
 * 2. Добавьте в приложение меню параметров. (то которое с появляется по нажатию на 3
 * точки в углу приложения :) ). В меню добавьте минимум один пункт - выход.
 *
 * 3. По нажатию на "выход" в любом активити, должен появляться диалог - "вы точно хотите выйти?",
 * и варианты "да/нет". При ответе "да" - все окна закрываются и приложение полностью завершает
 * свою работу.
 * (Сделать нормальный выход из приложения - довольно сложно, тем более что вы не знаете о режимах
 * запуска и управления activity - но если вы почитаете и разберетесь, будет хорошо.
 * Если нет - достаточно закрыть все активити вызвав finish() или finishAndRemoveTask() -
 * будем считать это выходом.
 * Ни в коем случае не используйте говнокодские решения из stackoverflow с убиванием процессов!!!)
 * */