package ar.com.ceritdumbre.com.android.apps.memoryhelper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.data.MemoryHelperDatabaseAdapter;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.utils.AndroidUtils;

public class CreateMemoryActivity extends BaseCrudMemoryActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		memoryButton.setText(R.string.create_memory_Button);
		memoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateMemoryEditText()) {
					createMemory();
					AndroidUtils.showAlertDialogWithOkButton(
							CreateMemoryActivity.this, "Create memory",
							"Memory created",
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
		MemoryHelperDatabaseAdapter.getInstance(this).createMemory(
				memoryEditText.getText().toString());
	}

}
