package com.helper.mistletoe.c.task;

import java.util.ArrayList;

import com.helper.mistletoe.json.MyJsonWriter;
import com.helper.mistletoe.m.net.request.Find_GroupMember_NetRequest;
import com.helper.mistletoe.m.net.request.Update_Group_Members_NetRequest;
import com.helper.mistletoe.m.pojo.Group_Bean;
import com.helper.mistletoe.util.Instruction_Utils;

import android.content.Context;
import android.os.AsyncTask;

public class UpdataGroupMemberFromaHelperFragmentTask extends AsyncTask<String, Integer, Boolean> {

	private Context context;
	private Update_Group_Members_NetRequest update_Group_Members_NetRequest;
	private Find_GroupMember_NetRequest find_GroupMember_NetRequest;
	private Group_Bean group;
	private Boolean result = false;
	private ArrayList<Integer> newMemberIds;

	public UpdataGroupMemberFromaHelperFragmentTask(Context context, Group_Bean group) {
		this.context = context;
		this.group = group;
		newMemberIds = new ArrayList<Integer>();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			ArrayList<Group_Bean> groupList = new ArrayList<Group_Bean>();
			find_GroupMember_NetRequest = new Find_GroupMember_NetRequest(context);
			groupList = find_GroupMember_NetRequest.doFindGroupById(group.getGroup_id());

			if (groupList.size() > 0) {
				for (int i = 0; i < groupList.size(); i++) {
					if (groupList.get(i).getGroup_member_id() > 0) {
						if (!newMemberIds.contains(groupList.get(i).getGroup_member_id())) {
							newMemberIds.add(groupList.get(i).getGroup_member_id());
						}
					}
				}
			}
			if (!newMemberIds.contains(group.getGroup_member_id())) {
				newMemberIds.add(group.getGroup_member_id());
			}
			String json = MyJsonWriter.getJsonDataForGroup(newMemberIds);
			group.setGroup_memberIds(json);
			update_Group_Members_NetRequest = new Update_Group_Members_NetRequest(context);
			result = update_Group_Members_NetRequest.doUpdateGroupMembers(group);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (result) {// ture为需要更新，false不需要更新
			Instruction_Utils.sendInstrustion(context, Instruction_Utils.SYNCHRONOUS_GROUP_MEMBER, group.getGroup_id());
		}
	}
}
