package tests.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lexer.Lexer;
import lexer.daos.Token;
import lexer.daos.TokenKind;
import lexer.readers.IReader;
import tests.helpers.TestReader;

public class LexerTest {

  @Test
  public void testEndOfFile() throws Exception {
    IReader testReader = new TestReader(List.of("   \n", " "));

    try (Lexer lexer = new Lexer(testReader);) {
      Token token = lexer.nextToken();

      assertEquals(TokenKind.EOF, token.getTokenKind());
      assertEquals(1, token.getLeftPosition());
      assertEquals(1, token.getRightPosition());
    }
  }

  @Test
  public void testWhitespaceIsIgnored() throws Exception {
    IReader testReader = new TestReader(List.of("\r\n", "\t   "));

    try (Lexer lexer = new Lexer(testReader);) {
      Token token = lexer.nextToken();

      assertEquals(TokenKind.EOF, token.getTokenKind());
      assertEquals(4, token.getLeftPosition());
      assertEquals(4, token.getRightPosition());
    }
  }

  @ParameterizedTest
  @MethodSource("provideBuiltInKeywords")
  public void testBuiltInKeywords(String source, TokenKind kind, int expectedStart, int expectedEnd)
      throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getTokenKind());
      assertEquals(expectedStart, token.getLeftPosition());
      assertEquals(expectedEnd, token.getRightPosition());
    }
  }

  @ParameterizedTest
  @MethodSource("provideBuiltInOperators")
  public void testBuiltInOperators(String source, TokenKind kind, int expectedStart, int expectedEnd)
      throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getTokenKind());
      assertEquals(expectedStart, token.getLeftPosition());
      assertEquals(expectedEnd, token.getRightPosition());
    }
  }

  @ParameterizedTest
  @MethodSource("provideBuiltInSeparators")
  public void testBuiltInSeparators(String source, TokenKind kind, int expectedStart, int expectedEnd)
      throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getTokenKind());
      assertEquals(expectedStart, token.getLeftPosition());
      assertEquals(expectedEnd, token.getRightPosition());
    }
  }

  @Test
  public void testComments() throws Exception {
    // Comments are a special case since the token gets "ignored" by the lexer
    try (Lexer lexer = new Lexer(new TestReader(List.of(" // ")))) {
      Token token = lexer.nextToken();
      // We expect EOF in this case because we only have a comment, which should
      // be ignored by the lexer. Lexer would normally reset start position
      // when next token encountered, but here we use string's length
      // (column 4 is where we encounter EOF)

      assertEquals("\0", token.getLexeme());
      assertEquals(TokenKind.EOF, token.getTokenKind());
      assertEquals(4, token.getLeftPosition());
      assertEquals(4, token.getRightPosition());
    }
  }

  @ParameterizedTest
  @MethodSource("provideIdentifierExamples")
  public void testIdentifiers(String source, TokenKind kind, int expectedStart, int expectedEnd)
      throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getTokenKind());
      assertEquals(expectedStart, token.getLeftPosition());
      assertEquals(expectedEnd, token.getRightPosition());
    }
  }

  @ParameterizedTest
  @MethodSource("provideIntegerExamples")
  public void testIntegers(String source, TokenKind kind, int expectedStart, int expectedEnd)
      throws Exception {
    try (Lexer lexer = new Lexer(new TestReader(List.of(source)))) {
      Token token = lexer.nextToken();

      assertEquals(source.trim(), token.getLexeme());
      assertEquals(kind, token.getTokenKind());
      assertEquals(expectedStart, token.getLeftPosition());
      assertEquals(expectedEnd, token.getRightPosition());
    }
  }

  private static Stream<Arguments> provideBuiltInKeywords() {
    return Stream.of(
        Arguments.of(" program  ", TokenKind.Program, 1, 7),
        Arguments.of(" int  ", TokenKind.IntType, 1, 3),
        Arguments.of(" boolean  ", TokenKind.BooleanType, 1, 7),
        Arguments.of(" if  ", TokenKind.If, 1, 2),
        Arguments.of(" then  ", TokenKind.Then, 1, 4),
        Arguments.of(" else  ", TokenKind.Else, 1, 4),
        Arguments.of(" while  ", TokenKind.While, 1, 5),
        Arguments.of(" function  ", TokenKind.Function, 1, 8),
        Arguments.of(" return  ", TokenKind.Return, 1, 6));
  }

  private static Stream<Arguments> provideBuiltInOperators() {
    return Stream.of(
        Arguments.of(" + ", TokenKind.Plus, 1, 1),
        Arguments.of(" - ", TokenKind.Minus, 1, 1),
        Arguments.of(" / ", TokenKind.Divide, 1, 1),
        Arguments.of(" * ", TokenKind.Multiply, 1, 1),
        Arguments.of(" & ", TokenKind.And, 1, 1),
        Arguments.of(" | ", TokenKind.Or, 1, 1),
        Arguments.of(" = ", TokenKind.Assign, 1, 1),
        Arguments.of(" == ", TokenKind.Equal, 1, 2),
        Arguments.of(" != ", TokenKind.NotEqual, 1, 2),
        Arguments.of(" < ", TokenKind.Less, 1, 1),
        Arguments.of(" <= ", TokenKind.LessEqual, 1, 2));
  }

  private static Stream<Arguments> provideBuiltInSeparators() {
    return Stream.of(
        Arguments.of(" , ", TokenKind.Comma, 1, 1),
        Arguments.of(" ( ", TokenKind.LeftParen, 1, 1),
        Arguments.of(" ) ", TokenKind.RightParen, 1, 1),
        Arguments.of(" { ", TokenKind.LeftBrace, 1, 1),
        Arguments.of(" } ", TokenKind.RightBrace, 1, 1));
  }

  private static Stream<Arguments> provideIdentifierExamples() {
    return Stream.of(
        Arguments.of(" _underscore ", TokenKind.Identifier, 1, 11),
        Arguments.of(" i ", TokenKind.Identifier, 1, 1),
        Arguments.of(" ab ", TokenKind.Identifier, 1, 2),
        Arguments.of(" abalamahalamatandra ", TokenKind.Identifier, 1, 19));
  }

  private static Stream<Arguments> provideIntegerExamples() {
    return Stream.of(
        Arguments.of(" 012345 ", TokenKind.IntLit, 1, 6),
        Arguments.of(" 123 ", TokenKind.IntLit, 1, 3));
  }
}
