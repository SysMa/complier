package com.lexical;

import java.util.Map;

public class LexicalAnalyzer implements LexicalAnalyzerConstants {
	public LexicalAnalyzerTokenManager token_source;
	SimpleCharStream jj_input_stream;
	
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
	
	public String getTokenString(Map<String, Integer> tokenCountMap) throws Exception{
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
				case LONG_LITERAL:
					tokenStr += "<" + tokenImage[t.kind] + ", " + t.image + ">";
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
					}
					break;
				default:
					tokenStr += "<" + tokenImage[t.kind].replaceAll("\"", "") + ">";
					break;
				}
			}
		}
		catch (Error e){
			throw new Exception(e.getMessage());
		}
		return tokenStr;
	}

}
