package bibliotech4android.com.bibliotechforandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowAllAuthors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_authors);
        TextView tv = findViewById(R.id.textView);
        tv.setText(prepareString());
    }

    private String prepareString(){
        ConnectorForAuthors cfa = new ConnectorForAuthors(getApplicationContext());
        String txt = new String();
        ArrayList<String> al = cfa.toArrayOfStrings(cfa.showAllAuthors());
//        c.moveToFirst();
        if(al.isEmpty()){
            //Toast.makeText(getApplicationContext(),"Nic tu nie ma",Toast.LENGTH_SHORT).show();
            txt+="Wygląda na to, że nic tu nie ma";
        }
        else{

            for (String s : al) {
                txt += s +"\n";
            }
        }
        return txt;
    }
}
