package bibliotech4android.com.bibliotechforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Vector;

public class AddAuthor extends AppCompatActivity {

    Integer authorId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_author);
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if (extras.containsKey("Position")) {
                Button add = findViewById(R.id.addButton);
                add.setVisibility(Button.INVISIBLE);
                Button save1 = findViewById(R.id.saveButton);
                save1.setVisibility(Button.VISIBLE);
                EditText name =  findViewById(R.id.nameField);
                EditText lastName = findViewById(R.id.lastNameField);
                EditText birth = findViewById(R.id.birthYearField);
                EditText death = findViewById(R.id.deathYearField);

                authorId = extras.getInt("Position");
                Vector<Author> author = cfa.searchAuthorById(authorId);
                name.setText(author.get(0).getName());
                lastName.setText(author.get(0).getLastName());
                birth.setText(author.get(0).getYearOfBirth().toString());
                death.setText(author.get(0).getYearOfDeath().toString());
            }
        }
        else{ Button save = findViewById(R.id.saveButton);
        save.setVisibility(Button.INVISIBLE);
        }

    }


    public void add(View view){
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());

        if(cfa.addAuthor(prepareAuthor())){
            Toast.makeText(getApplicationContext(),"Udało się dodać autora!",Toast.LENGTH_SHORT).show();
            finish();
        }
        else Toast.makeText(getApplicationContext(),"Niepowodzenie!",Toast.LENGTH_SHORT).show();
    }
    public void save(View view){
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        if(cfa.updateAuthor(prepareAuthor())){
            Toast.makeText(getApplicationContext(),"Udało się zaktualizować autora!",Toast.LENGTH_SHORT).show();
            finish();
        }
        else Toast.makeText(getApplicationContext(),"Niepowodzenie!",Toast.LENGTH_SHORT).show();
    }

    private Author prepareAuthor(){
        EditText name = findViewById(R.id.nameField);
        EditText lastName = findViewById(R.id.lastNameField);
        EditText birth = findViewById(R.id.birthYearField);
        EditText death = findViewById(R.id.deathYearField);

        Integer birthYear = Integer.parseInt(birth.getText().toString());
        Integer deathYear = Integer.parseInt(death.getText().toString());
        if(authorId!=null) return new Author(authorId,name.getText().toString(),lastName.getText().toString(),birthYear,deathYear);

        return new Author(name.getText().toString(),lastName.getText().toString(),birthYear,deathYear);
    }
}
