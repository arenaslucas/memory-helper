package ar.com.ceritdumbre.com.android.apps.memoryhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	protected Button mainSearchMemoryButton;
	protected Button mainCreateMemoryButton;
	protected Button exitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		mainCreateMemoryButton = (Button) findViewById(R.id.main_create_memory_Button);
		mainSearchMemoryButton = (Button) findViewById(R.id.main_search_memory_Button);
		exitButton = (Button) findViewById(R.id.exit_Button);

		mainCreateMemoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent createMemoryIntent = new Intent(MainActivity.this,
						CreateMemoryActivity.class);
				startActivity(createMemoryIntent);
			}
		});

		mainSearchMemoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent searchMemoryIntent = new Intent(MainActivity.this,
						SearchMemoryActivity.class);
				startActivity(searchMemoryIntent);
			}
		});

		exitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}


}
