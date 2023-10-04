package tests.assignment_1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lexer.Lexer;

public class LexerOutputTest {

  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  public void testLexerOutput() throws Exception {
    File temp = File.createTempFile("atestfileforme", null);
    String path = temp.getAbsolutePath().toString();

    FileWriter writer = new FileWriter(temp);
    writer.write("int i\n");
    writer.write("program");
    writer.close();

    Lexer.main(new String[] { path });

    List<String> output = Arrays.asList(outputStreamCaptor.toString().split(System.lineSeparator()));

    assertEquals(output.get(0), "int                  left: 0        right: 2        line: 1        IntType");
    assertEquals(output.get(1), "i                    left: 4        right: 4        line: 1        Identifier");
    assertEquals(output.get(2), "program              left: 0        right: 6        line: 2        Program");
    assertEquals(output.get(3), "");
    assertEquals(output.get(4), "  1: int i");
    assertEquals(output.get(5), "  2: program");
  }

  @Test
  public void testLexerOutputSimpleX() throws Exception {
    File temp = File.createTempFile("atestfileforme", null);
    String path = temp.getAbsolutePath().toString();

    FileWriter writer = new FileWriter(temp);
    writer.write("program { int i int j\n");
    writer.write("   i = i + j + 7\n");
    writer.write("   j = write(i)\n");
    writer.write("}\n");
    writer.close();
    // this should get overwritten

    Lexer.main(new String[] { path });

    List<String> output = Arrays.asList(outputStreamCaptor.toString().split(System.lineSeparator()));

    assertEquals(output.get(0), "program              left: 0        right: 6        line: 1        Program");
    assertEquals(output.get(1), "{                    left: 8        right: 8        line: 1        LeftBrace");
    assertEquals(output.get(2), "int                  left: 10       right: 12       line: 1        IntType");
    assertEquals(output.get(3), "i                    left: 14       right: 14       line: 1        Identifier");
    assertEquals(output.get(4), "int                  left: 16       right: 18       line: 1        IntType");
    assertEquals(output.get(5), "j                    left: 20       right: 20       line: 1        Identifier");
    assertEquals(output.get(6), "i                    left: 3        right: 3        line: 2        Identifier");
    assertEquals(output.get(7), "=                    left: 5        right: 5        line: 2        Assign");
    assertEquals(output.get(8), "i                    left: 7        right: 7        line: 2        Identifier");
    assertEquals(output.get(9), "+                    left: 9        right: 9        line: 2        Plus");
    assertEquals(output.get(10), "j                    left: 11       right: 11       line: 2        Identifier");
    assertEquals(output.get(11), "+                    left: 13       right: 13       line: 2        Plus");
    assertEquals(output.get(12), "7                    left: 15       right: 15       line: 2        IntLit");
    assertEquals(output.get(13), "j                    left: 3        right: 3        line: 3        Identifier");
    assertEquals(output.get(14), "=                    left: 5        right: 5        line: 3        Assign");
    assertEquals(output.get(15), "write                left: 7        right: 11       line: 3        Identifier");
    assertEquals(output.get(16), "(                    left: 12       right: 12       line: 3        LeftParen");
    assertEquals(output.get(17), "i                    left: 13       right: 13       line: 3        Identifier");
    assertEquals(output.get(18), ")                    left: 14       right: 14       line: 3        RightParen");
    assertEquals(output.get(19), "}                    left: 0        right: 0        line: 4        RightBrace");
    assertEquals(output.get(20), "");
    assertEquals(output.get(21), "  1: program { int i int j");
    assertEquals(output.get(22), "  2:    i = i + j + 7");
    assertEquals(output.get(23), "  3:    j = write(i)");
    assertEquals(output.get(24), "  4: }");
  }
}
