package com.helper.mistletoe.c.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.helper.mistletoe.R;

public class HealthIndexActivity extends Activity {

	private Button calculate;
	private EditText height, weight;
	private TextView result_w, result_a, result_c;
	private Double heignt_num, weight_num, result_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.health_index);
		initView();
		setListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	protected void initView() {
		calculate = (Button) findViewById(R.id.health_index_button_calculate);
		height = (EditText) findViewById(R.id.health_index_editText_height);
		weight = (EditText) findViewById(R.id.health_index_editText_weight);
		result_c = (TextView) findViewById(R.id.health_index_textView_result_china);
		result_a = (TextView) findViewById(R.id.health_index_textView_result_asia);
		result_w = (TextView) findViewById(R.id.health_index_textView_result_world);
	}

	protected void setListener() {
		calculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String h = height.getText().toString();
				String w = weight.getText().toString();
				if (h.length() > 0) {
					heignt_num = Double.valueOf(h) * 0.01;
				} else {
					heignt_num = 0.0;
				}
				if (w.length() > 0) {
					weight_num = Double.valueOf(w);
				} else {
					weight_num = 0.0;
				}
				if (heignt_num > 0 && weight_num > 0) {
					result_num = weight_num / (heignt_num * heignt_num);
					result_w.setText(healthResultForWorld(result_num));
					result_a.setText(healthResultForAsia(result_num));
					result_c.setText(healthResultForChina(result_num));
				}
			}
		});
	}

	private String healthResultForChina(Double result_num) {
		String result_china = null;
		if (result_num < 18.5) {
			result_china = "中国标准：偏瘦";
		} else if (18.5 <= result_num && result_num <= 23.9) {
			result_china = "中国标准：正常";
		} else if (23.9 < result_num && result_num <= 27.9) {
			result_china = "中国标准：偏胖";
		} else if (27.9 < result_num) {
			result_china = "中国标准：肥胖";
		}
		return result_china;
	}

	private String healthResultForAsia(Double result_num) {
		String result_asia = null;
		if (result_num < 18.5) {
			result_asia = "亚洲标准：偏瘦";
		} else if (18.5 <= result_num && result_num <= 22.9) {
			result_asia = "亚洲标准：正常";
		} else if (22.9 < result_num && result_num <= 24.9) {
			result_asia = "亚洲标准：偏胖";
		} else if (24.9 < result_num && result_num <= 29.9) {
			result_asia = "亚洲标准：肥胖";
		} else if (29.9 < result_num) {
			result_asia = "亚洲标准：重度肥胖";
		}
		return result_asia;
	}

	private String healthResultForWorld(Double result_num) {
		String result_world = null;
		if (result_num < 18.5) {
			result_world = "世界标准：偏瘦";
		} else if (18.5 <= result_num && result_num <= 24.9) {
			result_world = "世界标准：正常";
		} else if (24.9 < result_num && result_num <= 29.9) {
			result_world = "世界标准：偏胖";
		} else if (29.9 < result_num && result_num <= 34.9) {
			result_world = "世界标准：肥胖";
		} else if (34.9 < result_num && result_num <= 39.9) {
			result_world = "世界标准：重度肥胖";
		} else if (39.9 < result_num) {
			result_world = "世界标准：极重度肥胖";
		}
		return result_world;
	}

}