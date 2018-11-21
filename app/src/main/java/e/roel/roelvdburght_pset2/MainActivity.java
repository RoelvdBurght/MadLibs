package e.roel.roelvdburght_pset2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start listening for button click to start new story game
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new StartButtonListener());
    }

    // Listener class for the start button
    private class StartButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startButtonClicked(v);
        }
    }

    // This function checks the radio buttons for the chosen story, creates the story object
    // and passes this to the next activity
    private void startButtonClicked(View v) {
        // Check what story is selected
        RadioGroup storyButtons = findViewById(R.id.radioGroup);
        int currentRadioId = storyButtons.getCheckedRadioButtonId();

        // If a story is selected, proceed to check which and continue to next activity
        if (currentRadioId != -1) {
            InputStream is = getResources().openRawResource(R.raw.madlib0_simple);
            switch (currentRadioId) {
                case R.id.simple:
                    is = getResources().openRawResource(R.raw.madlib0_simple);
                    break;

                case R.id.tarzan:
                    is = getResources().openRawResource(R.raw.madlib1_tarzan);
                    break;

                case R.id.university:
                    is = getResources().openRawResource(R.raw.madlib2_university);
                    break;

                case R.id.clothes:
                    is = getResources().openRawResource(R.raw.madlib3_clothes);
                    break;

                case R.id.dance:
                    is = getResources().openRawResource(R.raw.madlib4_dance);
                    break;
            }

            Story story = new Story(is);
            Intent fillingIntent = new Intent(this, fillingActivity.class);
            fillingIntent.putExtra("story", story);
            startActivity(fillingIntent);
        }

        else {
            Context context = getApplicationContext();
            CharSequence text = "Please select a story to start";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
