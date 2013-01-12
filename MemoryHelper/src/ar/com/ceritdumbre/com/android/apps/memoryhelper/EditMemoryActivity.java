package ar.com.ceritdumbre.com.android.apps.memoryhelper;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.data.MemoryHelperDatabaseAdapter;
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

		String memory = null;

		Cursor cursor = MemoryHelperDatabaseAdapter.getInstance(this).find(
				" WHERE " + MemoryHelperDatabaseAdapter.KEY_ROWID + " = ?",
				new String[] { "" + memoryId });
		startManagingCursor(cursor);
		if (cursor.moveToFirst()) {
			memory = cursor.getString(cursor
					.getColumnIndex(MemoryHelperDatabaseAdapter.KEY_MEMORY));
		}
		memoryEditText.setText(memory);
	}

	public void updateMemory() {
		Log.i("updateMemory", "editing memory "
				+ memoryEditText.getText().toString());
		MemoryHelperDatabaseAdapter.getInstance(this).updateMemory(memoryId,
				MemoryHelperDatabaseAdapter.KEY_ROWID + " = ?",
				memoryEditText.getText().toString());
	}

}
