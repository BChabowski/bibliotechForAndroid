package bibliotech4android.com.bibliotechforandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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

        Button save = findViewById(R.id.saveButton);
        Button delete = findViewById(R.id.deleteAuthorButton);
        Button add = findViewById(R.id.addButton);

        EditText name = findViewById(R.id.nameField);
        EditText lastName = findViewById(R.id.lastNameField);
        EditText birth = findViewById(R.id.birthYearField);
        EditText death = findViewById(R.id.deathYearField);

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                if (extras.containsKey("Position")) {
                    //it's an existing author and we are editing him!
                    add.setVisibility(Button.INVISIBLE);

                    save.setVisibility(Button.VISIBLE);
                    delete.setVisibility(Button.VISIBLE);


                    authorId = extras.getInt("Position");
                    Author author = cfa.searchAuthorById(authorId);
                    name.setText(author.getName());
                    lastName.setText(author.getLastName());

                    if(author.getYearOfBirth()==null) birth.setText("");
                    else birth.setText(author.getYearOfBirth().toString());

                    if(author.getYearOfDeath()==null) death.setText("");
                    else death.setText(author.getYearOfDeath().toString());
                }
            } else {
                //it's a new author!
                save.setVisibility(Button.INVISIBLE);
                delete.setVisibility(Button.INVISIBLE);
            }

        if(!LoginScreen.isLogged) {
                //if user is not logged in, disable EditTexts and hide buttons
            save.setVisibility(Button.INVISIBLE);
            delete.setVisibility(Button.INVISIBLE);
            add.setVisibility(Button.INVISIBLE);

            name.setInputType(InputType.TYPE_NULL);
            lastName.setInputType(InputType.TYPE_NULL);
            birth.setInputType(InputType.TYPE_NULL);
            death.setInputType(InputType.TYPE_NULL);
        }

    }


    public void add(View view) {

        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        Author author = prepareAuthor();
        Vector<Author> authors = cfa.showAllAuthors();
        boolean isUnique = true;

        if(author!=null) {
            //find whether there is such author in db
            for (Author authorFromDb : authors) {
                if (authorFromDb.toString().equals(author.toString())) {
                    Toast.makeText(getApplicationContext(), "Taki autor już istnieje w bazie danych!", Toast.LENGTH_SHORT).show();
                    isUnique = false;
                    break;
                }
            }
            //and if not, proceed
            if (isUnique) {
                boolean isAdded = cfa.addAuthor(author);
                if (isAdded) {
                    Toast.makeText(getApplicationContext(), "Udało się dodać autora!", Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Niepowodzenie!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void save(View view) {
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
            Author author = prepareAuthor();
        Vector<Author> authors = cfa.showAllAuthors();
        boolean isUnique = true;
            if(author!=null) {
                for (Author authorFromDb : authors) {
                    if (authorFromDb.toString().equals(author.toString())) {
                        Toast.makeText(getApplicationContext(), "Taki autor już istnieje w bazie danych!", Toast.LENGTH_SHORT).show();
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) {
                    boolean isSaved = cfa.updateAuthor(author);
                    if (isSaved) {
                        Toast.makeText(getApplicationContext(), "Udało się zaktualizować autora!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Niepowodzenie!", Toast.LENGTH_SHORT).show();
                }
            }
    }


    public void delete(View view) {
        final ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        ConnectorForBooks cfb = new ConnectorForBooks(getApplicationContext());
        //check if author has some books linked to him
        Vector<Book> books = cfb.selectBooks(authorId.toString(),8);
        if(books.isEmpty()) {
            Dialog.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == Dialog.BUTTON_POSITIVE) {
                        boolean isDeleted = cfa.deleteAuthor(authorId);
                        if (isDeleted) {
                            Toast.makeText(getApplicationContext(), "Udało się usunąć autora!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else
                            Toast.makeText(getApplicationContext(), "Niepowodzenie!", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Czy na pewno usunąć autora?").setNegativeButton("Nie", listener).setPositiveButton("Tak", listener).show();


        }else Toast.makeText(this,"Nie udało się usunąć autora! Być może w bazie danych wciąż" +
                " znajdują się przypisane do niego książki",Toast.LENGTH_LONG).show();

    }

    private Author prepareAuthor() {
        EditText name = findViewById(R.id.nameField);
        EditText lastName = findViewById(R.id.lastNameField);
        EditText birth = findViewById(R.id.birthYearField);
        EditText death = findViewById(R.id.deathYearField);
        String bY = birth.getText().toString();
        String dY = death.getText().toString();
        Integer birthYear;
        Integer deathYear;

        if (bY.equals("")) birthYear = null;
        else if(bY.matches("\\d+")) birthYear = Integer.parseInt(bY);
        else {Toast.makeText(this,"W polu \"Rok urodzenia\" powinny znajdować się tylko cyfry!",Toast.LENGTH_SHORT).show();
        return null;
        }
        if (dY.equals("")) deathYear = null;
        else if(dY.matches("\\d+")) deathYear = Integer.parseInt(dY);
        else {Toast.makeText(this,"W polu \"Rok śmierci\" powinny znajdować się tylko cyfry!",Toast.LENGTH_SHORT).show();
        return null;
        }

        //if it's an edited author
        if (authorId != null)
            return new Author(authorId, name.getText().toString(), lastName.getText().toString(), birthYear, deathYear);
        //if it's a new author
        return new Author(name.getText().toString(), lastName.getText().toString(), birthYear, deathYear);
    }
}
