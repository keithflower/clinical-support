import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class StringToHeight{

    private static boolean useMeters (String u) {
	String meters =  "meters metres mtrs";

	if (meters.indexOf(u) != -1) {
	    System.out.println ("...using meters");
	    return (true);
	}
	else {
	    return (false);
	}
    }

    private static boolean useCms (String u) {
	String cms    = "cm cms centimeters centimetres";

	if (cms.indexOf(u) != -1) {
	    System.out.println ("...using cms");
	    return (true);
	}
	else {
	    return (false);
	}
    }


    private static boolean useFeet (String u) {
	String feet   = "'ft foot feet";

	if (feet.indexOf(u) != -1) {
	    System.out.println ("...using feet");
	    return (true);
	}
	else {
	    return (false);
	}
    }

    private static boolean useInches (String u) {
	String inch   = "\"ins inch inches";

	if (inch.indexOf(u) != -1) {
	    System.out.println ("...using inches");
	    return (true);
	}
	else {
	    return (false);
	}
    }


    private static double xHeight (String h, String u) {
	double height = 0;

	height = Double.parseDouble(h);
	if (useMeters(u)) {
	    return (height);
	}
	if (useCms(u)) {
	    return (height / 100.0);
	}
	if (useFeet(u)) {
	    return (height * 0.3048);
	}
	if (useInches(u)) {
	    return (height / 12 * 0.3048);
	}
	return height;
    }

 public static double InputToHeight (String s) { 
      // The intent of the regex is to match up to 4 tokens: two (possibly floating point) numbers, 
      // each possibly followed by a string indicating units of measure.
      Pattern p = Pattern.compile( "([0-9]*\\.?[0-9]*)( *[A-Za-z']* *)([0-9]*\\.?[0-9]*)( *[A-Za-z\"]* *)");
      Matcher m = p.matcher(s);
      boolean firstHeight  = false;
      boolean firstUnit    = false; 
      boolean secondHeight = false;
      boolean secondUnit   = false;
      double fHeight       = 0;
      double sHeight       = 0;
      double height        = 0;

      if (m.find()) {
     // How many tokens?
	  int n = m.groupCount();
	  System.out.println (n);
	  for (int i=0; i <= n; i++) {
	      if (m.group(i).length() > 0) {
		  if (i == 1) {firstHeight = true;}
		  if (i == 2) {firstUnit   = true;}
		  if (i == 3) {secondHeight = true;}
		  if (i == 4) {secondUnit   = true;}
		  System.out.println (m.group(i));
	      }
	  }
	  if (firstHeight) {
	      if (firstUnit) {
		  fHeight = xHeight (m.group(1), m.group(2).trim());
	      }
	      else {
		  fHeight = xHeight (m.group(1), "none");
	      }
	  }
	  if (secondHeight) {
	      if (secondUnit) {
		  sHeight = xHeight (m.group(3), m.group(4).trim());
	      }
	      else {
		  if (useFeet (m.group(2))) { 
			  sHeight = xHeight (m.group(3), "in");
		  }
		  if (useMeters (m.group(2))) {
			  sHeight = xHeight (m.group(3), "cm");
		  }
	      }
	  }
      }
      return (fHeight + sHeight);
  }

  public static void main(String[] argv) {
      
      System.out.println (InputToHeight (argv[0]));
  }
}
