package e.roel.roelvdburght_pset2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class finalActivity extends AppCompatActivity {

    Story story;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_activity);

        // Get the story and fill the textView with the story
        story = (Story) getIntent().getSerializableExtra("story");
        TextView storyText = findViewById(R.id.storyView);
        storyText.setText(Html.fromHtml(story.toString(), Html.FROM_HTML_MODE_LEGACY));
    }

    // When pressing android back button, this function makes you return to the start screen instead
    // of the filling in screen
    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
