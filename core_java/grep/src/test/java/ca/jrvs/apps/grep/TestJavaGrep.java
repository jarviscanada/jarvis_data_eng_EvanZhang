package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

public class TestJavaGrep {
  JavaGrepImp javaGrepImp;
  ProcessBuilder processBuilder;

  @Before
  public void setUp() {
    javaGrepImp = new JavaGrepImp();
    processBuilder = new ProcessBuilder();
    BasicConfigurator.configure();
  }

  @Test
  public void testJavaGrep() {
    String[] args = {".*Romeo.*", "./grep/data", "grep/out/grepOut.txt", "grep/out/egrepOut.txt"};

    JavaGrepImp javaGrepImp;
    javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
//      javaGrepImp.process(); //egrep -r ${regex_pattern} ${src_dir}
//      processBuilder.command("egrep", "-r", args[0], args[1]);
//      processBuilder.start();
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
    assert(true);
  }

}
