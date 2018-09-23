package bibliotech4android.com.bibliotechforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.Vector;

public class SearchResults extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    private RecyclerViewAdapter adapter;
    private ArrayList<String> results  = new ArrayList<>();
    private Vector<Author> authors = new Vector<>();
    private Vector<Book> books = new Vector<>();
    private final Integer AUTHOR = 1;
    private boolean isBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);


        Bundle resources = getIntent().getExtras();
        Intent intent = getIntent();
        if(resources.containsKey("misc")){
            //it's a book!
            isBook = true;
            ConnectorForBooks cfb = new ConnectorForBooks(getApplicationContext());
            String searchQuery = intent.getStringExtra("what");
            String where = intent.getStringExtra("misc");
            Integer column = Integer.parseInt(where);
            if(column==AUTHOR){
                //search books by author
                books = cfb.selectBooks(searchQuery);
            }
            else {
                books = cfb.selectBooks(searchQuery,column);
            }
            results = cfb.toArrayOfStrings(books);
        }
        else {
            //it's an author!
            isBook = false;
            ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
            String searchQuery = intent.getStringExtra("what");
            authors = cfa.selectAuthors(searchQuery);
            results = cfa.toArrayOfStrings(authors);
        }
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvSearchResults);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        adapter = new RecyclerViewAdapter(this, results);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }
    @Override
    public void onItemClick(View view, int position) {
        if(isBook){
            int i = books.get(position).getId();
            Intent intent = new Intent(this,BookCard.class);
            intent.putExtra("BookId",String.valueOf(i));
            startActivity(intent);
            finish();
        }else {
            int i = authors.get(position).getId();
            Intent intent = new Intent(this, AuthorCard.class);
            intent.putExtra("Position", i);
            startActivity(intent);
            finish();
        }
    }

}
