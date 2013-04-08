package ar.com.ceritdumbre.com.android.apps.memoryhelper.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.R;
import ar.com.ceritdumbre.com.android.apps.memoryhelper.utils.AndroidUtils;

public class BaseCrudMemoryActivity extends Activity {

	protected Button memoryButton;
	protected EditText memoryEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memory_form);

		memoryButton = (Button) findViewById(R.id.memory_Button);
		memoryEditText = (EditText) findViewById(R.id.memory_EditText);
	}

	protected boolean validateMemoryEditText() {
		if (memoryEditText.getText() == null
				|| memoryEditText.getText().toString().trim().length() == 0) {
			AndroidUtils.showAlertDialogWithOkButton(this,
					getString(R.string.app_name),
					getString(R.string.validation_not_empty),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							return;
						}
					});
			return false;
		}
		return true;
	}
}
