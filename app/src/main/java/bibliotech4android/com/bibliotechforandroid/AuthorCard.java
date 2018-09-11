package bibliotech4android.com.bibliotechforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;

public class AuthorCard extends AppCompatActivity {

    Integer authorId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_author);
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("Position")) {
                Button add = findViewById(R.id.addButton);
                add.setVisibility(Button.INVISIBLE);
                Button save = findViewById(R.id.saveButton);
                save.setVisibility(Button.VISIBLE);
                Button delete = findViewById(R.id.deleteAuthorButton);
                delete.setVisibility(Button.VISIBLE);
                EditText name = findViewById(R.id.nameField);
                EditText lastName = findViewById(R.id.lastNameField);
                EditText birth = findViewById(R.id.birthYearField);
                EditText death = findViewById(R.id.deathYearField);

                authorId = extras.getInt("Position");
                Author author = cfa.searchAuthorById(authorId);
                name.setText(author.getName());
                lastName.setText(author.getLastName());
                birth.setText(author.getYearOfBirth().toString());
                death.setText(author.getYearOfDeath().toString());
            }
        } else {
            Button save = findViewById(R.id.saveButton);
            save.setVisibility(Button.INVISIBLE);
            Button delete = findViewById(R.id.deleteAuthorButton);
            delete.setVisibility(Button.INVISIBLE);
        }

    }


    public void add(View view) {
        EditText birth = findViewById(R.id.birthYearField);
        EditText death = findViewById(R.id.deathYearField);
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        if (birth.getText().toString().matches("\\d+") && death.getText().toString().matches("\\d+")) {
            boolean isAdded = cfa.addAuthor(prepareAuthor());
            if (isAdded) {
                Toast.makeText(getApplicationContext(), "Udało się dodać autora!", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Niepowodzenie!", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(getApplicationContext(), "W polach \"Rok urodzenia\" i \"Rok śmierci\" " +
                    "powinny znajdować się tylko liczby!", Toast.LENGTH_SHORT).show();
    }

    public void save(View view) {
        EditText birth = findViewById(R.id.birthYearField);
        EditText death = findViewById(R.id.deathYearField);
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());

        if (birth.getText().toString().matches("\\d+") && death.getText().toString().matches("\\d+")) {
            boolean isSaved = cfa.updateAuthor(prepareAuthor());
            if (isSaved) {
                Toast.makeText(getApplicationContext(), "Udało się zaktualizować autora!", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Niepowodzenie!", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(getApplicationContext(), "W polach \"Rok urodzenia\" i \"Rok śmierci\" " +
                    "powinny znajdować się tylko liczby!", Toast.LENGTH_SHORT).show();
    }

    public void delete(View view) {
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        boolean isDeleted = cfa.deleteAuthor(authorId);
        if (isDeleted) {
            Toast.makeText(getApplicationContext(), "Udało się usunąć autora!", Toast.LENGTH_SHORT).show();
            finish();
        } else Toast.makeText(getApplicationContext(), "Niepowodzenie!", Toast.LENGTH_SHORT).show();


    }

    private Author prepareAuthor() {
        EditText name = findViewById(R.id.nameField);
        EditText lastName = findViewById(R.id.lastNameField);
        EditText birth = findViewById(R.id.birthYearField);
        EditText death = findViewById(R.id.deathYearField);
        String bY = birth.getText().toString();
        String dY = death.getText().toString();
        //null handling
        Integer birthYear;
        Integer deathYear;
        if (bY.equals("")) birthYear = null;
        else birthYear = Integer.parseInt(bY);
        if (dY.equals("")) deathYear = null;
        else deathYear = Integer.parseInt(dY);

        //if it's edited author
        if (authorId != null)
            return new Author(authorId, name.getText().toString(), lastName.getText().toString(), birthYear, deathYear);
        //if it's new author
        return new Author(name.getText().toString(), lastName.getText().toString(), birthYear, deathYear);
    }
}
