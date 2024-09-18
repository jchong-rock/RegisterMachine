import java.util.List;

public class Computer {
  public static void main(String[] args) {

    if (args.length == 0) {
      System.out.println("Need a number\n");
      System.exit(256);
    }
    RegisterDecoder decoder = new RegisterDecoder();
    Long n = Long.decode(args[0]);
    int[] ran = decoder.run(n);
    int r = 0;
    System.out.println("Final state of registers:\n");
    for (int i : ran) {
      System.out.println("R (" + r + "): " + i);
      r++;
    }

    System.out.println("\nExecuted the following instructions:\n");
    List<String> decoded = decoder.decode(n);
    int l = 0;
    for (String s : decoded) {
      System.out.println("L (" + l + "): " + s);
      l++;
    }
  }
}
