package mmf.piskunou.mobile5

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView


class DisplayMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Получаем сообщение из объекта intent
        val intent = intent
        val question = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)

        val parentLayout = LinearLayout(this)
        // Создаем текстовое поле
        val questionView = TextView(this)
        questionView.textSize = 40f
        questionView.text = question
        parentLayout.addView(questionView)

        // Устанавливаем текстовое поле в системе компоновки activity
        setContentView(parentLayout)
    }
}
