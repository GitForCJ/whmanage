package cn.com.WebXml;

public class Single {

	private Single() {

	}

	private static class SingleService {
		public static IpAddressSearchWebService locator =new IpAddressSearchWebServiceLocator();
	}

	public static IpAddressSearchWebService getSrvInstance() {
		return SingleService.locator;
	}
}
