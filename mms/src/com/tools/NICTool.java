package com.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

import javax.servlet.http.HttpServletRequest;

public class NICTool {
	/**
	 * 通过HttpServletRequest获得客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {

		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	/**
	 * 通过IP地址获取MAC地址
	 * 
	 * @param ip格式
	 *            ：127.0.0.1
	 * @return
	 * @throws Exception
	 */
	public static String getMACAddress(String ip) {
		String line = "";
		String macAddress = "";
		final String MAC_ADDRESS_PREFIX = "MAC Address = ";
		final String LOOPBACK_ADDRESS = "127.0.0.1";
		try {
			// 如果为127.0.0.1,则获取本地MAC地址。
			if (LOOPBACK_ADDRESS.equals(ip)) {
				InetAddress inetAddress = InetAddress.getLocalHost();
				byte[] mac = NetworkInterface.getByInetAddress(inetAddress)
						.getHardwareAddress();
				// 下面代码是把mac地址拼装成String
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					if (i != 0) {
						sb.append("-");
					}
					// mac[i] & 0xFF 是为了把byte转化为正整数
					String s = Integer.toHexString(mac[i] & 0xFF);
					sb.append(s.length() == 1 ? 0 + s : s);
				}
				// 把字符串所有小写字母改为大写成为正规的mac地址并返回
				macAddress = sb.toString().trim().toUpperCase();
				return macAddress;
			}
			// 获取非本地IP的MAC地址
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line != null) {
					int index = line.indexOf(MAC_ADDRESS_PREFIX);
					if (index != -1) {
						macAddress = line
								.substring(index + MAC_ADDRESS_PREFIX.length())
								.trim().toUpperCase();
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

}
