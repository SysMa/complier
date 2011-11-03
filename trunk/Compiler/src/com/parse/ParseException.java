package com.parse;

import com.lexical.Token;

/**
 * This exception is thrown when parse errors are encountered.
 * You can explicitly create objects of this exception type by
 * calling the method generateParseException in the generated
 * parser.
 *
 * You can modify this class to customize your error reporting
 * mechanisms so long as you retain the public fields.
 */
public class ParseException extends Exception {

  /**
   * The version identifier for this Serializable class.
   * Increment only if the <i>serialized</i> form of the
   * class changes.
   */
  private static final long serialVersionUID = 1L;

  /**
   * This constructor is used by the method "generateParseException"
   * in the generated parser.  Calling this constructor generates
   * a new object of this type with the fields "currentToken",
   * "expectedTokenSequences", and "tokenImage" set.
   */
  public ParseException(Token currentTokenVal,
                        int[][] expectedTokenSequencesVal,
                        String[] tokenImageVal
                       )
  {
    super(initialise(currentTokenVal, expectedTokenSequencesVal, tokenImageVal));
    currentToken = currentTokenVal;
    expectedTokenSequences = expectedTokenSequencesVal;
    tokenImage = tokenImageVal;
    errorcode = NONE_ERROR_CODE;
  }

  /**
   * The following constructors are for use by you for whatever
   * purpose you can think of.  Constructing the exception in this
   * manner makes the exception behave in the normal way - i.e., as
   * documented in the class "Throwable".  The fields "errorToken",
   * "expectedTokenSequences", and "tokenImage" do not contain
   * relevant information.  The JavaCC generated code does not use
   * these constructors.
   */

  public ParseException() {
    super();
  }

  /** Constructor with message. */
  public ParseException(String message) {
    super(message);
  }
  
  /** The error code. */
  public static int NONE_ERROR_CODE 	= -1;
  
  public static int ENDING_BEFORE_EOF 	= 0;
  
  public static int MISSING_AFTER_DOT	= 100;
  public static int MISSING_CLASS		= 101;
  public static int MISSING_CLOSEBRACE  = 102;
  public static int MISSING_CLOSEPAR	= 103;
  public static int MISSING_CLOSESQPAR	= 104;
  public static int MISSING_COLON		= 105;
  public static int MISSING_MAIN		= 106;
  public static int MISSING_POST		= 107;
  public static int MISSING_PRE 		= 108;
  public static int MISSING_RETURN		= 109;
  public static int MISSING_SEMICOLON	= 110;
  public static int MISSING_STATIC		= 111;
  public static int MISSING_VOID		= 112;
  
  public static int ERROR_AT_ASSIGNMENT		= 200;
  public static int ERROR_AFTER_OPERATOR	= 201;
  
  /** The error message responding to the error code. */
  private static String[][] errorMsg = {
	  {"The parser ends before it reaches the end of the file.\r\n\tMaybe you would declare a class starts with 'class'."},
	  {"Maybe you would add 'length' or a method call like 'add(1)' after the dot'.' .",
	   "Maybe you need to add a main class declaration like 'class ID{static void main(){}}'.",
	   "Close brace '}' missing.",
	   "Close parentheses ')' missing.",
	   "Close square parentheses ']' missing.",
	   "A colon ':' is needed following 'pre' or 'post' in the pre-post condition statement.",
	   "Maybe you need to add a 'main' because it's needed in the main function declaration.",
	   "There would be a post-condition statement like 'post : returnVal > 4;' after the pre-condition statement in front of the function declaration",
	   "In front of the function declaration would be a pre-condition statement like 'pre : param > 4;'.", 
	   "Maybe you would add a return statement like 'return 3;' at the end of a method declaration.",
	   "Maybe you want to end a statement but a semicolon';' is neccessary.",
	   "Maybe you need to add the main function declaration like 'static void main()'.",
	   "Maybe you need to add a 'void' because it's needed in the main function declaration."},
	  {"After an assign symbol there may be\r\n \t\tan integer literal like '1'\r\n \t\tor a long literal like '1L'\r\n \t\tor a boolean value like 'true'\r\n \t\tor some other legal expression.",
	   "After an operation there would be an legal expression like '2' or 'true'."}
  };
  
  public ParseException(Token token, 
		  				int errorcode){
	  super(initialise(token, errorcode));
	  currentToken = token;
	  this.errorcode = errorcode;
  }

  /**
   * This is the last token that has been consumed successfully.  If
   * this object has been created due to a parse error, the token
   * followng this token will (therefore) be the first error token.
   */
  public Token currentToken;

  /**
   * Each entry in this array is an array of integers.  Each array
   * of integers represents a sequence of tokens (by their ordinal
   * values) that is expected at this point of the parse.
   */
  public int[][] expectedTokenSequences;

  /**
   * This is a reference to the "tokenImage" array of the generated
   * parser within which the parse error occurred.  This array is
   * defined in the generated ...Constants interface.
   */
  public String[] tokenImage;
  
  /**
   * This is the error code.
   */
  public int errorcode;

  /**
   * It uses "currentToken" and "expectedTokenSequences" to generate a parse
   * error message and returns it.  If this object has been created
   * due to a parse error, and you do not catch it (it gets thrown
   * from the parser) the correct error message
   * gets displayed.
   */
  private static String initialise(Token currentToken,
                           int[][] expectedTokenSequences,
                           String[] tokenImage) {
    String eol = System.getProperty("line.separator", "\n");
    String expected = "";
    int maxSize = 0;
    for (int i = 0; i < expectedTokenSequences.length; i++) {
      if (maxSize < expectedTokenSequences[i].length) {
        maxSize = expectedTokenSequences[i].length;
      }
      for (int j = 0; j < expectedTokenSequences[i].length; j++) {
        expected += "\t" + tokenImage[expectedTokenSequences[i][j]];
      }
      expected += eol;
    }
    String retval = "Encountered \"";
    Token tok = currentToken.next;
    for (int i = 0; i < maxSize; i++) {
      if (i != 0) retval += " ";
      if (tok.kind == 0) {
        retval += tokenImage[0];
        break;
      }
      retval += " " + tokenImage[tok.kind].replaceAll("\"", "");
      retval += " \"";
      retval += add_escapes(tok.image);
      retval += " \"";
      tok = tok.next;
    }
    retval += "\" at line " + currentToken.next.beginLine + ", column " + currentToken.next.beginColumn;
    retval += "." + eol;
//    if (expectedTokenSequences.length == 1) {
//      retval += "Was expecting:" + eol + "    ";
//    } else {
//      retval += "Was expecting one of:" + eol + "    ";
//    }
    retval += "Maybe there would be" + eol + "    ";
    retval += expected;
    return retval;
  }
  
  /**
   * It uses "token" and "errorcode" to get the error message
   * and returns it.
   */
  private static String initialise(Token token, int errorcode){
	  String message = "";
	  if (errorcode == ENDING_BEFORE_EOF){
		  message = "Syntax error at Line " + token.next.beginLine
  		  		  + ", Column " + token.next.beginColumn
  		  		  + " on token '" + token.next.image
  		  		  + "'. " + eol + "\t";
	  }
	  else if (errorcode == MISSING_SEMICOLON || (errorcode / 100) == 2){
		  message = "Syntax error at Line " + token.endLine
  		  		  + ", Column " + token.endColumn
  		  		  + " after token '" + token.image
  		  		  + "'. " + eol + "\t";
	  }
	  else if (token.next.kind != 0){
		  message = "Syntax error at Line " + token.next.beginLine
		  		  + ", Column " + token.next.beginColumn
		  		  + " before token '" + token.next.image
		  		  + "'. " + eol + "\t";
	  }
	  else{
		  message = "Syntax error at Line " + token.next.beginLine
  		  		  + ", Column " + token.next.beginColumn
  		  		  + ". " + eol + "\t";
	  }
	  int type = errorcode / 100;
	  int type2 = errorcode % 100;
	  message += errorMsg[type][type2];
	  return message;
  }

  /**
   * The end of line string for this machine.
   */
  protected static String eol = System.getProperty("line.separator", "\n");

  /**
   * Used to convert raw characters to their escaped version
   * when these raw version cannot be used as part of an ASCII
   * string literal.
   */
  static String add_escapes(String str) {
      StringBuffer retval = new StringBuffer();
      char ch;
      for (int i = 0; i < str.length(); i++) {
        switch (str.charAt(i))
        {
           case 0 :
              continue;
           case '\b':
              retval.append("\\b");
              continue;
           case '\t':
              retval.append("\\t");
              continue;
           case '\n':
              retval.append("\\n");
              continue;
           case '\f':
              retval.append("\\f");
              continue;
           case '\r':
              retval.append("\\r");
              continue;
           case '\"':
              retval.append("\\\"");
              continue;
           case '\'':
              retval.append("\\\'");
              continue;
           case '\\':
              retval.append("\\\\");
              continue;
           default:
              if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
                 String s = "0000" + Integer.toString(ch, 16);
                 retval.append("\\u" + s.substring(s.length() - 4, s.length()));
              } else {
                 retval.append(ch);
              }
              continue;
        }
      }
      return retval.toString();
   }

}