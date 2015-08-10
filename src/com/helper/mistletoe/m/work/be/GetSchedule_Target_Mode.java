package com.helper.mistletoe.m.work.be;

import com.helper.mistletoe.m.db.SchdeuleManager;
import com.helper.mistletoe.m.pojo.Schedule_Bean;
import com.helper.mistletoe.m.pojo.TargetMember_Bean;
import com.helper.mistletoe.util.ExceptionHandle;
import com.helper.mistletoe.util.prcomode.v3.FactoryWork_v3;

public class GetSchedule_Target_Mode extends FactoryWork_v3 {
	private int m_TargetId;
	private String m_TargetTag;
	private transient Schedule_Bean schedule;

	public GetSchedule_Target_Mode(int m_TargetId, String m_TargetTag) {
		try {
			this.m_TargetId = m_TargetId;
			this.m_TargetTag = m_TargetTag;
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	@Override
	public void doWork() {
		try {
			SchdeuleManager dbWorkOj = SchdeuleManager
					.getInstance(getContext());
			schedule = dbWorkOj.readSchdeule(m_TargetId, m_TargetTag);

			schedule.foundCreater(m_TargetId, getContext());
			schedule.getLoc_Creater().foundHelper(getContext());
			schedule.foundReciver();
			for (TargetMember_Bean tI : schedule.getLoc_Sendto()) {
				tI.foundHelper(getContext());
			}
		} catch (Exception e) {
			ExceptionHandle.ignoreException(e);
		}
	}

	public Schedule_Bean getSchedule() {
		if (schedule == null) {
			schedule = new Schedule_Bean();
		}
		return schedule;
	}

}