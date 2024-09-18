import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegisterDecoder {

  public static final int DEFAULT_REGISTERS = 10;
  public static final int DEFAULT_START = 0;

  public int[] runBig(List<Integer> val) {
    return runSave(new int[DEFAULT_REGISTERS], DEFAULT_START, getLabelsBig(val));
  }

  public int[] run(long x) {
    return runSave(new int[DEFAULT_REGISTERS], DEFAULT_START, getLabelsNormal(x));
  }

  private List<Triple> getLabelsNormal(long x) {
    List<Triple> labels = new ArrayList<>();
    int divisions = 0;
    while (x > 0) {
      if (x % 2 == 1) {
        labels.add(interpret(divisions));
        x >>= 1;
      }
      divisions++;
      x /= 2;
    }
    return labels;
  }

  private List<Triple> getLabelsBig(List<Integer> bits) {
    List<Triple> labels = new ArrayList<>();
    labels.add(interpret(bits.get(bits.size() - 1)));
    Collections.sort(bits);
    for (int i = 0; i < (bits.size() - 2); i++)
      labels.add(interpret(bits.get(i + 1) - bits.get(i) - 1));
    return labels;
  }

  // in case we want to start from a particular configuration
  public int[] runSave(int registers[], int start, List<Triple> labels) {

    int current = start;
    boolean done = false;
    while (!done) {
      if (current >= labels.size()) {
        break;
      }
      Triple t = labels.get(current);
      Integer r = t.getA();
      if (r == null) {
        done = true;
      }
      else if (r >= registers.length) {
        System.out.println("Not enough registers\n");
        System.exit(-1);
      } else {
        Integer l0 = t.getB();
        Integer l1 = t.getC();
        if (l1 == null) {
          registers[r]++;
          current = l0;
        }
        else {
          if (registers[r] > 0) {
            registers[r]--;
            current = l0;
          } else {
            current = l1;
          }
        }
      }
    }
    return registers;
  }

  public List<String> decodeBig(List<Integer> bits) {
    List<String> bs = new ArrayList<>();
    bs.add(body(bits.get(bits.size() - 1)));
    Collections.sort(bits);
    for (int i = 0; i < bits.size() - 2; i++)
      bs.add(body(bits.get(i + 1) - bits.get(i) - 1));
    return bs;
  }

  public List<String> decode(long x) {
    List<String> bs = new ArrayList<>();
    int divisions = 0;
    while (x > 0) {
      if (x % 2 == 1) {
        bs.add(body(divisions));
        x >>= 1;
      }
      divisions++;
      x /= 2;
    }
    return bs;
  }

  private Triple interpret(int n) {
    if (n == 0) {
      return new Triple(null, null, null);
    }
    int x = 0;
    while (n % 2 == 0) {
      x++;
      n /= 2;
    }
    int y = n >> 1;
    if (x % 2 == 1) {
      int z = 0;
      while (y % 2 == 1) {
        z++;
        y >>= 1;
        if (y == 0) {
          return new Triple(null, null, null);
        }
      }
      y /= 2;
      return new Triple(x >> 2, z, y);


    }
    return new Triple(x / 2, y, null);
  }

  private String body(int n) {
    Triple t = interpret(n);
    Integer x = t.getA();
    if (x == null)
      return "HALT";

    Integer y = t.getB();
    Integer z = t.getC();

    if (z == null)
      return ("R+ (" + x + ") -> L (" + y + ")");
    return ("R- (" + x + ") -> L (" + y + "), L (" + z + ")");
  }

  private class Triple {

    private final Integer a;
    private final Integer b;
    private final Integer c;

    Triple(Integer a, Integer b, Integer c) {
      this.a = a;
      this.b = b;
      this.c = c;
    }

    Integer getA() {
      return a;
    }
    Integer getB() {
      return b;
    }
    Integer getC() {
      return c;
    }
  }
}
