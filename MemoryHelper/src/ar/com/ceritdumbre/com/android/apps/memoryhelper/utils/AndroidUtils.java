package ar.com.ceritdumbre.com.android.apps.memoryhelper.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AndroidUtils {

	public static void showAlertDialogWithOkButton(Context context,
			String title, String message,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", onClickListener);
		alertDialog.show();
	}

	public static void showConfirmDialog(Context context, String title,
			String message,
			DialogInterface.OnClickListener positiveOnClickListener,
			DialogInterface.OnClickListener negaviteOnClickListener) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		alertDialogBuilder.setTitle(title);
		alertDialogBuilder.setMessage(message);
		alertDialogBuilder.setPositiveButton("Yes", positiveOnClickListener);
		alertDialogBuilder.setNegativeButton("No", negaviteOnClickListener);

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}
}
