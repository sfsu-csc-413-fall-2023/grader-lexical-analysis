package tests.assignment_1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lexer.daos.Symbol;
import lexer.daos.Token;
import lexer.daos.TokenKind;

public class TokenTest {

  @Test
  public void testLegacyConstructor() {
    int left = 8, right = 42;
    String lexeme = "testlexeme";
    TokenKind kind = TokenKind.BogusToken;

    Symbol symbol = new Symbol(lexeme, kind);
    Token token = new Token(symbol, left, right);

    assertEquals(-1, token.getLineNumber());
  }

  @Test
  public void testNewConstructor() {
    int left = 8, right = 42;
    int lineNumber = 1873;
    String lexeme = "testlexeme";
    TokenKind kind = TokenKind.BogusToken;

    Symbol symbol = new Symbol(lexeme, kind);
    Token token = new Token(symbol, left, right, lineNumber);

    assertEquals(lineNumber, token.getLineNumber());
  }

  @Test
  public void testTokenStringRepresentation() {
    int left = 8, right = 42;
    int lineNumber = 1873;
    String lexeme = "testlexeme";
    TokenKind kind = TokenKind.BogusToken;
    String expectedString = "testlexeme           left: 8        right: 42       line: 1873     BogusToken";

    Symbol symbol = new Symbol(lexeme, kind);
    Token token = new Token(symbol, left, right, lineNumber);
    
    assertEquals(expectedString, token.toString());
  }
}
