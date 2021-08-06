package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp extends JavaGrepImp {

  @Override
  public void process() throws IOException {

  }

  @Override
  public List<File> listFiles(String rootDir) {
    return null;
  }

  @Override
  public List<String> readLines(File inputFile) {
    return null;
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {

  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error(ex.getMessage(), ex);
    }
  }
}

