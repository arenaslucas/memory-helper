package ar.com.ceritdumbre.com.android.apps.memoryhelper.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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
import ar.com.ceritdumbre.com.android.apps.memoryhelper.MemoryHelperApplication;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.R;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.adapter.MemoryAdapter;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.model.Memory;
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
		MemoryHelperApplication application = (MemoryHelperApplication) this
				.getApplication();

		List<Memory> memories = application.getMemoryData().findByMemory(
				searchText.getText().toString());

		MemoryAdapter memoryAdapter = new MemoryAdapter(this,
				R.layout.memories_list, memories);

		setListAdapter(memoryAdapter);

		if (memories.size() == 0) {
			AndroidUtils.showAlertDialogWithOkButton(this,
					getString(R.string.app_name),
					getString(R.string.message_no_memories_found),
					new DialogInterface.OnClickListener() {

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
		menu.setHeaderTitle(R.string.options);
		menu.add(0, MEMORY_DELETE, Menu.FIRST, R.string.delete_memory);
		menu.add(1, MEMORY_EDIT, Menu.FIRST, R.string.edit_memory);
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
			AndroidUtils.showConfirmDialog(this,
					getString(R.string.delete_memory),
					getString(R.string.message_delete_memory_confirm),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							MemoryHelperApplication application = (MemoryHelperApplication) SearchMemoryActivity.this
									.getApplication();
							application.getMemoryData().deleteMemory(
									selectedMemory.getId());
							search(null);
						}
					}, null);

			break;
		case MEMORY_EDIT:
			Intent intent = new Intent(this, EditMemoryActivity.class);
			intent.putExtra(EditMemoryActivity.MEMORY_ID_KEY,
					selectedMemory.getId());
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