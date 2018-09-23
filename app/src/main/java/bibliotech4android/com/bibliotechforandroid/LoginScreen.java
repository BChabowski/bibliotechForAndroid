package bibliotech4android.com.bibliotechforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {
    public static boolean isLogged = false;
    private ConnectorForUser cfu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        Intent intent = getIntent();

        EditText login = findViewById(R.id.loginField);
        EditText favouriteAuthor = findViewById(R.id.loginFavouriteAuthorField);
        EditText password = findViewById(R.id.loginPasswordField);
        EditText repeatPassword = findViewById(R.id.loginRepeatPasswordField);
        Button signInButton = findViewById(R.id.loginSignInButton);
        Button signUpButton = findViewById(R.id.loginSignUpButton);
        Button forgottenPasswordButton = findViewById(R.id.loginForgottenPasswordButton);
        Button resetPasswordButton = findViewById(R.id.loginResetPassButton);

        cfu = new ConnectorForUser(getApplicationContext());
        boolean isSignedUp = cfu.isSignedUp();
        if (isSignedUp) {
            login.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.INVISIBLE);
            password.setVisibility(View.VISIBLE);
            forgottenPasswordButton.setVisibility(View.VISIBLE);
            resetPasswordButton.setVisibility(View.INVISIBLE);
            favouriteAuthor.setVisibility(View.INVISIBLE);
            repeatPassword.setVisibility(View.INVISIBLE);
            if (intent.getBooleanExtra("newpass",false)==true) {
                resetPasswordButton.setVisibility(View.VISIBLE);
                favouriteAuthor.setVisibility(View.VISIBLE);
                login.setVisibility(View.INVISIBLE);
                forgottenPasswordButton.setVisibility(View.INVISIBLE);
                signInButton.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);

            }
        } else {
            signInButton.setVisibility(View.INVISIBLE);
            signUpButton.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            repeatPassword.setVisibility(View.VISIBLE);
            favouriteAuthor.setVisibility(View.VISIBLE);
            resetPasswordButton.setVisibility(View.INVISIBLE);
            forgottenPasswordButton.setVisibility(View.INVISIBLE);
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void login(View view) {
        EditText login = findViewById(R.id.loginField);
        EditText password = findViewById(R.id.loginPasswordField);
        String loginEntered = login.getText().toString();
        String passwordEntered = password.getText().toString();
        boolean isPassOk = cfu.isPassOk(loginEntered, passwordEntered);
        if (isPassOk) {
            Intent intent = new Intent(this,MainActivity.class);
            isLogged = true;
            finish();
            startActivity(intent);
        } else Toast.makeText(this, "Nieprawidłowe hasło i login!", Toast.LENGTH_LONG).show();
    }

    public void signUp(View view) {
        EditText login = findViewById(R.id.loginField);
        EditText password = findViewById(R.id.loginPasswordField);
        EditText repeatPassword = findViewById(R.id.loginRepeatPasswordField);
        EditText favouriteAuthor = findViewById(R.id.loginFavouriteAuthorField);
        String log = login.getText().toString();
        String pass = password.getText().toString();
        String pass2 = repeatPassword.getText().toString();
        String favAut = favouriteAuthor.getText().toString();
        if (pass.equals(pass2) && !pass.equals("") && !log.equals("") && !favAut.equals("")) {
            //add new user
            boolean isAdded = cfu.addUser(log, pass, favAut);
            if (isAdded) {
                Toast.makeText(this, "Poprawnie założono konto", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,LoginScreen.class);
                intent.putExtra("newpass",false);
                finish();
                startActivity(intent);
            } else Toast.makeText(this, "Nie udało się założyć konta", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Proszę uzupełnić wszystkie pola i upewnić się, że oba hasła są takie same", Toast.LENGTH_LONG).show();
    }

    public void resetPassword(View view) {
        EditText favouriteAuthor = findViewById(R.id.loginFavouriteAuthorField);
        boolean isReseted = cfu.resetPass(favouriteAuthor.getText().toString());
        if (isReseted) {
            Toast.makeText(this, "Zresetowano hasło! Proszę ponownie założyć konto", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,LoginScreen.class);
            intent.putExtra("newpass",false);
            finish();
            startActivity(getIntent());
        }
        else
            Toast.makeText(this, "Podano niewłaściwego autora. Proszę zwrócić uwagę na " +
                    "kolejność imienia i nazwiska i wielkie litery", Toast.LENGTH_LONG).show();
    }

    public void forgottenPassword(View view) {
        Intent intent = new Intent(this,LoginScreen.class);
        intent.putExtra("newpass",true);
        finish();
        startActivity(intent);
    }
}
