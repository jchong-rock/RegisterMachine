import java.util.List;

public class Analyser {
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Need a number\n");
      System.exit(256);
    }
    RegisterDecoder decoder = new RegisterDecoder();
    Integer n = Integer.decode(args[0]);
    List<String> decoded = decoder.decode(n);
    int l = 0;
    for (String s : decoded) {
      System.out.println("L (" + l + "): " + s);
      l++;
    }
  }
}
