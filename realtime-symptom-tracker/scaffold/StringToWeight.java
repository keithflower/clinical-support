import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class StringToWeight{

    private static boolean useKgs (String u) {
	String kgs =  "kg kgs kilo kilos";

	if (kgs.indexOf(u) != -1) {
	    System.out.println ("...using kgs");
	    return (true);
	}
	else {
	    return (false);
	}
    }

    private static boolean useLbs (String u) {
	String lbs    = "pounds lb lbs";

	if (lbs.indexOf(u) != -1) {
	    System.out.println ("...using lbs");
	    return (true);
	}
	else {
	    return (false);
	}
    }

    private static boolean useOzs (String u) {
	String ozs    = "ozs ounces";

	if (ozs.indexOf(u) != -1) {
	    System.out.println ("...using ozs");
	    return (true);
	}
	else {
	    return (false);
	}
    }

    private static boolean useGs (String u) {
	String gs    = "gs grams";

	if (gs.indexOf(u) != -1) {
	    System.out.println ("...using grams");
	    return (true);
	}
	else {
	    return (false);
	}
    }


    private static double xWeight (String w, String u) {
	double weight = 0;

	weight = Double.parseDouble(w);
	if (useKgs(u)) {
	    return (weight);
	}
	if (useOzs(u)) {
	    return (weight / 35.2739619);
	}
	if (useGs(u)) {
	    return (weight / 1000);
	}
	if (useLbs(u)) {
	    return (weight / 2.20462262);
	}
	return weight;
    }

 public static double InputToWeight (String s) { 
      // The intent of the regex is to match up to 4 tokens: two (possibly floating point) numbers, 
      // each possibly followed by a string indicating units of measure.
      Pattern p = Pattern.compile( "([0-9]*\\.?[0-9]*)( *[A-Za-z']* *)([0-9]*\\.?[0-9]*)( *[A-Za-z\"]* *)");
      Matcher m = p.matcher(s);
      boolean firstWeight  = false;
      boolean firstUnit    = false; 
      boolean secondWeight = false;
      boolean secondUnit   = false;
      double fWeight       = 0;
      double sWeight       = 0;
      double weight        = 0;

      if (m.find()) {
     // How many tokens?
	  int n = m.groupCount();
	  System.out.println (n);
	  for (int i=0; i <= n; i++) {
	      if (m.group(i).length() > 0) {
		  if (i == 1) {firstWeight = true;}
		  if (i == 2) {firstUnit   = true;}
		  if (i == 3) {secondWeight = true;}
		  if (i == 4) {secondUnit   = true;}
		  System.out.println (m.group(i));
	      }
	  }
	  if (firstWeight) {
	      if (firstUnit) {
		  fWeight = xWeight (m.group(1), m.group(2).trim());
	      }
	      else {
		  fWeight = xWeight (m.group(1), "none");
	      }
	  }
	  if (secondWeight) {
	      if (secondUnit) {
		  sWeight = xWeight (m.group(3), m.group(4).trim());
	      }
	      else {
		  if (useLbs (m.group(2))) { 
			  sWeight = xWeight (m.group(3), "oz");
		  }
		  if (useKgs (m.group(2))) {
			  sWeight = xWeight (m.group(3), "g");
		  }
	      }
	  }
      }
      return (fWeight + sWeight);
  }

  public static void main(String[] argv) {
      
      System.out.println (InputToWeight (argv[0]));
  }
}
