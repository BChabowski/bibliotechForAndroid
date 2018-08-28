package bibliotech4android.com.bibliotechforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class SearchResults extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    RecyclerViewAdapter adapter;
    ArrayList<String> results  = new ArrayList<>();
    Vector<Author> av = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);



        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        av = cfa.showAllAuthors();
        results = cfa.toArrayOfStrings(av);
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvSearchResults);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RecyclerViewAdapter(this, results);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


    }
    @Override
    public void onItemClick(View view, int position) {
        int i = av.get(position).getId();
        Intent intent = new Intent(this,AddAuthor.class);
        intent.putExtra("Position",i);
        startActivity(intent);
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}
