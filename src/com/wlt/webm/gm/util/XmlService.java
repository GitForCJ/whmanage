package com.wlt.webm.gm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.wlt.webm.gm.form.Game;
import com.wlt.webm.gm.form.GameArea;
import com.wlt.webm.gm.form.GameInterface;
import com.wlt.webm.gm.form.GameServer;

/**
 * 
 * @ClassName: XmlService
 * @author tanwanlong
 * @date 2014-6-5 下午04:33:50
 */
public class XmlService {
	public List<String> list = new ArrayList<String>();

	public XmlService() {
		// 添加游戏接口列表
		list.add("qqes2dq");
		list.add("163es");
		list.add("qqes4x");
		list.add("tces");
		list.add("gyes");
		list.add("sdoesdq");
		list.add("sohues");
		list.add("wmes");
	}

	public List<GameInterface> getGameInterfaceList(String result)
			throws DocumentException {
		List<GameInterface> gamelist = new ArrayList<GameInterface>();
		Document doc = DocumentHelper.parseText(result);
		Element rootElt = doc.getRootElement(); // 获取根节点
		// 获取根节点下的子节点info
		Iterator<Element> InfoIter = rootElt.elementIterator("info");
		String ret = "";
		String ret_msg = "";
		while (InfoIter.hasNext()) {
			Element info = InfoIter.next();
			ret = info.elementTextTrim("ret");
			ret_msg = info.elementTextTrim("ret_msg");
		}
		// 获取根节点下的子节点game
		Iterator<Element> gameIter = rootElt.elementIterator("game");
		while (gameIter.hasNext()) {
			Element game = gameIter.next();
			String gameapi = game.elementTextTrim("gameapi");
			String gamename = game.elementTextTrim("gamename");
			String lei = game.elementTextTrim("lei");
			String lx = game.elementTextTrim("lx");
			String zt = game.elementTextTrim("zt");
			String mprice = game.elementTextTrim("mprice");
			String lprice = game.elementTextTrim("lprice");
			String startbuy = game.elementTextTrim("startbuy");
			String endbuy = game.elementTextTrim("endbuy");
			String contbuy = game.elementTextTrim("contbuy");
			String buyunit = game.elementTextTrim("buyunit");
			String gtext = game.elementTextTrim("gtext");
			if (list.contains(gameapi)) {
				if (zt.equals("1")) {
					GameInterface game1 = new GameInterface();
					game1.setBuyunit(buyunit);
					game1.setContbuy(contbuy);
					game1.setEndbuy(endbuy);
					game1.setGameapi(gameapi);
					if (gamename.endsWith("接口")) {
						gamename = gamename
								.substring(0, gamename.indexOf("接口"));
					}
					game1.setGamename(gamename);
					game1.setGtext(gtext);
					game1.setLei(lei);
					game1.setLprice(lprice);
					game1.setLx(lx);
					game1.setMprice(mprice);
					game1.setRet(ret);
					game1.setRet_msg(ret_msg);
					game1.setStartbuy(startbuy);
					game1.setZt(zt);
					gamelist.add(game1);
				}
			}
		}
		return gamelist;
	}

	/**
	 * 
	 * @param result
	 * @return 0代表接收成功 1表示接收失败
	 * @throws DocumentException
	 */
	public Map fillGameOrder(String result) throws DocumentException {
		Document doc = DocumentHelper.parseText(result);
		Element rootElt = doc.getRootElement(); // 获取根节点
		// 获取根节点下的子节点info
		Iterator<Element> InfoIter = rootElt.elementIterator("info");
		String ret = "";
		while (InfoIter.hasNext()) {
			Element info = InfoIter.next();
			ret = info.elementTextTrim("ret");
		}
		String zt = "";
		String orderid = "";
		// 获取根节点下的子节点game
		Iterator<Element> gameIter = rootElt.elementIterator("gaorder");
		while (gameIter.hasNext()) {
			Element game = gameIter.next();
			zt = game.elementTextTrim("zt");
			orderid = game.elementTextTrim("orderid");
		}
		Map map = new HashMap<String, String>();
		if (ret.equals("0")
				&& (zt.equals("1") || zt.equals("2") || zt.equals("3"))) {
			map.put("state", "0");
			map.put("orderid", orderid);
		} else {
			map.put("state", "-1");
			map.put("orderid", orderid);
		}
		return map;
	}

	/**
	 * 查询接口
	 * 
	 * @param result
	 * @return
	 * @throws DocumentException
	 */
	public int queryGameOrder(String result) throws DocumentException {
		Document doc = DocumentHelper.parseText(result);
		Element rootElt = doc.getRootElement(); // 获取根节点
		// 获取根节点下的子节点game
		Iterator<Element> gameIter = rootElt.elementIterator("gasearch");
		String zt = "";
		while (gameIter.hasNext()) {
			Element game = gameIter.next();
			zt = game.elementTextTrim("zt");
			break;
		}
		if (zt.equals("1")) {// 充值成功
			return 0;
		} else if (zt.equals("0")) {// 充值失败
			return 1;
		} else {
			return 4;// 处理中
		}
	}

	/**
	 * 获取游戏列表
	 * 
	 * @param result
	 * @return
	 * @throws DocumentException
	 */
	public List<Game> getGameList(String result) throws DocumentException {
		List<Game> gamelist = new ArrayList<Game>();
		Document doc = DocumentHelper.parseText(result.trim());
		Element rootElt = doc.getRootElement(); // 获取根节点
		// 获取根节点下的子节点info
		Iterator<Element> InfoIter = rootElt.elementIterator("info");
		String ret = "";
		String ret_msg = "";
		while (InfoIter.hasNext()) {
			Element info = InfoIter.next();
			ret = info.elementTextTrim("ret");
			ret_msg = info.elementTextTrim("ret_msg");
		}
		// 获取根节点下的子节点game
		Iterator<Element> gameIter = rootElt.elementIterator("gamelist");
		while (gameIter.hasNext()) {
			Element game = gameIter.next();
			Iterator<Element> iter = game.elementIterator("game");
			while (iter.hasNext()) {
				Element g = iter.next();
				String id = g.attributeValue("id");
				String api = g.attributeValue("api");
				String corp = g.attributeValue("corp");
				String code = g.attributeValue("code");
				String mprice = g.attributeValue("mprice");
				String point = g.attributeValue("point");
				String unit = g.attributeValue("unit");
				String name = g.attributeValue("name");
				Game game2 = new Game();
				game2.setApi(api);
				game2.setCode(code);
				game2.setCorp(corp);
				game2.setId(id);
				game2.setMprice(mprice);
				game2.setName(name);
				game2.setPoint(point);
				game2.setRet(ret);
				game2.setRet_msg(ret_msg);
				game2.setUnit(unit);
				gamelist.add(game2);
			}
		}
		return gamelist;
	}

	public List<GameArea> getGameAreaList(String result) throws DocumentException {
		List<GameArea> gameArealist = new ArrayList<GameArea>();
		Document doc = DocumentHelper.parseText(result.trim());
		Element rootElt = doc.getRootElement(); // 获取根节点
		// 获取根节点下的子节点info
		Iterator<Element> InfoIter = rootElt.elementIterator("info");
		String ret = "";
		String ret_msg = "";
		while (InfoIter.hasNext()) {
			Element info = InfoIter.next();
			ret = info.elementTextTrim("ret");
			ret_msg = info.elementTextTrim("ret_msg");
		}
		if (ret.equals("0")) {
			// 获取根节点下的子节点game
			Iterator<Element> gameIter = rootElt.elementIterator("gameinfo");
			while (gameIter.hasNext()) {
				Element gameinfo = gameIter.next();
				Iterator<Element> iter = gameinfo.elementIterator("gamecode");
				while (iter.hasNext()) {
					Element gameCode = iter.next();
					Iterator<Element> g = gameCode.elementIterator("gamearea");
					while (g.hasNext()) {
						Element garea = g.next();
						String id = garea.attributeValue("id");
						String name = garea.attributeValue("name");
						GameArea ga = new GameArea();
						ga.setId(id);
						ga.setName(name);
						Iterator<Element> s = garea.elementIterator("gameserver");
						List<GameServer> gsList=new ArrayList<GameServer>();
						while (s.hasNext()) {
								Element gserver = s.next();
								String serverid = gserver.attributeValue("id");
								String servername = gserver.attributeValue("name");
								GameServer gs = new GameServer();
								gs.setId(serverid);
								gs.setName(servername);
								gsList.add(gs);
						}
						ga.setLitGameServer(gsList);
						gameArealist.add(ga);
					}
				}
			}
		}
		return gameArealist;
	}
}
