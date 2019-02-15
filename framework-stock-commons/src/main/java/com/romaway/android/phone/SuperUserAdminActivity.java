package com.romaway.android.phone;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.romaway.activity.basephone.BaseSherlockActivity;
import com.romaway.android.phone.config.RomaSysConfig;
import com.romaway.android.phone.utils.JYStatusUtil;
import com.romaway.common.android.base.Res;
import com.romaway.common.android.base.data.SharedPreferenceUtils;
import com.romaway.android.phone.config.Configs;
import com.romaway.android.phone.widget.SysInfo;
import com.romaway.common.net.serverinfo.ServerInfo;
import com.romaway.common.net.serverinfo.ServerInfoMgr;
import com.romaway.common.protocol.ProtocolConstant;
import com.romaway.commons.lang.NumberUtils;
import com.romaway.commons.lang.StringUtils;
import com.romaway.commons.log.Logger;
import com.trevorpage.tpsvg.SVGParserRenderer;

import java.util.List;

import roma.romaway.commons.android.config.ConfigsManager;
import roma.romaway.commons.android.h5download.H5Info;

/**
 * 超级用户界面
 * @author DELL
 *
 */
public class SuperUserAdminActivity extends BaseSherlockActivity implements OnClickListener {

	// Key常量:
	public static final String TEST_AUTH_ADDRESS = "Test_AuthAddress";
	public static final String TEST_AUTH_ADDRESS_PORT = "Test_AuthAddressPort";
	public static final String TEST_HANGQING_ADDRESS = "Test_HangqingAddress";
	public static final String TEST_TRADE_ADDRESS = "Test_tradeAddress";
	public static final String TEST_ZIXUN_ADDRESS = "Test_zixunAddress";

	private TextView tv_inner_h5_version,tv_current_h5_version;
	private EditText  edt_renzheng_server_address, edt_renzheng_addr_portNum, edt_hangqing_server_address, edt_zixun_server_address, edt_trade_address;
	// 编辑框数组及对应服务器类型数组:
	private EditText[] super_addEdt;
	private int[] serverType;
	private RadioButton rb_beta;
	private RadioButton rb_online;
	
	private RadioButton rb_jiaoyi_debug_beta;
    private RadioButton rb_jiaoyi_debug_online;
    
    private RadioButton rb_debug_false_online;
    private RadioButton rb_debug_true_beta;
    
    private RadioButton rb_jiaoyi_zidai;
    private RadioButton rb_jiaoyi_update;

	private ToggleButton roma_renzheng_btn, roma_hangqing_btn, roma_zixun_btn, roma_jiaoyi_btn;
	private ToggleButton[] roma_btn = new ToggleButton[]{roma_renzheng_btn, roma_hangqing_btn, roma_zixun_btn,roma_jiaoyi_btn};
	private int[] avg_btn = new int[]{R.id.roma_renzheng_btn, R.id.roma_hangqing_btn, R.id.roma_zixun_btn,R.id.roma_jiaoyi_btn};
    
	private Button btn_cancle, btn_confirm;

	public SuperUserAdminActivity() {
		super();
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.super_user_admin);

        tv_inner_h5_version = (TextView) this.findViewById(R.id.roma_H5_inner_version_show);
        String innerVersion = H5Info.getAssetsCurrVersion(this);
        tv_inner_h5_version.setText(StringUtils.isEmpty(innerVersion)?"无内置版本" : innerVersion);
        tv_current_h5_version= (TextView) this.findViewById(R.id.roma_H5_current_version_show);
        tv_current_h5_version.setText(H5Info.getCurrVersion(this));
        
        //app DEBUG模式设定
        rb_debug_false_online = (RadioButton) this.findViewById(R.id.rb_debug_false_online);
        rb_debug_true_beta = (RadioButton) this.findViewById(R.id.rb_debug_true_beta);
        if(RomaSysConfig.getIp().equals(Res.getString(R.string.NetWork_TEST_IP))){
            rb_debug_true_beta.setChecked(true);//DEBUG模式
        }else{
            rb_debug_false_online.setChecked(true); //非DEBUG模式
        }
        
        //认证
        edt_renzheng_server_address = (EditText) this.findViewById(R.id.edt_renzheng_server_address);
        edt_renzheng_server_address.setText(RomaSysConfig.getIp());
		edt_renzheng_addr_portNum = (EditText) this.findViewById(R.id.edt_renzheng_addr_portNum);
		edt_renzheng_server_address.setEnabled(true);
        //行情
        edt_hangqing_server_address = (EditText) this.findViewById(R.id.edt_hangqing_server_address);
		edt_hangqing_server_address.setVisibility(View.GONE);
		try {
			edt_hangqing_server_address.setText(RomaSysConfig.getIp());
		}catch (Exception e){
			SysInfo.showMessage(this, "站点配置异常: 未找到可用行情站点");
		}

        //资讯
        edt_zixun_server_address = (EditText) this.findViewById(R.id.edt_zixun_server_address);
		edt_zixun_server_address.setVisibility(View.GONE);
		try{
			edt_zixun_server_address.setText(RomaSysConfig.getIp());
		} catch (Exception e) {
			SysInfo.showMessage(this, "站点配置异常: 未找到可用资讯站点");
		}

		//交易
        edt_trade_address = (EditText) this.findViewById(R.id.edt_trade_address);
		edt_trade_address.setVisibility(View.GONE);
//		try{
//			setDefaultTradeAddress(edt_trade_address, ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_TRADING));
//		} catch (Exception e) {
//			SysInfo.showMessage(this, "站点配置异常: 未找到可用交易站点");
//		}

		// 初始化输入框数据组:
		super_addEdt = new EditText[]{edt_renzheng_server_address, edt_hangqing_server_address, edt_zixun_server_address, edt_trade_address};
		serverType = new int[]{ProtocolConstant.SERVER_FW_AUTH, ProtocolConstant.SERVER_FW_QUOTES, ProtocolConstant.SERVER_FW_NEWS, ProtocolConstant.SERVER_FW_TRADING};
        	
        rb_beta = (RadioButton) this.findViewById(R.id.rb_beta);
        rb_online = (RadioButton) this.findViewById(R.id.rb_online);
        Logger.i("tag", "ConfigsManager.isOnline():"+ConfigsManager.isOnline());
        if(ConfigsManager.isOnline()){
        	rb_online.setChecked(true);
        	rb_beta.setChecked(false);
        }else{
            rb_online.setChecked(false);
        	rb_beta.setChecked(true);
        }
        
        //交易模块DEBUG模式设定
        rb_jiaoyi_debug_beta = (RadioButton) this.findViewById(R.id.rb_jiaoyi_beta);
        rb_jiaoyi_debug_online = (RadioButton) this.findViewById(R.id.rb_jiaoyi_online);
        if(SharedPreferenceUtils.getPreference(
                SharedPreferenceUtils.DATA_CONFIG, 
                "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_ASSETS) == Configs.URL_TYPE_ASSETS){
            rb_jiaoyi_debug_online.setChecked(true);
        }
        else if(SharedPreferenceUtils.getPreference(
                SharedPreferenceUtils.DATA_CONFIG, 
                "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_ASSETS) == Configs.URL_TYPE_SDCARD){
            rb_jiaoyi_debug_beta.setChecked(true);
        }
        
        
        btn_cancle = (Button) this.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(this);
        btn_confirm = (Button) this.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        
       rb_jiaoyi_zidai= (RadioButton) this.findViewById(R.id.rb_jiaoyi_zidai);
       rb_jiaoyi_update= (RadioButton) this.findViewById(R.id.rb_jiaoyi_update);
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ActionBar mActionBar = getSupportActionBar();
		if (mActionBar != null ) {
			mActionBar.setTitle("超级用户");
//			mActionBar.setLeftSvgIcon(
//					new SVGParserRenderer(this, R.drawable.abs__navigation_back));
		}
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if(v.equals(btn_confirm)){
			//设置行情服务器地址
			String newAddress = edt_hangqing_server_address.getText().toString();
			if(!StringUtils.isEmpty(newAddress)){
				if(!newAddress.startsWith("http")){
					newAddress = "http://" + newAddress;
				}
				ServerInfo s = new ServerInfo(newAddress, ProtocolConstant.SERVER_FW_QUOTES, "", newAddress, false, ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_QUOTES).getHttpsPort());
				ServerInfoMgr.getInstance().setDefaultServerInfo(s);
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG, TEST_HANGQING_ADDRESS, edt_hangqing_server_address.isEnabled() ? newAddress : "");
			}

			//设置交易服务器地址
			String tradeUrl = edt_trade_address.getText().toString();
			if (!StringUtils.isEmpty(tradeUrl)) {
				if (!tradeUrl.contains("http")) {
					tradeUrl = tradeUrl.replace("http://", "https://");
				}
				int httpsPort = 21900;
				int index = tradeUrl.lastIndexOf(":");
				if (index > 5) {
					httpsPort = NumberUtils.toInt(tradeUrl.substring(index, tradeUrl.length()), ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_TRADING).getHttpsPort());
				}
				ServerInfo jyServerInfo = new ServerInfo(tradeUrl, ProtocolConstant.SERVER_FW_TRADING, tradeUrl, tradeUrl, false, httpsPort);
				ServerInfoMgr.getInstance().setDefaultServerInfo(jyServerInfo);
				JYStatusUtil.isChangePTJYUrl = true;
				JYStatusUtil.isChangeRZRQUrl = true;
				Configs.setTradeTestUrl(edt_trade_address.isEnabled() ? tradeUrl : "");
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG, TEST_TRADE_ADDRESS, edt_trade_address.isEnabled() ? tradeUrl : "");
			}

			//设置资讯服务器地址
			String zxUrl = edt_zixun_server_address.getText().toString();
			if (!StringUtils.isEmpty(zxUrl)) {
				if(!zxUrl.startsWith("http")){
					zxUrl = "http://" + zxUrl;
				}
				ServerInfo s = new ServerInfo(zxUrl, ProtocolConstant.SERVER_FW_NEWS, "", zxUrl, false,
						ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_QUOTES).getHttpsPort());
				ServerInfoMgr.getInstance().setDefaultServerInfo(s);
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG, TEST_ZIXUN_ADDRESS, edt_zixun_server_address.isEnabled() ? zxUrl : "");
			}

			//设置是正式配置信息还是测试配置信息
			if (rb_beta.isChecked()) {//测试
				ConfigsManager.setOnline(false);
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG, TEST_AUTH_ADDRESS, Res.getString(R.string.NetWork_TEST_IP));
			}
			if (rb_online.isChecked()) {//正式
				ConfigsManager.setOnline(true);
				SharedPreferenceUtils.setPreference(SharedPreferenceUtils.DATA_CONFIG, TEST_AUTH_ADDRESS, Res.getString(R.string.NetWork_ONLINE_IP));
			}
			
			//设置H5交易模块的额调试模式
            if (rb_jiaoyi_debug_beta.isChecked()) {//测试
                SharedPreferenceUtils.setPreference(
                        SharedPreferenceUtils.DATA_CONFIG, 
                        "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_SDCARD);
                finish();//
                return;//lichuan add 避免冲突
            }else
            if (rb_jiaoyi_debug_online.isChecked()) {//正式
                SharedPreferenceUtils.setPreference(
                        SharedPreferenceUtils.DATA_CONFIG, 
                        "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_ASSETS);
            }
			
            //APP debug模式设置
            if(rb_debug_false_online.isChecked()){
                SharedPreferenceUtils.setPreference(
                        SharedPreferenceUtils.DATA_CONFIG, 
                        "APP_DEBUG_MODE",false);
                
            }else if(rb_debug_true_beta.isChecked()){
                SharedPreferenceUtils.setPreference(
                        SharedPreferenceUtils.DATA_CONFIG, 
                        "APP_DEBUG_MODE", true);
            }
            //lichuan  后来加的
            if(rb_jiaoyi_zidai.isChecked()){
            	SharedPreferenceUtils.setPreference(
                        SharedPreferenceUtils.DATA_CONFIG, 
                        "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_ASSETS);
            }else if(rb_jiaoyi_update.isChecked()){
            	SharedPreferenceUtils.setPreference(
                        SharedPreferenceUtils.DATA_CONFIG, 
                        "JIAO_YI_DEBUG_ONLINE", Configs.URL_TYPE_SDCARD_UPDATE_H5);
            }

			finish();
		}else if(v.equals(btn_cancle)){
			finish();
		}
	}

	private String authServerName;
	public class OnItemCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{
		private int itemID;
		public OnItemCheckedChangeListener(int itemID){
			this.itemID = itemID;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			buttonView.setChecked(isChecked);
			super_addEdt[itemID].setEnabled(!roma_btn[itemID].isChecked());

			if(!super_addEdt[itemID].isEnabled()) {
				switch (serverType[itemID]){
					case ProtocolConstant.SERVER_FW_AUTH:
						if(!super_addEdt[itemID].getText().toString().equalsIgnoreCase(ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_AUTH).getUrl())) {
							String[] serverNames = Res.getStringArray(R.array.defaultservernames);
							String[] serverUrls = Res.getStringArray(R.array.defaultserveraddress);
							int[] httpsPort = Res.getIngegerArray(R.array.defaulthttpsport);
							super_addEdt[itemID].setText(serverUrls[0]);
							edt_renzheng_addr_portNum.setText(String.valueOf(httpsPort[0]));
							authServerName = serverNames[0];
						}
						break;
					case ProtocolConstant.SERVER_FW_TRADING:
					default:
						// 根据当前认证站点获取对应的服务器类型站点作为修改站点;
						List<ServerInfo> list = ServerInfoMgr.getInstance().getServerInfosByUrl(
								ServerInfoMgr.getInstance().getDefaultServerInfo(ProtocolConstant.SERVER_FW_AUTH).getAddress());
						for (ServerInfo info : list) {
							if (info.getServerType() == serverType[itemID]) {
								if (info.getServerType() == ProtocolConstant.SERVER_FW_TRADING)
									setDefaultTradeAddress(edt_trade_address, info);
								else if (info.getServerType() != ProtocolConstant.SERVER_FW_AUTH)
									super_addEdt[itemID].setText(info.getAddress());
							}
						}
						break;
				}
			}
		}
	}

	private void setDefaultTradeAddress(EditText edt_trade_address, ServerInfo tradeServer) {
		if (tradeServer != null) {
			String jyUrl = tradeServer.getAddress();
			if (!jyUrl.contains("https")) {// 将http替换为https，去掉端口号
				jyUrl = jyUrl.replace("http", "https");
				int index = jyUrl.lastIndexOf(":");
				if (index > 5) {
					jyUrl = jyUrl.substring(0, index) + ":"
							+ tradeServer.getHttpsPort();
				}
			}
			edt_trade_address.setText(jyUrl);
		}
	}
}
