package tests.lexer.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lexer.daos.Symbol;
import lexer.daos.TokenKind;

public class SymbolRegressionTest {

  @Test
  public void testGetLexeme() {
    String lexeme = "testlexeme";
    TokenKind kind = TokenKind.BogusToken;

    Symbol symbol = new Symbol(lexeme, kind);

    assertEquals(lexeme, symbol.getLexeme());
  }

  @Test
  public void testGetTokenKind() {
    String lexeme = "testlexeme";
    TokenKind kind = TokenKind.BogusToken;

    Symbol symbol = new Symbol(lexeme, kind);

    assertEquals(kind, symbol.getTokenKind());
  }
}