package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp extends JavaGrepImp {
  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    List<File> listFiles = listFiles(getRootPath());

    listFiles.stream()
        .map(this::readLines)
        .flatMap(Collection::stream)
        .filter(this::containsPattern)
        .forEach(matchedLines::add);

    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<>();
    File dir = new File(rootDir);
    try {
      Files.walk(dir.toPath())
          .filter(Files::isRegularFile)
          .forEach(path -> files.add(path.toFile()));

    } catch (IOException ex) {
      logger.error(ex.getMessage(), ex);
    }
    return files;
  }

  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<>();


    try {
      Files.lines(inputFile.toPath())
          .filter(Objects::nonNull)
          .forEach(line -> lines.add(inputFile + ":" + line));

    } catch (IOException ex) {
      this.logger.error(ex.getMessage(), ex);
    }
    return lines;
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepLambdaImp jGLI = new JavaGrepLambdaImp();
    jGLI.setRegex(args[0]);
    jGLI.setRootPath(args[1]);
    jGLI.setOutFile(args[2]);

    try {
      jGLI.process();
    } catch (IOException ex) {
      jGLI.logger.error(ex.getMessage(), ex);
    }
  }
}

