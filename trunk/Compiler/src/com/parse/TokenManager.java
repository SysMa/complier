package com.parse;

import java.util.ArrayList;

import com.lexical.Token;

public class TokenManager {
	int curPos = 0;
	ArrayList<Token> tokenSource;
	
	public TokenManager(ArrayList<Token> tokenSource){
		this.tokenSource = tokenSource;
	}
	
	public void ReInit(ArrayList<Token> tokenSource){
		this.tokenSource = tokenSource;
	}
	
	public Token getNextToken(){
		Token t = null;
		if (curPos < tokenSource.size()){
			t = tokenSource.get(curPos);
			curPos++;
		}
		return t;
	}
}
