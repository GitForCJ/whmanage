package com.wlt.webm.xunjie.form;

import java.util.List;

public class GameGoods {
	
	private String goods_id;
	private String goods_name;
	private List<Game> gameList;

	
	public String getGoods_id() {
		return goods_id;
	}


	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}


	public String getGoods_name() {
		return goods_name;
	}


	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public List<Game> getGameList() {
		return gameList;
	}


	public void setGameList(List<Game> gameList) {
		this.gameList = gameList;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
