package bibliotech4android.com.bibliotechforandroid;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String[] authorOrBookContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method extracting array from searchbar_contents.xml
        Resources resources = getResources();
        authorOrBookContent = resources.getStringArray(R.array.author_or_book_content);

        TextView isLogged = findViewById(R.id.isLoggedTextView);
        Button login = findViewById(R.id.loginButton);
        Button addAuthor = findViewById(R.id.addAuthorButton);
        Button addBook = findViewById(R.id.addBookButton);

        if(LoginScreen.isLogged){
            isLogged.setText("Zalogowano");
            isLogged.setTextColor(Color.BLACK);
            login.setText("Wyloguj");
            addAuthor.setVisibility(View.VISIBLE);
            addBook.setVisibility(View.VISIBLE);
        }
        else{
            isLogged.setText("Niezalogowano");
            isLogged.setTextColor(Color.RED);
            login.setText("Zaloguj się");
            addAuthor.setVisibility(View.INVISIBLE);
            addBook.setVisibility(View.INVISIBLE);
        }

        final Spinner authorOrBook = findViewById(R.id.AuthorOrBookSpinner);
        final Spinner bookMisc = findViewById(R.id.bookMiscSpinner);
        EditText searchField = findViewById(R.id.searchField);
        searchField.setText("");

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(authorOrBook.getSelectedItem().equals(authorOrBookContent[1])){
                    bookMisc.setEnabled(false);

                }
                else bookMisc.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        authorOrBook.setOnItemSelectedListener(listener);
    }

    public void addAuthor(View view){
        Intent intent = new Intent(this,AuthorCard.class);
        startActivity(intent);
    }
    public void showAuthors(View view){
        Intent intent = new Intent(this,BookCard.class);
        startActivity(intent);
    }
    public void login(View view){
        Button login = findViewById(R.id.loginButton);

        if(login.getText().equals("Zaloguj się")){
        Intent intent = new Intent(this,LoginScreen.class);
        startActivity(intent);
        finish();
        }
        else{
            LoginScreen.isLogged = false;
            finish();
            startActivity(getIntent());
        }
    }
    public void search(View view){
        Spinner bookMisc = findViewById(R.id.bookMiscSpinner);
        EditText searchField = findViewById(R.id.searchField);
        Intent intent = new Intent(this,SearchResults.class);
        intent.putExtra("what",searchField.getText().toString());
        if(bookMisc.isEnabled()) {
            intent.putExtra("misc",String.valueOf(bookMisc.getSelectedItemId()));
        }
        startActivity(intent);
    }
}

