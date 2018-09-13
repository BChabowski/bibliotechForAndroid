package bibliotech4android.com.bibliotechforandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class BookCard extends AppCompatActivity {
    Integer bookId = null;
    Integer authorId = null;
    ArrayList<String> authorsArray;
    private String[] lentOrNot;
    ArrayAdapter<String> adapter;
    Vector<Author> authorVector;
    AutoCompleteTextView authorTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_card);
        Button add = findViewById(R.id.addBookButton);
        Button update = findViewById(R.id.updateBookButton);
        Button delete = findViewById(R.id.deleteBookButton);

        EditText title = findViewById(R.id.bookTitlefField);
        EditText publisher = findViewById(R.id.bookPublisherField);
        EditText issueYear = findViewById(R.id.bookIssueYearField);
        EditText isbn = findViewById(R.id.bookIsbnField);
        EditText localization = findViewById(R.id.bookLocalizationField);
        Spinner isLent = findViewById(R.id.isLentSpinner);
        EditText whoLent = findViewById(R.id.bookWhoLentField);
        EditText tags = findViewById(R.id.bookTagsField);
        EditText notes = findViewById(R.id.bookNotesField);

        ConnectorForBooks cfb = new ConnectorForBooks(getApplicationContext());
        final ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());

        //autocompletetextview handling
        authorVector = cfa.showAllAuthors();
        authorsArray = cfa.toArrayOfStrings(authorVector);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, authorsArray);

        authorTV = findViewById(R.id.authorATV);
        authorTV.setText("");
        authorTV.setAdapter(adapter);
        authorTV.setThreshold(2);
        authorTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //method for figuring out the authorid
                for (int i = 0; i < authorsArray.size(); i++) {
                    if (authorsArray.get(i).equals(adapter.getItem(position))) {
                        authorId = authorVector.get(i).getId();
                        break;
                    }
                }

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("BookId")){
                //if activity started as card for existing book
                Intent intent = getIntent();
                bookId = Integer.parseInt(intent.getStringExtra("BookId"));
                add.setVisibility(View.INVISIBLE);
                update.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);

                Vector<Book> bv = cfb.selectBooks(bookId.toString(),7);
                Author author = cfa.searchAuthorById(bv.get(0).getAuthorid());
                authorTV.setText(author.toString());
                authorId = bv.get(0).getAuthorid();
                title.setText(bv.get(0).getTitle());
                publisher.setText(bv.get(0).getPublisher());
                issueYear.setText(bv.get(0).getIssueYear().toString());
                isbn.setText(bv.get(0).getIsbnNo());
                localization.setText(bv.get(0).getLocalization());
                whoLent.setText(bv.get(0).getWhoLent());
                tags.setText(bv.get(0).getTags());
                notes.setText(bv.get(0).getNotes());
                if(bv.get(0).getIsLent().equals("Dostępna")){
                    isLent.setSelection(0);
                }
                else isLent.setSelection(1);
            }

        }
        else {
            //if activity started as page for adding new book
            add.setVisibility(View.VISIBLE);
            update.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
        }
        if(!LoginScreen.isLogged)
        {
            //if user is not logged, don't show him any buttons and disable EditTexts
            add.setVisibility(View.INVISIBLE);
            update.setVisibility(View.INVISIBLE);
            delete.setVisibility(View.INVISIBLE);
            Button addAuthor = findViewById(R.id.bookAddAuthorButton);
            addAuthor.setVisibility(View.INVISIBLE);

            title.setInputType(InputType.TYPE_NULL);
            publisher.setInputType(InputType.TYPE_NULL);
            issueYear.setInputType(InputType.TYPE_NULL);
            isbn.setInputType(InputType.TYPE_NULL);
            localization.setInputType(InputType.TYPE_NULL);
            isLent.setEnabled(false);
            whoLent.setVisibility(View.INVISIBLE);
            tags.setInputType(InputType.TYPE_NULL);
            notes.setInputType(InputType.TYPE_NULL);
            authorTV.setInputType(InputType.TYPE_NULL);
        }
        Resources resources = getResources();
        lentOrNot = resources.getStringArray(R.array.lent_not_lent);
        final TextView whoLentTV = findViewById(R.id.whoLentTV);
        final EditText whoLentField = findViewById(R.id.bookWhoLentField);
        final Spinner isLentSpinner = findViewById(R.id.isLentSpinner);
        isLentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isLentSpinner.getSelectedItem().toString().equals(lentOrNot[1])) {
                    //if book was lent, show who lent it
                    whoLentField.setVisibility(View.VISIBLE);
                    whoLentTV.setVisibility(View.VISIBLE);

                } else {
                    whoLentField.setVisibility(View.INVISIBLE);
                    whoLentTV.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addBook(View view) {
        ConnectorForBooks cfb = new ConnectorForBooks(getApplicationContext());
        Book book = prepareBook();
        if(book!=null){
            boolean isInserted = cfb.addBook(book);
            if(isInserted){
                Toast.makeText(getApplicationContext(),"Poprawnie dodano książkę!",Toast.LENGTH_SHORT).show();
                finish();
            }
            else Toast.makeText(getApplicationContext(),"Nie udało się dodać książki",Toast.LENGTH_SHORT).show();
        }
        //else  Toast.makeText(getApplicationContext(),"To nie powinno się pojawić",Toast.LENGTH_SHORT).show();

    }

    public void updateBook(View view){
        ConnectorForBooks cfb = new ConnectorForBooks(getApplicationContext());
        Book book = prepareBook();

        if(book!=null){

            boolean isUpdated = cfb.updateBook(book);
            if(isUpdated) {
                Toast.makeText(getApplicationContext(),"Poprawnie zaktualizowano książkę!",Toast.LENGTH_SHORT).show();
            }
            else  Toast.makeText(getApplicationContext(),"Nie udało się zaktualizować książki",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //updating list of authors after adding a new one
        adapter.clear();
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        authorVector = cfa.showAllAuthors();
        authorsArray = cfa.toArrayOfStrings(authorVector);
        for (String s : authorsArray){
            adapter.add(s);
        }
    }

    public void deleteBook(View view){
        final ConnectorForBooks cfb = new ConnectorForBooks(getApplicationContext());
        Dialog.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==Dialog.BUTTON_POSITIVE){
                    boolean isDeleted = cfb.deleteBook(bookId);
                    if(isDeleted){
                        Toast.makeText(getApplicationContext(),"Poprawnie usunięto książkę!",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else Toast.makeText(getApplicationContext(),"Nie udało się usunąć książki",Toast.LENGTH_SHORT).show();
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy na pewno usunąć książkę?").setNegativeButton("Nie",listener).setPositiveButton("Tak",listener).show();

    }

    public void addAuthor(View view){
        Intent intent = new Intent(this,AuthorCard.class);
        startActivity(intent);
    }

    private Book prepareBook() {
        //method to prepare book for insertion
        EditText title = findViewById(R.id.bookTitlefField);
        EditText publisher = findViewById(R.id.bookPublisherField);
        EditText issueYear = findViewById(R.id.bookIssueYearField);
        EditText isbn = findViewById(R.id.bookIsbnField);
        EditText localization = findViewById(R.id.bookLocalizationField);
        EditText whoLent = findViewById(R.id.bookWhoLentField);
        EditText tags = findViewById(R.id.bookTagsField);
        EditText notes = findViewById(R.id.bookNotesField);
        Spinner isLent = findViewById(R.id.isLentSpinner);

        if(isBookReady()) {
        String bookTitle = title.getText().toString();
        String bookPublisher = publisher.getText().toString();

            Integer bookIssueYear = Integer.parseInt(issueYear.getText().toString());

            String bookIsbn = isbn.getText().toString();
            String bookTags = tags.getText().toString();
            String bookNotes = notes.getText().toString();

            String bookIsLent = isLent.getSelectedItem().toString();
            String bookWhoLent;
            if(isLent.getSelectedItem().toString().equals(lentOrNot[1])) {
               bookWhoLent = whoLent.getText().toString();
            }
            else bookWhoLent = "";
            String bookLocalization = localization.getText().toString();


                if (bookId != null) {
                    return new Book(bookId, bookTitle, bookPublisher, bookIssueYear, bookIsbn, authorId, bookTags, bookNotes,
                            bookIsLent, bookWhoLent, bookLocalization);
                }
                return new Book(bookTitle, bookPublisher, bookIssueYear, bookIsbn, authorId, bookTags, bookNotes, bookIsLent, bookWhoLent, bookLocalization);
            }
            return null;
    }

    private boolean isBookReady(){
        //method to validate data
        EditText title = findViewById(R.id.bookTitlefField);
        AutoCompleteTextView authorTV = findViewById(R.id.authorATV);
        EditText publisher = findViewById(R.id.bookPublisherField);
        EditText issueYear = findViewById(R.id.bookIssueYearField);
        EditText tags = findViewById(R.id.bookTagsField);
        EditText whoLent = findViewById(R.id.bookWhoLentField);
        Spinner isLent = findViewById(R.id.isLentSpinner);
        //Toast.makeText(getApplicationContext(),"tada",Toast)

        if(title.getText().toString().equals("")||publisher.getText().toString().equals("")||issueYear.getText().toString().equals("")||
                !issueYear.getText().toString().matches("\\d+")|| tags.getText().toString().equals("")||
                authorTV.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Proszę wypełnić poprawnymi danymi pola \"Tytuł\", \"Autor\",\"Wydawnictwo\",\"Rok publikacji\" i" +
                    " \"Tagi\"",Toast.LENGTH_LONG).show();
            return false;
        }
        if(authorId==null) {
            Toast.makeText(getApplicationContext(), "Proszę poprawić pole \"Autor\". Jeżeli autora nie ma na liście podpowiedzi," +
                    " należy go dodać przyciskiem \"Dodaj\"", Toast.LENGTH_LONG).show();
            return false;
        }
        if(isLent.getSelectedItem().toString().equals(lentOrNot[1])&&whoLent.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Pola \"Stan\" i \"Wypożyczył\" nie zgadzają się.",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
