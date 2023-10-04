package tests.helpers;

import java.util.List;

import lexer.readers.IReader;

public class TestReader implements IReader {
  private int lineNumber = 1;
  private int column = -1;
  private boolean atEndOfLine = false;
  private List<String> sourceLines;

  public TestReader(List<String> sourceLines) {
    this.sourceLines = sourceLines;
  }

  @Override
  public void close() {
    // no-op
  }

  @Override
  public char read() {
    if (this.atEndOfLine) {
      this.atEndOfLine = false;
      this.column = -1;
      this.lineNumber++;
    }
    this.column++;

    char c = '\0';

    try {
      c = sourceLines.get(lineNumber - 1).charAt(this.column);
    } catch (IndexOutOfBoundsException exception) {
      return c;
    }

    if (c == '\n') {
      this.atEndOfLine = true;
    }

    return c;
  }

  @Override
  public int getColumn() {
    return this.column;
  }

  @Override
  public int getLineNumber() {
    return this.lineNumber;
  }
}
