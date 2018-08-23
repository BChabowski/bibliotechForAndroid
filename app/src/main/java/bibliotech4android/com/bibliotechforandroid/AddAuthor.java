package bibliotech4android.com.bibliotechforandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddAuthor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
    }
    public void add(View view){
        EditText name = findViewById(R.id.nameField);
        EditText lastName = findViewById(R.id.lastNameField);
        EditText birth = findViewById(R.id.birthYearField);
        EditText death = findViewById(R.id.deathYearField);
        Integer birthYear = Integer.parseInt(birth.getText().toString());
        Integer deathYear = Integer.parseInt(death.getText().toString());
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        if(cfa.addAuthor(new Author(name.getText().toString(),lastName.getText().toString(),birthYear,deathYear))){
            Toast.makeText(getApplicationContext(),"Udało się dodać autora!",Toast.LENGTH_SHORT).show();
            finish();
        }
        else Toast.makeText(getApplicationContext(),"Niepowodzenie!",Toast.LENGTH_SHORT).show();
    }
}
