package ua.com.adr.android.spravichnik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView tv_detail = findViewById(R.id.tv_detail);

        Intent intent = getIntent();
        String descText = intent.getStringExtra("WOW");

        tv_detail.setText(descText);

    }
}
