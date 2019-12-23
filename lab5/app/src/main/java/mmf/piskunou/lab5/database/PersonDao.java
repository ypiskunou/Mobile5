package mmf.piskunou.lab5.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PersonDao {
    @Query("SELECT id, first_name, last_name, date_of_birth FROM person " +
            "WHERE first_name LIKE '%' || :first || '%' AND last_name LIKE '%' || :last || '%'")
    List<Person> getAllByFirstAndLastNameOrPartOfThem(String first, String last);

    @Query("SELECT id, first_name, last_name, date_of_birth FROM person WHERE last_name LIKE '%' || :last || '%' ")
    List<Person> getAllByLastNameOrPartOfThem(String last);

    @Query("SELECT id, first_name, last_name, date_of_birth FROM person WHERE first_name LIKE '%' || :first || '%' ")
    List<Person> getAllByFirstNameOrPartOfThem(String first);

    @Insert
    void insert(Person person);
}
