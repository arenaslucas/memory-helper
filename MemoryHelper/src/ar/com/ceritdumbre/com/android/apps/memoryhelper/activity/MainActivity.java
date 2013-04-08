package ar.com.ceritdumbre.com.android.apps.memoryhelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.R;

public class MainActivity extends Activity {

	protected Button buttonSearchMemory;
	protected Button buttonCreateMemory;
	protected Button buttonExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		buttonCreateMemory = (Button) findViewById(R.id.button_createMemory);
		buttonSearchMemory = (Button) findViewById(R.id.button_searchMemory);
		buttonExit = (Button) findViewById(R.id.button_exit);

		buttonCreateMemory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent createMemoryIntent = new Intent(MainActivity.this,
						CreateMemoryActivity.class);
				startActivity(createMemoryIntent);
			}
		});

		buttonSearchMemory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent searchMemoryIntent = new Intent(MainActivity.this,
						SearchMemoryActivity.class);
				startActivity(searchMemoryIntent);
			}
		});

		buttonExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

}
