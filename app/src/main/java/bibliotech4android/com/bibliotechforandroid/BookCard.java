package bibliotech4android.com.bibliotechforandroid;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BookCard extends AppCompatActivity {
    Integer bookId = null;
    Integer authorId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_card);
    }

    public void addBook(View view) {
        ConnectorForBooks cfb = new ConnectorForBooks(getApplicationContext());


    }

    public void searchAuthor(View view){
        //if searchres==null -> alert z propozycją dodania autora
    }

    private Book prepareBook() {
        EditText title = findViewById(R.id.bookTitlefField);
        //autor jako edittext?
        //EditText author = findViewById(R.id.bookAuthorField);
        EditText publisher = findViewById(R.id.bookPublisherField);
        EditText issueYear = findViewById(R.id.bookIssueYearField);
        EditText isbn = findViewById(R.id.bookIsbnField);
        EditText localization = findViewById(R.id.bookLocalizationField);
        EditText whoLent = findViewById(R.id.bookWhoLentField);
        EditText tags = findViewById(R.id.bookTagsField);
        EditText notes = findViewById(R.id.bookNotesField);

        String bookTitle = title.getText().toString();
        String bookPublisher = publisher.getText().toString();

            Integer bookIssueYear = Integer.parseInt(issueYear.getText().toString());

            String bookIsbn = isbn.getText().toString();
            String bookTags = tags.getText().toString();
            String bookNotes = notes.getText().toString();

            String bookIsLent = "Dostępna";

            String bookWhoLent = whoLent.getText().toString();
            String bookLocalization = localization.getText().toString();


                if (bookId != null) {
                    return new Book(bookId, bookTitle, bookPublisher, bookIssueYear, bookIsbn, authorId, bookTags, bookNotes, bookIsLent, bookWhoLent, bookLocalization);
                }
            return new Book(bookTitle, bookPublisher, bookIssueYear, bookIsbn, authorId, bookTags, bookNotes, bookIsLent, bookWhoLent, bookLocalization);

    }

    private boolean isBookReady(){
        EditText title = findViewById(R.id.bookTitlefField);
        //autor jako edittext?
        //EditText author = findViewById(R.id.bookAuthorField);
        EditText publisher = findViewById(R.id.bookPublisherField);
        EditText issueYear = findViewById(R.id.bookIssueYearField);
        EditText isbn = findViewById(R.id.bookIsbnField);
        EditText localization = findViewById(R.id.bookLocalizationField);
        EditText whoLent = findViewById(R.id.bookWhoLentField);
        EditText tags = findViewById(R.id.bookTagsField);
        EditText notes = findViewById(R.id.bookNotesField);



        if(title.getText().equals("")||publisher.getText().equals("")||issueYear.getText().equals("")||tags.getText().equals("")){
            Toast.makeText(getApplicationContext(),"Proszę wypełnić poprawnymi danymi pola \"Tytuł\",\"Wydawnictwo\",\"Rok publikacji\"," +
                    "\"Tagi\"",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
