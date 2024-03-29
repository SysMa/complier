package com.lexical;

import java.util.ArrayList;
import java.util.HashMap;

public class LexicalAnalyzer implements LexicalAnalyzerConstants {
	public LexicalAnalyzerTokenManager token_source;
	SimpleCharStream jj_input_stream;
	ArrayList<Token> tokenSource = new ArrayList<Token>();
	
	/** Constructor with InputStream. */
	public LexicalAnalyzer(java.io.InputStream stream) {
		this(stream, null);
	}
	/** Constructor with InputStream and supplied encoding */
	public LexicalAnalyzer(java.io.InputStream stream, String encoding) {
	    try { 
	    	jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); 
	    } 
	    catch(java.io.UnsupportedEncodingException e) { 
	    	throw new RuntimeException(e); 
	    }
	    token_source = new LexicalAnalyzerTokenManager(jj_input_stream);
	}
	
	public String getTokenString(HashMap<String, Integer> tokenCountMap) throws Exception{
		Token t;
		String tokenStr = "";
		try {
			while ((t = token_source.getNextToken()).kind != EOF){
				switch (t.kind){
				case EOL:
					tokenStr += "\n";
					break;
				case SPACE:
					tokenStr += " ";
					break;
				case TAB:
					tokenStr += "\t";
					break;
				case INTEGER_LITERAL:
					{
						Integer i = tokenCountMap.get("Integer literals: " + t.image);
					    if (i == null)
					    {
					      i = 0;
					    }
					    i++;
					    tokenCountMap.put("Integer literals: " + t.image, i);
						tokenStr += "<" + tokenImage[t.kind] + ", " + t.image + ">";
						tokenSource.add(t);
					}
					break;
				case LONG_LITERAL:
					{
						Integer i = tokenCountMap.get("Long literals: " + t.image);
					    if (i == null)
					    {
					      i = 0;
					    }
					    i++;
					    tokenCountMap.put("Long literals: " + t.image, i);
						tokenStr += "<" + tokenImage[t.kind] + ", " + t.image + ">";
						tokenSource.add(t);
					}
					break;
				case BOOLEAN:
				case CLASS:
				case ELSE:
				case EXTENDS:
				case FALSE:
				case IF:
				case WHILE:
				case INTEGER:
				case LONG:
				case NEW:
				case RETURN:
				case STATIC:
				case THIS:
				case TRUE:
				case VOID:
				case PRE:
				case POST:
				case LENGTH:
				case MAIN:
					{
						Integer i = tokenCountMap.get("Keywords: " + t.image);
					    if (i == null)
					    {
					      i = 0;
					    }
					    i++;
					    tokenCountMap.put("Keywords: " + t.image, i);
					    tokenStr += "<" 
					    		 + tokenImage[t.kind].replaceAll("\"", "") + ">";
					    tokenSource.add(t);
					}
					break;
				case ID:
					{
						Integer i = tokenCountMap.get("Identifiers: " + t.image);
					    if (i == null)
					    {
					      i = 0;
					    }
					    i++;
					    tokenCountMap.put("Identifiers: " + t.image, i);
					    tokenStr += "<id, " + t.image + ">";
					    tokenSource.add(t);
					}
					break;
				default:
					tokenStr += "<" + tokenImage[t.kind].replaceAll("\"", "") + ">";
					tokenSource.add(t);
					break;
				}
			}
			tokenSource.add(t);
		}
		catch (Error e){
			throw new Exception(e.getMessage());
		}
		return tokenStr;
	}
	
	public ArrayList<Token> getTokenSource(){
		return tokenSource;
	}

}
