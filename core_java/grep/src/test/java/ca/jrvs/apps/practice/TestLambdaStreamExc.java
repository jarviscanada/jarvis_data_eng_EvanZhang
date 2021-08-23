package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

public class TestLambdaStreamExc {
  LambdaStreamExcImp lse;

  @Before
  public void setUp() {
    lse = new LambdaStreamExcImp();
    BasicConfigurator.configure();
  }

  @Test
  public void testStrStream() {
    String[] args = {"alpha", "beta", "gamma"};
    List<String> filterTest = Arrays.asList("alpha", "beta");
    List<String> upperTest = Arrays.asList("ALPHA", "BETA", "GAMMA");

    Stream<String> strStream = lse.createStrStream(args);
    List<String> filterResult = lse.toList(lse.filter(strStream, "m"));
    List<String> upperResult = lse.toList(lse.toUpperCase(args));

    assert(filterTest.equals(filterResult));
    assert(upperTest.equals(upperResult));
  }

  @Test
  public void testIntStream() {
    int[] args = {2,3,4,5};
    double[] squareRootTest = {Math.sqrt(2), Math.sqrt(3), Math.sqrt(4), Math.sqrt(5)};
    List<Integer> getOddTest = Arrays.asList(3, 5);

    IntStream arrayIntStream = lse.createIntStream(args);
    IntStream rangeIntStream = lse.createIntStream(2, 5);

    DoubleStream squareRootResult = lse.squareRootIntStream(arrayIntStream);
    List<Integer> getOddResult = lse.toList(lse.getOdd(rangeIntStream));

    assert(Arrays.equals(squareRootTest, squareRootResult.toArray()));
    assert(getOddTest.equals(getOddResult));
  }

  @Test
  public void testPrint() {
    Consumer<String> lambdaPrinter = lse.getLambdaPrinter("start: ", " :end");
    lambdaPrinter.accept("bee");
    lambdaPrinter.accept("before");
    System.out.println();

    String[] messages = {"b", "beta", "best"};
    lse.printMessages(messages, lambdaPrinter);
    System.out.println();

    lse.printOdd(lse.createIntStream(1,6), lambdaPrinter);
    System.out.println();
  }

  @Test
  public void testFlatNestedInt() {
    List<Integer> nestedInt = Arrays.asList(1, 2, 3, 4, 5);

    Stream<Integer> nestedIntResult = lse.flatNestedInt(Stream.of(nestedInt));
    nestedIntResult.forEach(System.out::println);
    System.out.println();
  }
}
