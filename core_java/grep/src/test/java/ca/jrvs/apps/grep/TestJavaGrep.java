package ca.jrvs.apps.grep;

import java.io.File;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.io.FileUtils;

public class TestJavaGrep {
  JavaGrepImp javaGrepImp;

  @Before
  public void setUp() {
    javaGrepImp = new JavaGrepImp();
    BasicConfigurator.configure();
  }

  @Test
  public void testJavaRomeoJuliet() {
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
  public void testJavaRomeo() {
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
  public void testJavaJuliet() {
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

}
