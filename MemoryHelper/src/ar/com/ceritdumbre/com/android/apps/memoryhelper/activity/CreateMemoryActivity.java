package ar.com.ceritdumbre.com.android.apps.memoryhelper.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.MemoryHelperApplication;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.R;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.utils.AndroidUtils;

public class CreateMemoryActivity extends BaseCrudMemoryActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		memoryButton.setText(R.string.create_memory);
		memoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateMemoryEditText()) {
					createMemory();
					AndroidUtils.showAlertDialogWithOkButton(
							CreateMemoryActivity.this,
							getString(R.string.create_memory),
							getString(R.string.message_memory_created),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									finish();
								}
							});

				}
			}
		});

	}

	public void createMemory() {
		Log.i("CreateMemory", "creating memory "
				+ memoryEditText.getText().toString());

		MemoryHelperApplication application = (MemoryHelperApplication) this
				.getApplication();
		application.getMemoryData().insertOrIgnore(
				memoryEditText.getText().toString());
	}
}
