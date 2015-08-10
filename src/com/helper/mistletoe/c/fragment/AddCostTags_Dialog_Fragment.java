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
import com.helper.mistletoe.c.task.AddCostTagsByTargetIdTask;

public class AddCostTags_Dialog_Fragment extends DialogFragment {
	private String id;
	private static String DIALOG_FRAGMENT_DATA = "com.helper.mistletoe.c.fragment.SendEmail_Dialog_Fragment.ID";
	private EditText ed;
	private Button cancle, send;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String s = (String) getArguments().getSerializable(
				DIALOG_FRAGMENT_DATA);
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.addcosttags_dialog, null);
		ed = (EditText) v.findViewById(R.id.addcosttags_editText);
		cancle = (Button) v.findViewById(R.id.addcosttags_Button_cancle);
		send = (Button) v.findViewById(R.id.addcosttags_Button_send);
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AddCostTagsByTargetIdTask geb = new AddCostTagsByTargetIdTask(
							getActivity().getApplicationContext(), Integer
									.decode(s), ed.getText().toString());
					geb.execute();
					getDialog().dismiss();
			}
		});

		ed.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
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

	public static AddCostTags_Dialog_Fragment getFragmet(String id) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(DIALOG_FRAGMENT_DATA, id);
		AddCostTags_Dialog_Fragment sdf = new AddCostTags_Dialog_Fragment();
		sdf.setArguments(bundle);
		return sdf;
	}
}
