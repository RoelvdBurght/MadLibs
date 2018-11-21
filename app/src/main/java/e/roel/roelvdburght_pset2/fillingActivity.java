package e.roel.roelvdburght_pset2;

import android.content.Context;
import  android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class fillingActivity extends AppCompatActivity {

    Story story;
    EditText entryField;
    int remainingCount;
    TextView remainingText;
    Button nextButton;
    Boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling);

        // Get story object from intent and check how many words are needed, update this to activity
        story = (Story) getIntent().getSerializableExtra("story");
        remainingCount = story.getPlaceholderCount();
        remainingText = findViewById(R.id.remainingText);
        String remainingTextFill = remainingCount + " words left";
        remainingText.setText(remainingTextFill);

        // Start listening for the buttons and init entry field object with hint
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new SaveButtonListener());
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new NextButtonListener());
        nextButton.setVisibility(View.INVISIBLE);
        entryField = findViewById(R.id.fillForm);
        entryField.setHint(story.getNextPlaceholder().toLowerCase());

        // Start listening for hintbutton
        Button hintButton = findViewById(R.id.hintButton);
        hintButton.setOnClickListener(new HintButtonListener());

        // Check if the first word to fill in is a adjective, noun or plural noun
        // and set visibility of button accordingly
        setHint(story.getNextPlaceholder());
    }

    // Listener class for save button
    private class SaveButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // checks if there is any input, if not sends an error message to the user
            if (!entryField.getText().toString().equals("")){
                saveButtonClicked(v);
            }
            else {
                Context context = getApplicationContext();
                CharSequence text = "Please fill in a word before saving";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }

    // Listener for the next button
    private class NextButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            nextButtonClicked(v);
        }
    }

    // Listener for the hintbutton
    private class HintButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            hintClick(v);
        }
    }

    // On save button click do the following: get the text from the textfield and save it to the
    // story, and update the remaining word counter and text field. If the story is filled a button
    // becomes visible to go to the next activity
    private void saveButtonClicked(View v) {
        // resets the hintbutton and hint textview visibility
        TextView hintText = findViewById(R.id.hintTextview);
        hintText.setVisibility(View.INVISIBLE);
        Button hintButton = findViewById(R.id.hintButton);
        hintButton.setVisibility(View.INVISIBLE);


        String text = entryField.getText().toString().toLowerCase();
        text = "<b>" + text + "</b>";
        entryField.setText("");
        story.fillInPlaceholder(text);
        entryField.setHint(story.getNextPlaceholder().toLowerCase());
        remainingCount --;

        // Check how many words are left, if there is only one left the diplayed message will be
        // 1 word left, not 1 wordS
        String textRemaining;
        if (remainingCount != 1 && !finished) {
            textRemaining = remainingCount + " words left";
            remainingText.setText(textRemaining);
        }
        if (remainingCount == 1 && !finished){
            textRemaining = remainingCount + " word left";
            remainingText.setText(textRemaining);
        }

        // if the story is filled the button to proceed to the next screen becomes visible
        if (story.isFilledIn()) {
            nextButton.setVisibility(View.VISIBLE);
            finished = true;
        }

        // Checks if the hint option should be displayed for the next placeholder
        setHint(story.getNextPlaceholder().toLowerCase());
    }

    // If the next word to be written is a adjective, noun or plural noun, display the hint button
    // It loads the text into the textview and makes the button visible
    private void setHint(String s) {
        Button hintButton = findViewById(R.id.hintButton);
        TextView hintText = findViewById(R.id.hintTextview);

        if(s.equals("adjective")) {
            int adjectId = this.getResources().getIdentifier("@string/adjective",
                    "string", this.getPackageName());
            int buttonTextId = this.getResources().getIdentifier("@string/button_adjective",
                    "string", this.getPackageName());
            hintButton.setText(buttonTextId);
            hintText.setText(adjectId);
            hintButton.setVisibility(View.VISIBLE);
        }

        if(s.equals("noun")) {
            int nounId = this.getResources().getIdentifier("@string/noun",
                    "string", this.getPackageName());
            int buttonTextId = this.getResources().getIdentifier("@string/button_noun",
                    "string", this.getPackageName());
            hintButton.setText(buttonTextId);
            hintText.setText(nounId);
            hintButton.setVisibility(View.VISIBLE);
        }

        if(s.equals("plural noun")) {
            int pluralNounId = this.getResources().getIdentifier("@string/plural_noun",
                    "string", this.getPackageName());
            int buttonTextId = this.getResources().getIdentifier("@string/button_plural_noun",
                    "string", this.getPackageName());
            hintText.setText(pluralNounId);
            hintButton.setText(buttonTextId);
            hintButton.setVisibility(View.VISIBLE);
        }
    }

    // When all the placeholders are filled in, this button takes you to the story screen
    private void nextButtonClicked(View v) {
        Intent finalIntent = new Intent(this, finalActivity.class);
        finalIntent.putExtra("story", story);
        startActivity(finalIntent);
    }

    private void hintClick(View v) {
        Log.d("tag", "sdvfjvsjkdfgskdjvfbkse");
        TextView hintText = findViewById(R.id.hintTextview);
        hintText.setVisibility(View.VISIBLE);
    }
}
