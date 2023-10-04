package tests.lexer.readers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import lexer.readers.SourceFileReader;

public class SourceFileReaderRegressionTest {

  private File getEmptyTestFile() throws IOException {
    return File.createTempFile("temp", null, null);
  }

  private File getTestFile(String content) throws IOException {
    File file = File.createTempFile("temp", null, null);

    try (FileWriter writer = new FileWriter(file)) {
      writer.write(content);
    }

    return file;
  }

  @Test
  public void testReadOnEmptyFile() throws IOException {
    File empty = getEmptyTestFile();

    try (SourceFileReader reader = new SourceFileReader(empty.getAbsolutePath().toString())) {
      assertEquals('\0', reader.read());
    }
  }

  @Test
  public void testReadOnlyOneCharacter() throws IOException {
    char content = 'a';
    File file = getTestFile(String.format("%c", content));

    try (SourceFileReader reader = new SourceFileReader(file.getAbsolutePath().toString())) {
      assertEquals(content, reader.read());
    }
  }

  @Test
  public void testRead() throws IOException {
    String content = "abc\n12 3";
    File file = getTestFile(content);

    try (SourceFileReader reader = new SourceFileReader(file.getAbsolutePath().toString())) {
      assertEquals(content.charAt(0), reader.read());
      assertEquals(content.charAt(1), reader.read());
      assertEquals(content.charAt(2), reader.read());
      assertEquals(content.charAt(3), reader.read());
      assertEquals(content.charAt(4), reader.read());
      assertEquals(content.charAt(5), reader.read());
      assertEquals(content.charAt(6), reader.read());
      assertEquals(content.charAt(7), reader.read());
    }
  }

  @Test
  public void testReadEndOfFile() throws IOException {
    char content = 'a';
    File file = getTestFile(String.format("%c", content));

    try (SourceFileReader reader = new SourceFileReader(file.getAbsolutePath().toString())) {
      assertEquals(content, reader.read());
      assertEquals('\0', reader.read());
      assertEquals('\0', reader.read());
    }
  }

  @Test
  public void testGetColumnOnEmptyFile() throws IOException {
    File empty = getEmptyTestFile();

    try (SourceFileReader reader = new SourceFileReader(empty.getAbsolutePath().toString())) {
      assertEquals(-1, reader.getColumn());
    }
  }

  @Test
  public void testGetColumn() throws IOException {
    String content = "abc\n12 3";
    File file = getTestFile(content);

    try (SourceFileReader reader = new SourceFileReader(file.getAbsolutePath().toString())) {
      reader.read();
      assertEquals(0, reader.getColumn());
      reader.read();
      assertEquals(1, reader.getColumn());
      reader.read();
      assertEquals(2, reader.getColumn());
      reader.read();
      assertEquals(3, reader.getColumn());
      reader.read();
      assertEquals(0, reader.getColumn());
      reader.read();
      assertEquals(1, reader.getColumn());
      reader.read();
      assertEquals(2, reader.getColumn());
      reader.read();
      assertEquals(3, reader.getColumn());
    }
  }

  @Test
  public void testGetLineNumberOnEmptyFile() throws IOException {
    File empty = getEmptyTestFile();

    try (SourceFileReader reader = new SourceFileReader(empty.getAbsolutePath().toString())) {
      assertEquals(1, reader.getLineNumber());
    }
  }

  @Test
  public void testGetLineNumber() throws IOException {
    String content = "abc\n12 3";
    File file = getTestFile(content);

    try (SourceFileReader reader = new SourceFileReader(file.getAbsolutePath().toString())) {
      reader.read();
      assertEquals(1, reader.getLineNumber());
      reader.read();
      assertEquals(1, reader.getLineNumber());
      reader.read();
      assertEquals(1, reader.getLineNumber());
      reader.read();
      assertEquals(1, reader.getLineNumber());
      reader.read();
      assertEquals(2, reader.getLineNumber());
      reader.read();
      assertEquals(2, reader.getLineNumber());
      reader.read();
      assertEquals(2, reader.getLineNumber());
      reader.read();
      assertEquals(2, reader.getLineNumber());
    }
  }
}
