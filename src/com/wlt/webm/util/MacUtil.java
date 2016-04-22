package com.wlt.webm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

import javax.servlet.http.HttpServletRequest;

public class MacUtil {

	 public static String getOsName() {
	        String os = "";
	        os = System.getProperty("os.name");
	        return os;
	    }
	    
	    public static String getMACAddress1() {
	        String address = "";
	        String os = getOsName();
	        if (os.startsWith("Windows")) {
	            try {
	                String command = "cmd.exe /c ipconfig /all";
	                Process p = Runtime.getRuntime().exec(command);
	                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	                String line;
	                while ((line = br.readLine()) != null) {
	                    if (line.indexOf("Physical Address") > 0 || line.indexOf("物理地址") > 0) {
	                        int index = line.indexOf(":");
	                        index += 2;
	                        address = line.substring(index);
	                        break;
	                    }
	                }
	                br.close();
	                return address.trim();
	            } catch (IOException e) {
	            }
	        } else if (os.startsWith("Linux")) {
	            String command = "/bin/sh -c ifconfig -a";
	            Process p;
	            try {
	                p = Runtime.getRuntime().exec(command);
	                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	                String line;
	                while ((line = br.readLine()) != null) {
	                    if (line.indexOf("HWaddr") > 0) {
	                        int index = line.indexOf("HWaddr") + "HWaddr".length();
	                        address = line.substring(index);
	                        break;
	                    }
	                }
	                br.close();
	            } catch (IOException e) {
	            }
	        }
	        address = address.trim();
	        return address;
	    }
	    public static String getIpAddr(HttpServletRequest request) throws Exception {    
	        String ip = request.getHeader("x-forwarded-for");    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("Proxy-Client-IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("WL-Proxy-Client-IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("HTTP_CLIENT_IP");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
	        }    
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	            ip = request.getRemoteAddr();    
	        }    
	        return ip;    
	    } 
	    public static String getMACAddress(String ip) throws Exception {    
	        String line = "";    
	        String macAddress = "";    
	        final String MAC_ADDRESS_PREFIX = "MAC Address = ";   
	        final String MAC_ADDR_CH ="MAC 地址";
	        final String LOOPBACK_ADDRESS = "127.0.0.1";    
	        //如果为127.0.0.1,则获取本地MAC地址。    
	        if (LOOPBACK_ADDRESS.equals(ip)) {    
	            InetAddress inetAddress = InetAddress.getLocalHost();    
	            //貌似此方法需要JDK1.6。    
	            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();    
	            //下面代码是把mac地址拼装成String    
	            StringBuilder sb = new StringBuilder();    
	            for (int i = 0; i < mac.length; i++) {    
	                if (i != 0) {    
	                    sb.append("-");    
	                }    
	                //mac[i] & 0xFF 是为了把byte转化为正整数    
	                String s = Integer.toHexString(mac[i] & 0xFF);    
	                sb.append(s.length() == 1 ? 0 + s : s);    
	            }    
	            //把字符串所有小写字母改为大写成为正规的mac地址并返回    
	            macAddress = sb.toString().trim().toUpperCase();    
	            return macAddress;    
	        }    
	        //获取非本地IP的MAC地址    
	        try {    
	            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);    
	            InputStreamReader isr = new InputStreamReader(p.getInputStream());    
	            BufferedReader br = new BufferedReader(isr);    
	            while ((line = br.readLine()) != null) {    
	                if (line != null) {    
	                    int index = line.indexOf(MAC_ADDRESS_PREFIX);
	                    int chAddIdx = line.indexOf(MAC_ADDR_CH);
	                    if (index != -1 ) {    
	                        macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();
	                        break;
	                    }  
	                    if(chAddIdx!=-1)
	                    {
	                    	macAddress = line.substring(chAddIdx + MAC_ADDR_CH.length()+2).trim().toUpperCase();
	                    	break;
	                    }
	                    
	                }    
	            }    
	            br.close();    
	        } catch (IOException e) {    
	            e.printStackTrace(System.out);    
	        }    
	        return macAddress;    
	    }    
	      
	    public static void main(String[] args) throws Exception {
			System.out.println(getMACAddress("127.0.0.1"));
		}
}
