package ar.com.ceritdumbre.com.android.apps.memoryhelper;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.data.Memory;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.data.MemoryHelperDatabaseAdapter;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.utils.AndroidUtils;

public class SearchMemoryActivity extends ListActivity {

	protected EditText searchText;
	protected Button searchButton;

	private static final int MEMORY_DELETE = 1;
	private static final int MEMORY_EDIT = 2;

	/** Called when the activity is first created. **/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_memory);

		searchText = (EditText) findViewById(R.id.searchText);
		searchButton = (Button) findViewById(R.id.search_Button);

		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				search(v);
			}
		});

		registerForContextMenu(getListView());

	}

	public void search(View view) {
		Cursor cursor = MemoryHelperDatabaseAdapter.getInstance(this).find(
				"WHERE memory LIKE ?",
				new String[] { "%" + searchText.getText() + "%" });
		Log.d("search",
				"searching for memories with text [" + searchText.getText()
						+ "]");
		startManagingCursor(cursor);

		List<Memory> memories = new ArrayList<Memory>();
		Memory memory = null;

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.d("search", "adding memory " + cursor.getString(1));
			memory = new Memory();
			memory.setId(cursor.getInt(cursor
					.getColumnIndex(MemoryHelperDatabaseAdapter.KEY_ROWID)));
			memory.setMemory(cursor.getString(cursor
					.getColumnIndex(MemoryHelperDatabaseAdapter.KEY_MEMORY)));
			memory.setCreationDate(cursor.getString(cursor
					.getColumnIndex(MemoryHelperDatabaseAdapter.KEY_CREATION_DATE)));
			memories.add(memory);
			Log.i("adding Memory", memory.toString());
		}

		MemoryAdapter memoryAdapter = new MemoryAdapter(this,
				R.layout.memories_list, memories);

		setListAdapter(memoryAdapter);

		if (cursor.getCount() == 0) {
			AndroidUtils.showAlertDialogWithOkButton(this, "Memory helper",
					"No memories found", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							return;
						}
					});
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(R.string.memory_options);
		menu.add(0, MEMORY_DELETE, Menu.FIRST, R.string.memory_delete);
		menu.add(1, MEMORY_EDIT, Menu.FIRST, R.string.memory_edit);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		final Memory selectedMemory = (Memory) getListAdapter().getItem(
				info.position);

		Log.d("onContextItemSelected", "info.id: " + info.id);
		Log.d("onContextItemSelected", "selectedMemory: " + selectedMemory);

		switch (item.getItemId()) {
		case MEMORY_DELETE:
			AndroidUtils.showConfirmDialog(this, "Memory delete",
					"Delete memory selected?",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							MemoryHelperDatabaseAdapter.getInstance(
									SearchMemoryActivity.this).deleteMemory(
									selectedMemory.getId());
							search(null);
						}
					}, null);

			break;
		case MEMORY_EDIT:
			Intent intent = new Intent(this, EditMemoryActivity.class);
			intent.putExtra("MEMORY_ID", selectedMemory.getId());
			startActivityForResult(intent, MEMORY_EDIT);
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("onActivityResult", "requestCode: " + requestCode);
		Log.d("onActivityResult", "resultCode: " + resultCode);
		switch (requestCode) {
		case MEMORY_EDIT:
			Log.d("onActivityResult", "refreshing serch...");
			search(null);
			break;
		}
	}

}