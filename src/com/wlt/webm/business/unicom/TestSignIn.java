package com.wlt.webm.business.unicom;

public class TestSignIn {	
	public void send(Unicom uni, byte[] message){		
		uni.send(message);
	}
	
	public byte[] receive(){
		return null;
	}
	
}
