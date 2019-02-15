/**
 * 
 */
package com.romaway.common.net.serverinfo;

import android.support.annotation.Keep;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 服务器信息类
 * 
 * @author duminghui xueyan:2012.4.16添加cpid字段
 */
@Keep
public class ServerInfo
{
	/**
	 * 服务器标志
	 */
	private String serverFlag;
	/**
	 * 服务器名称
	 */
	private String serverName;

	/**
	 * 服务器地址
	 */
	private String url;
	
	private String subFunUrl = "";
	
	/**
	 * 是否长连接
	 */
	private boolean keepAlive;
	/**
	 * 券商标示
	 */
	private static String cpid;
	/**
	 * 站点权重
	 */
	private float serverWeight;
	/**
	 * 站点负债
	 */
	private float serverLoad;
	/**
	 * 站点测速开始时间
	 */
	private long startTime;
	/**
	 * 站点测速结束时间
	 */
	private long endTime;
	/**
	 * 站点速度
	 */
	private long serverSpeed;
	/**
	 * 站点速度*比例 + 站点权重 *（ 1 - 比例 ）
	 * 计算后的权重
	 */
	private double serverCalWeight;
	/**
	 * https端口号
	 */
	private int httpsPort;
	/**
	 * 站点类型
	 * 200：平台，201：交易，202：行情，203：资讯，204：认证
	 */
	private int serverType;
	/**
	 * 可用功能列表
	 */
	private List<String> functionList = new ArrayList<String>();
	/**
	 * 站点所属运营商
	 */
	private String carrieroperator;
	
	public static String getCpid()
	{
		return cpid;
	}

	public static void setCpid(String value)
	{
		cpid = value;
	}

	/**
	 * 验证 https 证书公钥流
	 */
	private InputStream sslPublicKeyIs = null;

	/**
	 * 公钥路径
	 */
	private String sslPublicKeyPath;

	/**
	 * 是否检验证书，默认检验
	 */
	private boolean isCheckCertificate = true;

	/*public ServerInfo(String serverFlag, String serverName, String url, boolean keepAlive, int httpsport) {
		this.serverFlag = serverFlag;
		this.serverName = serverName;
		this.url = url;
		this.serverWeight = serverWeight;
		this.serverLoad = serverLoad;
		this.httpsPort = httpsport;
	}*/
	
	public ServerInfo(String serverFlag, int serverType, String serverName, String url, boolean keepAlive, int httpsport) {
		this.serverFlag = serverFlag;
		this.serverType = serverType;
		this.serverName = serverName;
		this.url = url;
		this.httpsPort = httpsport;
	}
	
	public ServerInfo(String serverFlag, String serverName, String url,
			float serverWeight, float serverLoad, boolean keepAlive, int httpsport) {
		this.serverFlag = serverFlag;
		this.serverName = serverName;
		this.url = url;
		this.keepAlive = keepAlive;
		this.serverWeight = serverWeight;
		this.serverLoad = serverLoad;
		this.httpsPort = httpsport;
	}

	public ServerInfo(String serverFlag, int serverType, String serverName, String url,
			float serverWeight, float serverLoad, boolean keepAlive, int httpsport) {
		this.serverFlag = serverFlag;
		this.serverType = serverType;
		this.serverName = serverName;
		this.url = url;
		this.keepAlive = keepAlive;
		this.serverWeight = serverWeight;
		this.serverLoad = serverLoad;
		this.httpsPort = httpsport;
	}
	
	public ServerInfo(String serverFlag, String serverName, String url,
			float serverWeight, float serverLoad, boolean keepAlive, int httpsport, String carrieroperator) {
		this.serverFlag = serverFlag;
		this.serverType = serverType;
		this.serverName = serverName;
		this.url = url;
		this.keepAlive = keepAlive;
		this.serverWeight = serverWeight;
		this.serverLoad = serverLoad;
		this.httpsPort = httpsport;
		this.carrieroperator = carrieroperator;
	}

	/**
	 * @return the serverFlag
	 */
	public String getServerFlag()
	{
		return serverFlag;
	}

	/**
	 * @param serverFlag
	 *            the serverFlag to set
	 */
	public void setServerFlag(String serverFlag)
	{
		this.serverFlag = serverFlag;
	}

	/**
	 * 是否进行证书校验
	 * @return
     */
	public boolean isCheckCertificate(){
		return isCheckCertificate;
	}

	/**
	 * 设置证书是否被校验
	 * @param isCheck true 进行校验 false 不校验
     */
	public void setCheckCertificate(boolean isCheck){
		isCheckCertificate = isCheck;
	}

	/**
	 * 设置证书验证公钥
	 * @param inputStream
     */
	public void setSSLPublicKey(InputStream inputStream ){
		this.sslPublicKeyIs = inputStream;
	}

	/**
	 * 获取证书验证的公钥
	 * @return
     */
	public InputStream getSslPublicKeyIs(){
		return sslPublicKeyIs;
	}

	/**
	 * 设置证书验证公钥被保存的路径
	 * @param publicKeyPath
	 */
	public void setSSLPublicKeyPath(String publicKeyPath ){
		this.sslPublicKeyPath = publicKeyPath;
	}

	/**
	 * 获取证书验证的公钥被保存的路径
	 * @return
	 */
	public String getSslPublicKeyPath(){
		return sslPublicKeyPath;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
 		return url + subFunUrl; 
	}
	
	/**
	 * 获取无后缀地址
	 * @return
	 */
	public String getAddress(){
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	public void setSubFunUrl(String subFunUrl){
		this.subFunUrl = subFunUrl;
	}
	
	public String getSubFunUrl() {
		return subFunUrl;
	}

	/**
	 * @return the keepAlive
	 */
	public boolean isKeepAlive()
	{
		return keepAlive;
	}

	/**
	 * @param keepAlive
	 *            the keepAlive to set
	 */
	public void setKeepAlive(boolean keepAlive)
	{
		this.keepAlive = keepAlive;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName()
	{
		return serverName;
	}

	/**
	 * @param serverName
	 *            the serverName to set
	 */
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}
	

	public float getServerWeight() {
		return serverWeight;
	}

	public void setServerWeight(int serverWeight) {
		this.serverWeight = serverWeight;
	}

	public float getServerLoad() {
		return serverLoad;
	}

	public void setServerLoad(int serverLoad) {
		this.serverLoad = serverLoad;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getServerSpeed() {
		return serverSpeed;
	}

	public void setServerSpeed(long serverSpeed) {
		this.serverSpeed = serverSpeed;
	}

	public double getServerCalWeight() {
		return serverCalWeight;
	}

	public void setServerCalWeight(double serverCalWeight) {
		this.serverCalWeight = serverCalWeight;
	}
	
	public int getHttpsPort(){
		return httpsPort;
	}
	
	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public List<String> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(String[] functionList) {
		if (functionList != null && functionList.length > 0) {
			for (String module : functionList) {
				this.functionList.add(module);
			}
		}
	}

	public String getCarrieroperator() {
		return carrieroperator;
	}

	public void setCarrieroperator(String carrieroperator) {
		this.carrieroperator = carrieroperator;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof ServerInfo)
		{
			ServerInfo serverInfo = (ServerInfo) obj;
			return serverInfo.getServerName().equals(this.serverName)
			        && serverInfo.getUrl().equals(this.url);
		} else
		{
			return false;
		}
	}

}
