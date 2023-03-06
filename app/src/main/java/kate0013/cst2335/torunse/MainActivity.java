package kate0013.cst2335.torunse;

import static kate0013.cst2335.torunse.R.id.loginButton;
import static kate0013.cst2335.torunse.R.id.passWordField;
import static kate0013.cst2335.torunse.R.id.passWordTextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * variable of type TextView to store the id of TextView
     */
    private TextView textView = null;

    /**
     * variable of type EditText to store the id of editText
     */
    private EditText editText = null;

    /**
     * variable of type Button to hold the id of the button
     */
    private Button loginButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.passWordTextView);
        editText = findViewById(R.id.passWordField);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(click -> {
            String userValue = editText.getText().toString();
            if (checkPasswordComplexity(userValue))
                textView.setText("Your password is complex enough");
            else
                textView.setText("You shall not pass!");
        });
    }

    /**
     * function with if and else cases to check if entered password follows the criteria
     *
     * @param password is the entered value of type string
     * @return returns a boolean value
     */
    private boolean checkPasswordComplexity(String password) {
        boolean isDigitState, isUpperCaseState, isLowerCaseState, foundSpecialState;
        isDigitState = isUpperCaseState = isLowerCaseState = foundSpecialState = false;
        //Iterating through the entered password
                for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c))
                isUpperCaseState = true;
            if (Character.isLowerCase(c))
                isLowerCaseState = true;
            if (Character.isDigit(c))
                isDigitState = true;

            switch (c) {
                case '#':
                case '$':
                case '%':
                case '^':
                case '&':
                case '*':
                case '!':
                case '@':
                case '?':
                    foundSpecialState = true;
                    break;
                default:
                    foundSpecialState = false;
                    break;
            }
        }
        // checking for various password requirements if they exist in the entered password of not
        if (!isUpperCaseState) {
            Toast.makeText(this, "Password requires Uppercase!!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isLowerCaseState) {
            Toast.makeText(this, "Password requires Lowercase!!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isDigitState) {
            Toast.makeText(this, "Password requires digit!!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecialState) {
            Toast.makeText(this, "Password requires special character!!!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(this, "You have a strong password!!!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}