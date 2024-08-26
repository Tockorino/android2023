package theo.androidproject.medictime.Controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import theo.androidproject.medictime.R;

public class MedicTimeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MedicFragment())
                    .commit();
        }
    }
}