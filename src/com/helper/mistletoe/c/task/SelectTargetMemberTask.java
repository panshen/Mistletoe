//package com.helper.mistletoe.c.task;
//
//import java.util.ArrayList;
//
//import com.helper.mistletoe.c.ui.SelectTargetMemberActivity;
//import com.helper.mistletoe.m.db.HelperManager;
//import com.helper.mistletoe.m.pojo.ShowData_Bean;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//public class SelectTargetMemberTask extends
//		AsyncTask<String, Integer, ArrayList<ShowData_Bean>> {
//
//	private Context context;
//	private SelectTargetMemberActivity t;
//
//	public SelectTargetMemberTask(
//			SelectTargetMemberActivity selectTargetMemberActivity, Context context) {
//		this.context = context;
//		this.t = selectTargetMemberActivity;
//	}
//
//	@Override
//	protected ArrayList<ShowData_Bean> doInBackground(String... params) {
//		ArrayList<ShowData_Bean> ListHelper = new ArrayList<ShowData_Bean>();
//		ListHelper = HelperManager.ReadShowDataFromDB(context);
//		return ListHelper;
//	}
//
//	@Override
//	protected void onPostExecute(ArrayList<ShowData_Bean> result) {
//		super.onPostExecute(result);
//		t.display(result);
//	}
// }
