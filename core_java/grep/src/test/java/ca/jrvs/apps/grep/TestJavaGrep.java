package ca.jrvs.apps.grep;

import java.io.File;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.io.FileUtils;

public class TestJavaGrep {
  JavaGrepImp javaGrepImp;
  JavaGrepLambdaImp javaGrepLambdaImp;

  @Before
  public void setUp() {
    javaGrepImp = new JavaGrepImp();
    javaGrepLambdaImp = new JavaGrepLambdaImp();
    BasicConfigurator.configure();
  }

  @Test
  public void testGrepRomeoJuliet() {
    String[] args = {".*Romeo.*Juliet.*", "./data", "./out/javaGrepRomeoJuliet.txt", "./out/RomeoJuliet.txt"};

    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
      assert(FileUtils.contentEquals(new File(args[2]), new File(args[3])));
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }

  @Test
  public void testGrepRomeo() {
    String[] args = {".*Romeo.*", "./data", "./out/javaGrepRomeo.txt", "./out/Romeo.txt"};
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
      assert(FileUtils.contentEquals(new File(args[2]), new File(args[3])));
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }

  @Test
  public void testGrepJuliet() {
    String[] args = {".*Juliet.*", "./data", "./out/javaGrepJuliet.txt", "./out/Juliet.txt"};
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
      assert(FileUtils.contentEquals(new File(args[2]), new File(args[3])));
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }

  @Test
  public void testGrepLambdaJuliet() {
    String[] args = {".*Juliet.*", "./data", "./out/javaGrepLambdaJuliet.txt", "./out/Juliet.txt"};
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try {
      javaGrepLambdaImp.process();
      assert(FileUtils.contentEquals(new File(args[2]), new File(args[3])));
    } catch (Exception ex) {
      javaGrepLambdaImp.logger.error(ex.getMessage(), ex);
    }
  }
}
