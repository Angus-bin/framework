/**
 * 
 */
package com.romaway.common.net.conn;

/**
 * @author duminghui
 * 
 */
public interface IConnectionManager
{
	void init();

	void destroy();

	AConnection crate(ConnInfo connInfo);
}
