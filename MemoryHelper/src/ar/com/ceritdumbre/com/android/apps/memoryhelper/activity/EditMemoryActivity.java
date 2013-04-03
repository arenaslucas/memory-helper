package ar.com.ceritdumbre.com.android.apps.memoryhelper.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.MemoryHelperApplication;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.R;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.model.Memory;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.utils.AndroidUtils;

public class EditMemoryActivity extends BaseCrudMemoryActivity {

	protected int memoryId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		memoryId = getIntent().getExtras().getInt("MEMORY_ID");

		memoryButton.setText(R.string.edit_memory_Button);
		memoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validateMemoryEditText()) {
					updateMemory();
					AndroidUtils.showAlertDialogWithOkButton(
							EditMemoryActivity.this, "Create Memory",
							"Memory updated",
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

		fillData();

		memoryEditText.setSelection(memoryEditText.getText().toString()
				.length());
	}

	private void fillData() {
		MemoryHelperApplication application = (MemoryHelperApplication) this
				.getApplication();

		Memory memory = application.getMemoryData().findById(memoryId);

		memoryEditText.setText(memory.getMemory());
	}

	public void updateMemory() {
		Log.i("updateMemory", "editing memory "
				+ memoryEditText.getText().toString());

		MemoryHelperApplication application = (MemoryHelperApplication) this
				.getApplication();

		application.getMemoryData().updateMemory(memoryId,
				memoryEditText.getText().toString());

	}

}
