package com.congxing.core.web.tree;

public class TreeRefIndex {
	
	private int refIndex = 0;
	
	public TreeRefIndex(){
		
	}
	public int getNextRefIndex(){
		return refIndex++;
	}

}
