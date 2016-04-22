package com.wlt.webm.business.junbao.bean;

import java.io.DataInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

import com.wlt.webm.util.MD5;

public class testYd {

public static void main(String[] args) throws Exception {
HttpURLConnection conn = null;

// DataOutputStream dos = null;
DataInputStream inStream = null;

boolean ret = false;

//查询产品信息
//String strver = "agentid=DE201206051422351440&merchantKey=9aa9346ca786452a9dc680e3487b1251f77ea58ee6e94a7db34abe3452e99398bf696694c65243baa9f17f289b5f4e4f1ec3afd713084539ae26d6d205c2d75a";

//直冲订单
String strver ="prodid=106926&agentid=DE201206051422351440&orderid=141&mobilenum=13413404100&datetime=20120713105600&mark=&merchantKey=9aa9346ca786452a9dc680e3487b1251f77ea58ee6e94a7db34abe3452e99398bf696694c65243baa9f17f289b5f4e4f1ec3afd713084539ae26d6d205c2d75a";
//查询订单状态
//String strver ="agentid=DE201206051422351440&orderid=117&merchantKey=9aa9346ca786452a9dc680e3487b1251f77ea58ee6e94a7db34abe3452e99398bf696694c65243baa9f17f289b5f4e4f1ec3afd713084539ae26d6d205c2d75a";

// byte b[]
// ="1.02154325432h664562IVR12200909081712415106022735013016952733127.0.27.116Union20090514JS".getBytes();
MD5 d = new MD5();
strver = MD5.encode(strver).toLowerCase();

// System.out.println(getFromBASE64(
// "VkVSU0lPTj02NTUzOCZDSEFOTkVMSUQ9MjAwNzAxJkJVWUVSPTUxMDYwMjI3MyZDQVNIPTUwMDAmUEFZRVI9MTMwMTY5NTI3MzMmU1RBVFVTPTUwMDEmU0lHTj1mNWM4ZTg4MDA4ZjZhNTRkN2FmNjJkZDcwZjMyMDk0ZCZESUdJVEFMU0lHTj11TWtJVlVCZHVxY0lSNktvSk1kT3Q5ZG9ZRVZrMkdVODQwQVlzQS8zRFZ5M2t0UFpyZjBMbVE9PQ=="));

//直冲订单
String urlString ="http://122.224.88.60:8088/esales/recharge/mobileCharge.do?prodid=106926&agentid=DE201206051422351440&orderid=141&mobilenum=13413404100&datetime=20120713105600&mark=&verifystring="+strver;
//查询订单状态
//String urlString ="http://122.224.88.60:8088/esales/order/orderQuery.do?agentid=DE201206051422351440&orderid=117&verifystring="+strver;
//查询商品信息
//String urlString = "http://122.224.88.60:8088/esales/product/directProduct.do?agentid=DE201206051422351440&verifystring="
//	+ strver;  
System.out.println(urlString);
try {
// ------------------ CLIENT REQUEST
// FileInputStream fileInputStream = new FileInputStream(new
// File(exsistingFileName));
// open a URL connection to the Servlet
URL url = new URL(urlString);
// Open a HTTP connection to the
conn = (HttpURLConnection) url.openConnection();
// Allow Inputs
conn.setDoInput(true);
// Allow Outputs
conn.setDoOutput(true);
// Don't use a cached copy.
conn.setUseCaches(false);
// Use a post method.
conn.setRequestMethod("POST");

} catch (MalformedURLException ex) {
System.out.println("From ServletCom CLIENT REQUEST:" + ex);
} catch (IOException ioe) {
System.out.println("From ServletCom CLIENT REQUEST:" + ioe);
}
// ------------------ read the SERVER RESPONSE
try {
inStream = new DataInputStream(conn.getInputStream());
Map result=OrderQuery.xmlDeal(inStream);
if(result.size()>0){
	System.out.println((String) result.get("resultno"));
}
String str;
URLDecoder decoder =new URLDecoder();
while ((str = inStream.readLine()) != null) {
System.out.println("Server response is: " + new String( decoder.decode(str, "utf-8")));
System.out.println("");
}
inStream.close();
conn.disconnect();
} catch (IOException ioex) {
ioex.printStackTrace();
// System.out.println("From (ServerResponse): " + ioex);
}
}

}