package bibliotech4android.com.bibliotechforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void addAuthor(View view){
        Intent intent = new Intent(this,AddAuthor.class);
        startActivity(intent);
    }
    public void showAuthors(View view){
        Intent intent = new Intent(this,SearchResults.class);
        startActivity(intent);
    }
}

