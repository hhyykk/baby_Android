package com.keju.baby.activity.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.keju.baby.R;
import com.keju.baby.activity.base.BaseActivity;
import com.keju.baby.util.AndroidUtil;
import com.keju.baby.util.NetUtil;
import com.keju.baby.util.SharedPrefUtil;

/**
 * 登录切换界面
 * 
 * @author Zhoujun
 * @version 创建时间2013-10-25 下午2:33:05
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	private ImageView btnLeft, btnRight;
	private TextView tvTitle;
	private Button btnDoctor, btnBaby;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		findView();
		fillData();
	}

	private void findView() {
		btnLeft = (ImageView) findViewById(R.id.btnLeft);
		btnLeft.setVisibility(View.INVISIBLE);
		btnRight = (ImageView) findViewById(R.id.btnRight);
		btnRight.setVisibility(View.INVISIBLE);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		btnDoctor = (Button) findViewById(R.id.btnDoctor);
		btnBaby = (Button) findViewById(R.id.btnBaby);
		btnDoctor.setOnClickListener(this);
		btnBaby.setOnClickListener(this);
	}

	private void fillData() {
		tvTitle.setText("选择入口");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnDoctor:
				if(SharedPrefUtil.isDoctorFistLogin(LoginActivity.this)){
					if(NetUtil.checkNet(this)){
						openActivity(DoctorLoginActivity.class);
						SharedPrefUtil.setDoctorFistLogined(LoginActivity.this);
					}else{
						showShortToast("第一次登陆医生端必须有网络才能登陆");
					}
				}else{
					openActivity(DoctorLoginActivity.class);
				}
			break;
		case R.id.btnBaby:
			if(SharedPrefUtil.isFistLogin(LoginActivity.this)){
				if(NetUtil.checkNet(this)){
					openActivity(BabyLoginActivity.class);
					SharedPrefUtil.setFistLogined(LoginActivity.this);
				}else{
					showShortToast("第一次登陆婴儿必须有网络才能登陆");
				}
			}else{
				openActivity(BabyLoginActivity.class);
			}
			;
			break;

		default:
			break;
		}

	}
	private long exitTime;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				showShortToast(R.string.try_again_logout);
				exitTime = System.currentTimeMillis();
			} else {
				AndroidUtil.exitApp(this);
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
