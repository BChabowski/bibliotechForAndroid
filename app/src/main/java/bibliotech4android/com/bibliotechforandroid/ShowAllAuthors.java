package bibliotech4android.com.bibliotechforandroid;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ShowAllAuthors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_authors);
        TextView tv = findViewById(R.id.textView);
        tv.setText(prepareString());
    }

    private StringBuffer prepareString(){
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        StringBuffer s = new StringBuffer();
        Cursor c = cfa.showAllAuthors();
//        c.moveToFirst();
        if(c.getCount()==0){
            //Toast.makeText(getApplicationContext(),"Nic tu nie ma",Toast.LENGTH_SHORT).show();
            s.append("Wygląda na to, że nic tu nie ma");
        }
        else{

            while(c.moveToNext()){
                s.append(c.getString(2)+", "+c.getString(1)+" ("+c.getInt(3)+" - "+c.getInt(4)+")\n");
            }
        }
        return s;
    }
}
