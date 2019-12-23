package com.example.lab5;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab5.database.App;
import com.example.lab5.database.AppDatabase;
import com.example.lab5.database.Person;
import com.example.lab5.database.PersonDao;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private PersonDao personDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.database = App.getInstance().getDatabase();
        this.personDao = this.database.personDao();

        final TextView textViewPersons = findViewById(R.id.person);
        final EditText inputLastName = findViewById(R.id.last_name);
        final EditText inputFirstName = findViewById(R.id.first_name);
        final EditText inputDateOfBirth = findViewById(R.id.date);
        final Button buttonInsert = findViewById(R.id.button_insert);
        final Button buttonSearch = findViewById(R.id.button_search);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.firstName = inputFirstName.getText().toString();
                person.lastName = inputLastName.getText().toString();
                person.dateOfBirth = inputDateOfBirth.getText().toString();
                if (!person.lastName.equals("") && !person.firstName.equals("")){
                    personDao.insert(person);
                }
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = inputFirstName.getText().toString();
                String lastName = inputLastName.getText().toString();

                if (firstName.equals("") && !lastName.equals("")){
                    textViewPersons.setText(personDao.getAllByLastNameOrPartOfThem(lastName).toString());
                }
                else {
                    if (!firstName.equals("") && lastName.equals("")) {
                        textViewPersons.setText(personDao.getAllByFirstNameOrPartOfThem(firstName).toString());
                    }
                    else {
                        textViewPersons.setText(personDao.getAllByFirstAndLastNameOrPartOfThem(firstName, lastName).toString());
                    }
                }

            }
        });
    }
}
