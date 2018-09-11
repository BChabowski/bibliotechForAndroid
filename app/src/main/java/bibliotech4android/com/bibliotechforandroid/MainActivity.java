package bibliotech4android.com.bibliotechforandroid;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String[] authorOrBookContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method extracting array from searchbar_contents.xml
        Resources resources = getResources();
        authorOrBookContent = resources.getStringArray(R.array.author_or_book_content);

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
    public void search(View view){
        Spinner bookMisc = findViewById(R.id.bookMiscSpinner);
        //Spinner authorOrBook = findViewById(R.id.AuthorOrBookSpinner);
        EditText searchField = findViewById(R.id.searchField);
        Intent intent = new Intent(this,SearchResults.class);
        intent.putExtra("what",searchField.getText().toString());
        //intent.putExtra("where",authorOrBook.getSelectedItem().toString());
        if(bookMisc.isEnabled()) {
            intent.putExtra("misc",String.valueOf(bookMisc.getSelectedItemId()));
            //Toast.makeText(this,"enabled",Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this,intent.getStringExtra("misc"),Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}

