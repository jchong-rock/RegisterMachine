import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BigComputer {

  /*
    USAGE:
    arg[0] = power of 2 by which to multiply
    arg[n] = powers of 2 to sum

    The program number is represented as (2^x) * (2^a + 2^b + 2^c + ...)
    where arg[0] = x
          arg[1] = a
       (  arg[2] = b
          arg[3] = c
          etc.  )
  */

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Need a number\n");
      System.exit(256);
    }
    RegisterDecoder decoder = new RegisterDecoder();
    List<Integer> bits = Arrays.asList(args).stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    int[] ran = decoder.runBig(bits);
    int r = 0;
    System.out.println("Final state of registers:\n");
    for (int i : ran) {
      System.out.println("R (" + r + "): " + i);
      r++;
    }

    System.out.println("\nExecuted the following instructions:\n");
    List<String> decoded = decoder.decodeBig(bits);
    int l = 0;
    for (String s : decoded) {
      System.out.println("L (" + l + "): " + s);
      l++;
    }
  }
}
