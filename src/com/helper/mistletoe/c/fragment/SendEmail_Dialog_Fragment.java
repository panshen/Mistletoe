package com.helper.mistletoe.c.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.helper.mistletoe.R;
import com.helper.mistletoe.c.task.GenerateExcelByTargetIdTask;
import com.helper.mistletoe.util.Tool_Utils;

public class SendEmail_Dialog_Fragment extends DialogFragment {
	private String id;
	private static String DIALOG_FRAGMENT_DATA = "com.helper.mistletoe.c.fragment.SendEmail_Dialog_Fragment.ID";
	private EditText ed;
	private Button cancle, send;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String s = (String) getArguments().getSerializable(
				DIALOG_FRAGMENT_DATA);
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.sendemail_dialog, null);
		ed = (EditText) v.findViewById(R.id.Email_ed);
		cancle = (Button) v.findViewById(R.id.Button_cancle);
		send = (Button) v.findViewById(R.id.Button_send);
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Tool_Utils.isEmail(ed.getText().toString())) {
					GenerateExcelByTargetIdTask geb = new GenerateExcelByTargetIdTask(
							getActivity().getApplicationContext(), Integer
									.decode(s), ed.getText().toString());
					geb.execute();
					getDialog().dismiss();
				} else
					Tool_Utils.showInfo(getActivity().getApplicationContext(),
							"邮箱格式不正确");
			}
		});

		cancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});

		return new AlertDialog.Builder(getActivity()).setView(v).show();
	}

	public static SendEmail_Dialog_Fragment getFragmet(String id) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(DIALOG_FRAGMENT_DATA, id);
		SendEmail_Dialog_Fragment sdf = new SendEmail_Dialog_Fragment();
		sdf.setArguments(bundle);
		return sdf;
	}
}
