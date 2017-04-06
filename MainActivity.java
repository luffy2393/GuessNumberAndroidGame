package com.example.sevi.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button langu = (Button)findViewById(R.id.lang);
        langu.setOnClickListener(new View.OnClickListener()  {
            public void onClick(View v){
                /*
                This is the button that change the language
                It changes the locale on click and recreate activity in
                order for attachBaseContext to work and set the
                proper string.xml file
                 */
                String language = Locale.getDefault().toString();
                String p ="en_US";
                String t ="en";
                if(language.equals(p)){
                    Locale.setDefault(new Locale ("el", "GR"));
                    recreate();
                }else if (language.equals(t)){
                    Locale.setDefault(new Locale ("el", "GR"));
                    recreate();
                }
                else{
                    Locale.setDefault(new Locale ("en", "US"));
                    recreate();
                }
            }

        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        /*
        every time we create the activity it takes the default locale and parse it
        to the wrapper in order to adjust the xml files properly
        It is used because in API 25 (the latest) , updateConfiguration has become
        deprecated and suggests to use Context
         */
        Locale   newLocale = null;
        Context context = ContextWrapper.wrap(newBase, newLocale.getDefault());
        super.attachBaseContext(context);
    }

    public void onResume() {
        super.onResume();
        final Button guessButton = (Button)findViewById(R.id.guessButton);
        /*
        choose specific button by id and explain what will it do when the user press it
         */
        guessButton.setOnClickListener(new View.OnClickListener() {
            /*
            textView life shows the number of tries have left to the user
             */
            TextView life = (TextView)findViewById(R.id.textView2);
            int max=100;
            int min=1;
            /*
            use Math.random function and with specific adjustments we assign a random integer number
            from 1 to 100 to the randomNumber variable
             */
            final int randomNumber = (int)(int)(Math.random() * (max - min) + min);
            int maxclicks = 5; int currentnumber = 0;
            public void onClick(View v) {
                /*
                 lifes is a string variable that takes the integer currentnumber in order to show it
                  to the user later
                 */


                String lifes = Integer.toString((4-currentnumber));
                if (currentnumber == maxclicks) {
                    /*
                    when the user have uses all of his attempts(5) we disable the button and the
                    editText because he has lost
                    after that we show the correct number
                     */
                    guessButton.setEnabled(false);
                    EditText editText = (EditText)findViewById(R.id.editText);
                    editText.setEnabled(false);
                    String num;
                    num= Integer.toString(randomNumber);
                    life.setText(getText(R.string.correctAnswer)+" :  "+ num);
                    TextView result = (TextView)findViewById(R.id.textView);
                    result.setText(getText(R.string.correctAnswer)+"  :  "+ num);
                }
                else{
                    EditText editText = (EditText)findViewById(R.id.editText);
                    /*
                    declare the type of variable that guessedNumber is and give a starting value
                     to avoid conflicts
                     */
                    int guessedNumber=0;
                    boolean state=true;
                    try {
                        guessedNumber=Integer .parseInt(editText.getText().toString());

                    }catch(NumberFormatException e){
                        state=false;
                    }
                    if (state==false){
                        Context context = getApplicationContext();
                        String text = getString(R.string.rule1);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    if((guessedNumber<101)&&(guessedNumber>0)) {
                        /*
                        when the user have insert a number between 1-100
                        we guide him to find the correct answer and
                        when he finds it there is a congrats message
                         */
                        life.setText(getText(R.string.lif1)+"  "+lifes+"  "+getText(R.string.lif2));
                        String message;
                        int i = 0;
                        if (guessedNumber == randomNumber) {
                            guessButton.setEnabled(false);
                            i = 0;
                            EditText editText2 = (EditText) findViewById(R.id.editText);
                            editText2.setEnabled(false);
                            message = getString(R.string.congrats);
                            String num1;
                            num1 = Integer.toString(randomNumber);
                            life.setText(getText(R.string.correctAnswer)+"  :  " + num1);
                        } else if (guessedNumber < randomNumber) {
                            i = 1;
                            message = getString(R.string.smaller);
                        } else {
                            i = 1;
                            message = getString(R.string.bigger);
                        }

                        TextView result = (TextView) findViewById(R.id.textView);
                        result.setText(message);
                        currentnumber = currentnumber + 1;
                        if ((currentnumber == 5) && (i == 1)) {
                            /*
                            there is another check here because we want the moment the user
                            uses the last life(5) the button and editText to become disable
                            and change the string on textView
                             */
                            EditText editText1 = (EditText) findViewById(R.id.editText);
                            editText1.setEnabled(false);
                            guessButton.setEnabled(false);
                            String num1;
                            num1 = Integer.toString(randomNumber);
                            life.setText(getText(R.string.correctAnswer)+"  :  " + num1);
                            result.setText(getString(R.string.lost));
                        }
                    }else if((state==true)&&((guessedNumber>100)||(guessedNumber<1))){
                        /*
                        When state=true means that you gave a number
                        But in the same time the number is not between 1-100
                        So toast make a pop up to the user to give him specific orders about
                        the rules of the game
                        duration specifies for how long you want the pop up to stay visible
                        */
                        Context context = getApplicationContext();
                        CharSequence text = getString(R.string.rule2);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }


            }
        });
        /*
        The reset button that restart the game with full lives and a new random number
        It basically uses existed java functions as startActivity and Intent
        */
        final Button resetButton = (Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==resetButton) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }
        });
    }
}
