package com.parse;

import java.util.ArrayList;
import java.util.Map;

import com.lexical.LexicalAnalyzerConstants;
import com.lexical.Token;

public class Parser/*@bgen(jjtree)*/implements ParserTreeConstants, LexicalAnalyzerConstants {/*@bgen(jjtree)*/
  protected JJTParserState jjtree = new JJTParserState();

  final public SimpleNode program(Map<String, Integer> tokenCountMap) throws ParseException {
 /*@bgen(jjtree) Program */
  SimpleNode jjtn000 = new SimpleNode(JJTPROGRAM);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Integer weight = 0;
  Integer count  = 0;
  Integer[] subWeight;
    try {
      subWeight = mainClass();
    weight += subWeight[0];
    count  += subWeight[1];
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case CLASS:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        subWeight = classDecl();
      weight += subWeight[0];
      count  += subWeight[1];
      }
    if (token.next.kind != EOF){
    	throw new ParseException(token, ParseException.ENDING_BEFORE_EOF);
    }
    tokenCountMap.put("$weight$", weight);
    tokenCountMap.put("$count$", count);
    {if (true) return jjtn000;}
      jj_consume_token(0);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public Integer[] mainClass() throws ParseException {
 /*@bgen(jjtree) MainClass */
  SimpleNode jjtn000 = new SimpleNode(JJTMAINCLASS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Integer subWeight = 0;
  Integer count = 0;
  Integer[] temp;
    try {
      _class();
      id();
      jj_consume_token(LBRACE);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BOOLEAN:
        case INTEGER:
        case LONG:
        case ID:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_2;
        }
        temp = varOrMethodDecl();
      subWeight += temp[0];
      count += temp[1];
      }
      _static();
      _void();
      _main();
      jj_consume_token(LPAREN);
      jj_consume_token(RPAREN);
      jj_consume_token(LBRACE);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LBRACE:
        case BOOLEAN:
        case IF:
        case WHILE:
        case INTEGER:
        case LONG:
        case ID:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_3;
        }
        temp = statement();
      subWeight += temp[0];
      count += temp[1];
      }
      jj_consume_token(RBRACE);
      jj_consume_token(RBRACE);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return new Integer[]{subWeight, count};}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public Integer[] classDecl() throws ParseException {
 /*@bgen(jjtree) ClassDecl */
  SimpleNode jjtn000 = new SimpleNode(JJTCLASSDECL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Integer subWeight = 0;
  Integer count = 0;
  Integer[] temp;
    try {
      _class();
      id();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EXTENDS:
        _extends();
        id();
        break;
      default:
        jj_la1[3] = jj_gen;
        ;
      }
      jj_consume_token(LBRACE);
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BOOLEAN:
        case INTEGER:
        case LONG:
        case ID:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_4;
        }
        temp = varOrMethodDecl();
      subWeight += temp[0];
      count += temp[1];
      }
      jj_consume_token(RBRACE);
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return new Integer[]{subWeight, count};}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public Integer[] varOrMethodDecl() throws ParseException {
 /*@bgen(jjtree) VarOrMethodDecl */
  SimpleNode jjtn000 = new SimpleNode(JJTVARORMETHODDECL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Integer subWeight = 0;
  Integer count = 0;
  Integer[] temp;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
      case INTEGER:
      case LONG:
        type();
        break;
      case ID:
        id();
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      id();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SEMICOLON:
      case ASSIGN:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case SEMICOLON:
          jj_consume_token(SEMICOLON);
          break;
        case ASSIGN:
          assign();
          try {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case INTEGER_LITERAL:
          case LONG_LITERAL:
          case LPAREN:
          case NOT:
          case FALSE:
          case NEW:
          case THIS:
          case TRUE:
          case ID:
            exp();
            break;
          case LSQPAREN:
            jj_consume_token(LSQPAREN);
            expList();
            jj_consume_token(RSQPAREN);
            break;
          default:
            jj_la1[6] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          }
          catch (ParseException pe){
        	  if (pe.errorcode == ParseException.NONE_ERROR_CODE || 
              	  pe.errorcode == ParseException.MISSING_CLOSEPAR ||
              	  pe.errorcode == ParseException.MISSING_CLOSESQPAR ||
              	  pe.errorcode == ParseException.MISSING_CLOSEBRACE){
        		  throw new ParseException(token, ParseException.ERROR_AT_ASSIGNMENT);
        	  }
        	  throw pe;
          }
          jj_consume_token(SEMICOLON);
        {if (true) return new Integer[]{1, 1};}
          break;
        default:
          jj_la1[7] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      jjtree.closeNodeScope(jjtn000, true);
      jjtc000 = false;
      {if (true) return new Integer[]{0, 0};}
        break;
      case LPAREN:
        jj_consume_token(LPAREN);
        formalList();
        jj_consume_token(RPAREN);
        jj_consume_token(LBRACE);
        preDecl();
        postDecl();
        label_5:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case LBRACE:
          case BOOLEAN:
          case IF:
          case WHILE:
          case INTEGER:
          case LONG:
          case ID:
            ;
            break;
          default:
            jj_la1[8] = jj_gen;
            break label_5;
          }
          temp = statement();
                  subWeight += temp[0];
                  count += temp[1];
        }
        _return();
        exp();
                subWeight += 1;
                count += 1;
        jj_consume_token(SEMICOLON);
        jj_consume_token(RBRACE);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
          {if (true) return new Integer[]{subWeight, count};}
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public Integer[] mid_varDecl() throws ParseException {
 /*@bgen(jjtree) VarDecl */
  SimpleNode jjtn000 = new SimpleNode(JJTVARDECL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      type();
      id();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SEMICOLON:
        jj_consume_token(SEMICOLON);
        break;
      case ASSIGN:
        assign();
        try{
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INTEGER_LITERAL:
        case LONG_LITERAL:
        case LPAREN:
        case NOT:
        case FALSE:
        case NEW:
        case THIS:
        case TRUE:
        case ID:
          exp();
          break;
        case LSQPAREN:
          jj_consume_token(LSQPAREN);
          expList();
          jj_consume_token(RSQPAREN);
          break;
        default:
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        }
        catch (ParseException pe){
      	  if (pe.errorcode == ParseException.NONE_ERROR_CODE || 
      		  pe.errorcode == ParseException.MISSING_CLOSEPAR ||
      		  pe.errorcode == ParseException.MISSING_CLOSESQPAR ||
      		  pe.errorcode == ParseException.MISSING_CLOSEBRACE){
      		  throw new ParseException(token, ParseException.ERROR_AT_ASSIGNMENT);
      	  }
      	  throw pe;
        }
        jj_consume_token(SEMICOLON);
        {if (true) return new Integer[]{1, 1};}
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return new Integer[]{0, 0};}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public void formalList() throws ParseException {
 /*@bgen(jjtree) FormalList */
  SimpleNode jjtn000 = new SimpleNode(JJTFORMALLIST);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
      case INTEGER:
      case LONG:
        type();
        break;
      case ID:
        id();
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      id();
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[13] = jj_gen;
          break label_6;
        }
        jj_consume_token(COMMA);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BOOLEAN:
        case INTEGER:
        case LONG:
          type();
          break;
        case ID:
          id();
          break;
        default:
          jj_la1[14] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        id();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void preDecl() throws ParseException {
 /*@bgen(jjtree) PreDecl */
  SimpleNode jjtn000 = new SimpleNode(JJTPREDECL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      pre();
      jj_consume_token(COLON);
      exp();
      jj_consume_token(SEMICOLON);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void postDecl() throws ParseException {
 /*@bgen(jjtree) PostDecl */
  SimpleNode jjtn000 = new SimpleNode(JJTPOSTDECL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      post();
      jj_consume_token(COLON);
      exp();
      jj_consume_token(SEMICOLON);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void type() throws ParseException {
 /*@bgen(jjtree) Type */
  SimpleNode jjtn000 = new SimpleNode(JJTTYPE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      if (jj_2_1(3)) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INTEGER:
          integerArray();
          break;
        case LONG:
          longArray();
          break;
        default:
          jj_la1[15] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BOOLEAN:
        case INTEGER:
        case LONG:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case INTEGER:
            integer();
            break;
          case LONG:
            _long();
            break;
          case BOOLEAN:
            _boolean();
            break;
          default:
            jj_la1[16] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          break;
        default:
          jj_la1[17] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public Integer[] statement() throws ParseException {
 /*@bgen(jjtree) Statement */
  SimpleNode jjtn000 = new SimpleNode(JJTSTATEMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);Integer[] temp;
  Integer subWeight = 0;
  Integer count = 0;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
      case INTEGER:
      case LONG:
        temp = mid_varDecl();
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return temp;}
        break;
      case LBRACE:
        jj_consume_token(LBRACE);
        label_7:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case LBRACE:
          case BOOLEAN:
          case IF:
          case WHILE:
          case INTEGER:
          case LONG:
          case ID:
            ;
            break;
          default:
            jj_la1[18] = jj_gen;
            break label_7;
          }
          temp = statement();
      subWeight += temp[0];
      count += temp[1];
        }
        jj_consume_token(RBRACE);
              jjtree.closeNodeScope(jjtn000, true);
              jjtc000 = false;
             {if (true) return new Integer[]{subWeight, count};}
        break;
      case IF:
        _if();
        jj_consume_token(LPAREN);
        exp();
        jj_consume_token(RPAREN);
        temp = statement();
    subWeight = temp[0];
    count += temp[1];
        _else();
        temp = statement();
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    subWeight += temp[0];
    count += temp[1];
    subWeight = subWeight * 2;
    {if (true) return new Integer[]{subWeight, count};}
        break;
      case WHILE:
        _while();
        jj_consume_token(LPAREN);
        exp();
        jj_consume_token(RPAREN);
        temp = statement();
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    subWeight = temp[0] * 4;
    count = temp[1];
    {if (true) return new Integer[]{subWeight, count};}
        break;
      case ID:
        id();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ID:
          id();
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case SEMICOLON:
            jj_consume_token(SEMICOLON);
            break;
          case ASSIGN:
            assign();
            try{
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case INTEGER_LITERAL:
            case LONG_LITERAL:
            case LPAREN:
            case NOT:
            case FALSE:
            case NEW:
            case THIS:
            case TRUE:
            case ID:
              exp();
              break;
            case LSQPAREN:
              jj_consume_token(LSQPAREN);
              expList();
              jj_consume_token(RSQPAREN);
              break;
            default:
              jj_la1[19] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
            }
            }
            catch (ParseException pe){
          	  if (pe.errorcode == ParseException.NONE_ERROR_CODE || 
              	  pe.errorcode == ParseException.MISSING_CLOSEPAR ||
                  pe.errorcode == ParseException.MISSING_CLOSESQPAR ||
                  pe.errorcode == ParseException.MISSING_CLOSEBRACE){
          		  throw new ParseException(token, ParseException.ERROR_AT_ASSIGNMENT);
          	  }
          	  throw pe;
            }
            jj_consume_token(SEMICOLON);
                {if (true) return new Integer[]{1, 1};}
            break;
          default:
            jj_la1[20] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        jjtree.closeNodeScope(jjtn000, true);
        jjtc000 = false;
        {if (true) return new Integer[]{0, 0};}
          break;
        case LSQPAREN:
        case ASSIGN:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case ASSIGN:
            assign();
            try{
            	exp();
            }
            catch (ParseException pe){
          	  if (pe.errorcode == ParseException.NONE_ERROR_CODE || 
              	  pe.errorcode == ParseException.MISSING_CLOSEPAR ||
              	  pe.errorcode == ParseException.MISSING_CLOSESQPAR ||
              	  pe.errorcode == ParseException.MISSING_CLOSEBRACE){
          		  throw new ParseException(token, ParseException.ERROR_AT_ASSIGNMENT);
          	  }
          	  throw pe;
            }
            jj_consume_token(SEMICOLON);
            break;
          case LSQPAREN:
            jj_consume_token(LSQPAREN);
            exp();
            jj_consume_token(RSQPAREN);
            assign();
            try{
            	exp();
            }
            catch (ParseException pe){
          	  if (pe.errorcode == ParseException.NONE_ERROR_CODE || 
          		  pe.errorcode == ParseException.MISSING_CLOSEPAR ||
                  pe.errorcode == ParseException.MISSING_CLOSESQPAR ||
                  pe.errorcode == ParseException.MISSING_CLOSEBRACE){
          		  throw new ParseException(token, ParseException.ERROR_AT_ASSIGNMENT);
          	  }
          	  throw pe;
            }
            jj_consume_token(SEMICOLON);
            break;
          default:
            jj_la1[21] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
        {if (true) return new Integer[]{1, 1};}
          break;
        default:
          jj_la1[22] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[23] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  final public void exp() throws ParseException {
 /*@bgen(jjtree) Exp */
  SimpleNode jjtn000 = new SimpleNode(JJTEXP);
  jjtn000.jjtSetValue(token);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NEW:
        _new();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INTEGER:
        case LONG:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case INTEGER:
            integer();
            break;
          case LONG:
            _long();
            break;
          default:
            jj_la1[24] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          jj_consume_token(LSQPAREN);
          exp();
          jj_consume_token(RSQPAREN);
          break;
        case ID:
          id();
          jj_consume_token(LPAREN);
          jj_consume_token(RPAREN);
          break;
        default:
          jj_la1[25] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      case INTEGER_LITERAL:
      case LONG_LITERAL:
      case LPAREN:
      case FALSE:
      case THIS:
      case TRUE:
      case ID:
        lowLevelExp();
        break;
      case NOT:
        jj_consume_token(NOT);
        exp();
        break;
      default:
        jj_la1[26] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void lowLevelExp() throws ParseException {
 /*@bgen(jjtree) LowLevelExp */
  SimpleNode jjtn000 = new SimpleNode(JJTLOWLEVELEXP);
  jjtn000.jjtSetValue(token);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      highLevelExp();
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS:
        case MINUS:
        case AND:
        case OR:
          ;
          break;
        default:
          jj_la1[27] = jj_gen;
          break label_8;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case AND:
          and();
          break;
        case OR:
          or();
          break;
        case PLUS:
          plus();
          break;
        case MINUS:
          minus();
          break;
        default:
          jj_la1[28] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        try{
        	highLevelExp();
        }
        catch (ParseException pe){
        	if (pe.errorcode == ParseException.NONE_ERROR_CODE || 
        		pe.errorcode == ParseException.MISSING_CLOSEPAR ||
        		pe.errorcode == ParseException.MISSING_CLOSESQPAR ||
        		pe.errorcode == ParseException.MISSING_CLOSEBRACE){
        		throw new ParseException(token, ParseException.ERROR_AFTER_OPERATOR);
        	}
        	throw pe;
        }
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void highLevelExp() throws ParseException {
 /*@bgen(jjtree) HighLevelExp */
  SimpleNode jjtn000 = new SimpleNode(JJTHIGHLEVELEXP);
  jjtn000.jjtSetValue(token);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      unaryExp();
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LESSTHAN:
        case LARGERTHAN:
        case MUL:
        case DIVISION:
          ;
          break;
        default:
          jj_la1[29] = jj_gen;
          break label_9;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LESSTHAN:
          lessthan();
          break;
        case LARGERTHAN:
          largerthan();
          break;
        case MUL:
          multi();
          break;
        case DIVISION:
          division();
          break;
        default:
          jj_la1[30] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        try{
        	unaryExp();
        }
        catch (ParseException pe){
        	if (pe.errorcode == ParseException.NONE_ERROR_CODE || 
        		pe.errorcode == ParseException.MISSING_CLOSEPAR ||
            	pe.errorcode == ParseException.MISSING_CLOSESQPAR ||
            	pe.errorcode == ParseException.MISSING_CLOSEBRACE){
        		throw new ParseException(token, ParseException.ERROR_AFTER_OPERATOR);
        	}
        	throw pe;
        }
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void unaryExp() throws ParseException {
 /*@bgen(jjtree) UnaryExp */
  SimpleNode jjtn000 = new SimpleNode(JJTUNARYEXP);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LONG_LITERAL:
        long_literal();
        break;
      case INTEGER_LITERAL:
        int_literal();
        break;
      case TRUE:
        _true();
        break;
      case FALSE:
        _false();
        break;
      case ID:
        id();
        break;
      case THIS:
        _this();
        break;
      case LPAREN:
        jj_consume_token(LPAREN);
        exp();
        jj_consume_token(RPAREN);
        break;
      default:
        jj_la1[31] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LSQPAREN:
      case DOT:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LSQPAREN:
          jj_consume_token(LSQPAREN);
          exp();
          jj_consume_token(RSQPAREN);
          break;
        case DOT:
          dot();
          try{
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case LENGTH:
            length();
            break;
          case ID:
            id();
            jj_consume_token(LPAREN);
            expList();
            jj_consume_token(RPAREN);
            break;
          default:
            jj_la1[32] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }}
          catch (ParseException pe){
        	  if (pe.errorcode == ParseException.MISSING_CLOSEPAR){
        		  throw pe;
        	  }
        	  throw new ParseException(token, ParseException.MISSING_AFTER_DOT);
          }
          break;
        default:
          jj_la1[33] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[34] = jj_gen;
        ;
      }
    } catch (Throwable jjte000) {
  if (jjtc000) {
    jjtree.clearNodeScope(jjtn000);
    jjtc000 = false;
  } else {
    jjtree.popNode();
  }
  if (jjte000 instanceof RuntimeException) {
    {if (true) throw (RuntimeException)jjte000;}
  }
  if (jjte000 instanceof ParseException) {
    {if (true) throw (ParseException)jjte000;}
  }
  {if (true) throw (Error)jjte000;}
    } finally {
  if (jjtc000) {
    jjtree.closeNodeScope(jjtn000, true);
  }
    }
  }

  final public void expList() throws ParseException {
 /*@bgen(jjtree) ExpList */
  SimpleNode jjtn000 = new SimpleNode(JJTEXPLIST);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      exp();
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[35] = jj_gen;
          break label_10;
        }
        jj_consume_token(COMMA);
        exp();
      }
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void long_literal() throws ParseException {
 /*@bgen(jjtree) Long_Literal */
  SimpleNode jjtn000 = new SimpleNode(JJTLONG_LITERAL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(LONG_LITERAL);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void int_literal() throws ParseException {
 /*@bgen(jjtree) Int_Literal */
  SimpleNode jjtn000 = new SimpleNode(JJTINT_LITERAL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(INTEGER_LITERAL);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void id() throws ParseException {
 /*@bgen(jjtree) ID */
  SimpleNode jjtn000 = new SimpleNode(JJTID);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(ID);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void assign() throws ParseException {
 /*@bgen(jjtree) Assign */
  SimpleNode jjtn000 = new SimpleNode(JJTASSIGN);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(ASSIGN);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void pre() throws ParseException {
 /*@bgen(jjtree) Pre */
  SimpleNode jjtn000 = new SimpleNode(JJTPRE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(PRE);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void post() throws ParseException {
 /*@bgen(jjtree) Post */
  SimpleNode jjtn000 = new SimpleNode(JJTPOST);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(POST);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void integer() throws ParseException {
 /*@bgen(jjtree) Integer */
  SimpleNode jjtn000 = new SimpleNode(JJTINTEGER);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(INTEGER);
      jjtn000.jjtSetValue("int");
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void integerArray() throws ParseException {
 /*@bgen(jjtree) IntegerArray */
  SimpleNode jjtn000 = new SimpleNode(JJTINTEGERARRAY);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(INTEGER);
      jj_consume_token(LSQPAREN);
      jj_consume_token(RSQPAREN);
      jjtn000.jjtSetValue("int[]");
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void not() throws ParseException {
 /*@bgen(jjtree) Not */
  SimpleNode jjtn000 = new SimpleNode(JJTNOT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(NOT);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void and() throws ParseException {
 /*@bgen(jjtree) And */
  SimpleNode jjtn000 = new SimpleNode(JJTAND);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(AND);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void or() throws ParseException {
 /*@bgen(jjtree) Or */
  SimpleNode jjtn000 = new SimpleNode(JJTOR);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(OR);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void plus() throws ParseException {
 /*@bgen(jjtree) Plus */
  SimpleNode jjtn000 = new SimpleNode(JJTPLUS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(PLUS);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void minus() throws ParseException {
 /*@bgen(jjtree) Minus */
  SimpleNode jjtn000 = new SimpleNode(JJTMINUS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(MINUS);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void multi() throws ParseException {
 /*@bgen(jjtree) Multi */
  SimpleNode jjtn000 = new SimpleNode(JJTMULTI);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(MUL);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void division() throws ParseException {
 /*@bgen(jjtree) Division */
  SimpleNode jjtn000 = new SimpleNode(JJTDIVISION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(DIVISION);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void lessthan() throws ParseException {
 /*@bgen(jjtree) Lessthan */
  SimpleNode jjtn000 = new SimpleNode(JJTLESSTHAN);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(LESSTHAN);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void largerthan() throws ParseException {
 /*@bgen(jjtree) Largerthan */
  SimpleNode jjtn000 = new SimpleNode(JJTLARGERTHAN);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Token t = jj_consume_token(LARGERTHAN);
      jjtn000.jjtSetValue(t);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void dot() throws ParseException {
 /*@bgen(jjtree) Dot */
  SimpleNode jjtn000 = new SimpleNode(JJTDOT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(DOT);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void length() throws ParseException {
 /*@bgen(jjtree) Length */
  SimpleNode jjtn000 = new SimpleNode(JJTLENGTH);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(LENGTH);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _main() throws ParseException {
 /*@bgen(jjtree) Main */
  SimpleNode jjtn000 = new SimpleNode(JJTMAIN);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(MAIN);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _long() throws ParseException {
 /*@bgen(jjtree) Long */
  SimpleNode jjtn000 = new SimpleNode(JJTLONG);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(LONG);
      jjtn000.jjtSetValue("long");
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void longArray() throws ParseException {
 /*@bgen(jjtree) LongArray */
  SimpleNode jjtn000 = new SimpleNode(JJTLONGARRAY);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(LONG);
      jj_consume_token(LSQPAREN);
      jj_consume_token(RSQPAREN);
      jjtn000.jjtSetValue("long[]");
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _boolean() throws ParseException {
 /*@bgen(jjtree) Boolean */
  SimpleNode jjtn000 = new SimpleNode(JJTBOOLEAN);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(BOOLEAN);
      jjtn000.jjtSetValue("boolean");
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _class() throws ParseException {
 /*@bgen(jjtree) Class */
  SimpleNode jjtn000 = new SimpleNode(JJTCLASS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(CLASS);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _static() throws ParseException {
 /*@bgen(jjtree) Static */
  SimpleNode jjtn000 = new SimpleNode(JJTSTATIC);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(STATIC);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _void() throws ParseException {
 /*@bgen(jjtree) Void */
  SimpleNode jjtn000 = new SimpleNode(JJTVOID);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(VOID);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _extends() throws ParseException {
 /*@bgen(jjtree) Extends */
  SimpleNode jjtn000 = new SimpleNode(JJTEXTENDS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(EXTENDS);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _return() throws ParseException {
 /*@bgen(jjtree) Return */
  SimpleNode jjtn000 = new SimpleNode(JJTRETURN);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(RETURN);
    }
    catch (ParseException e){
    	throw new ParseException(token, ParseException.MISSING_RETURN);
    }
    finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _if() throws ParseException {
 /*@bgen(jjtree) If */
  SimpleNode jjtn000 = new SimpleNode(JJTIF);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(IF);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _else() throws ParseException {
 /*@bgen(jjtree) Else */
  SimpleNode jjtn000 = new SimpleNode(JJTELSE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(ELSE);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _while() throws ParseException {
 /*@bgen(jjtree) While */
  SimpleNode jjtn000 = new SimpleNode(JJTWHILE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(WHILE);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _new() throws ParseException {
 /*@bgen(jjtree) New */
  SimpleNode jjtn000 = new SimpleNode(JJTNEW);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(NEW);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _this() throws ParseException {
 /*@bgen(jjtree) This */
  SimpleNode jjtn000 = new SimpleNode(JJTTHIS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(THIS);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _true() throws ParseException {
 /*@bgen(jjtree) True */
  SimpleNode jjtn000 = new SimpleNode(JJTTRUE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(TRUE);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  final public void _false() throws ParseException {
 /*@bgen(jjtree) False */
  SimpleNode jjtn000 = new SimpleNode(JJTFALSE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(FALSE);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_3R_12() {
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3R_11() {
    if (jj_3R_13()) return true;
    return false;
  }

  private boolean jj_3R_13() {
    if (jj_scan_token(INTEGER)) return true;
    if (jj_scan_token(LSQPAREN)) return true;
    if (jj_scan_token(RSQPAREN)) return true;
    return false;
  }

  private boolean jj_3_1() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_11()) {
    jj_scanpos = xsp;
    if (jj_3R_12()) return true;
    }
    return false;
  }

  private boolean jj_3R_14() {
    if (jj_scan_token(LONG)) return true;
    if (jj_scan_token(LSQPAREN)) return true;
    if (jj_scan_token(RSQPAREN)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public TokenManager token_source;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[36];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x80000000,0x40000000,0x40004000,0x0,0x40000000,0x40000000,0x8001700,0x50000,0x40004000,0x50400,0x8001700,0x50000,0x40000000,0x10000000,0x40000000,0x0,0x40000000,0x40000000,0x40004000,0x8001700,0x50000,0x41000,0x41000,0x40004000,0x0,0x0,0x8000700,0x6600000,0x6600000,0x1980000,0x1980000,0x700,0x0,0x21000,0x21000,0x10000000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x20060,0x20078,0x2,0x20060,0x20060,0x20c84,0x0,0x20078,0x0,0x20c84,0x0,0x20060,0x0,0x20060,0x60,0x60,0x60,0x20078,0x20c84,0x0,0x0,0x20000,0x20078,0x60,0x20060,0x20c84,0x0,0x0,0x0,0x0,0x20c04,0x28000,0x0,0x0,0x0,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Parser(ArrayList<Token> tokenSource) {
     this(tokenSource, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(ArrayList<Token> tokenSource, String encoding) {
    token_source = new TokenManager(tokenSource);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 36; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(ArrayList<Token> tokenSource) {
     ReInit(tokenSource, null);
  }

  /** Reinitialise. */
  public void ReInit(ArrayList<Token> tokenSource, String encoding) {
    token_source.ReInit(tokenSource);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 36; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Parser(TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 36; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 36; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[53];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 36; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 53; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
        
        switch (i){
        case PRE:
        	return new ParseException(token, ParseException.MISSING_PRE);
        case POST:
        	return new ParseException(token, ParseException.MISSING_POST);
        case CLASS:
        	return new ParseException(token, ParseException.MISSING_CLASS);
        case STATIC:
        	return new ParseException(token, ParseException.MISSING_STATIC);
        case VOID:
        	return new ParseException(token, ParseException.MISSING_VOID);
        case MAIN:
        	return new ParseException(token, ParseException.MISSING_MAIN);
        case RBRACE:
        	return new ParseException(token, ParseException.MISSING_CLOSEBRACE);
        case RPAREN:
        	return new ParseException(token, ParseException.MISSING_CLOSEPAR);
        case RSQPAREN:
        	return new ParseException(token, ParseException.MISSING_CLOSESQPAR);
        case SEMICOLON:
        	return new ParseException(token, ParseException.MISSING_SEMICOLON);
        }
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
