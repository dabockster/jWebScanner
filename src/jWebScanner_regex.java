/*
  Steven Bock
  October 1st, 2014
  CSCE 343
  Homework 3

  BUGS:
  For some reason, it detects the favicon as an absolute url.

  SOURCES CITED:
  http://stackoverflow.com/questions/3341516/how-we-can-download-a-html-page-using-java
  http://docs.oracle.com/javase/tutorial/essential/exceptions/finally.html
  http://www.coffeecup.com/help/articles/absolute-vs-relative-pathslinks/
  http://docs.oracle.com/javase/tutorial/essential/regex/test_harness.html
  http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
  http://docs.oracle.com/javase/8/docs/api/java/util/regex/Matcher.html
  http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html
  http://stackoverflow.com/questions/5695623/remove-characters-from-a-string-in-java
  http://stackoverflow.com/questions/5375028/extraction-of-html-tags-using-java
  http://stackoverflow.com/questions/8307839/creating-java-regex-to-get-href-link
*/

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.regex.*;

public class jWebScanner_regex{

  public static void main(String args[]) throws IOException{
    //Line placeholder
    String line = null;

    //URL holder
    URL dlurl = null;

	//Scanner for user input (needed for later)
	Scanner keyboard = new Scanner(System.in);

    //Read the input data
    BufferedReader input = null;

    //grab data
    try{
  	  //ask the user for the URL
  	  System.out.print("Enter a URL with http prefix: ");
  	  String url = (keyboard.nextLine());

      dlurl = new URL(url);

      input = new BufferedReader(new InputStreamReader(dlurl.openStream()));

      //line counter
      int count = 0;

  	  //regex matching
  	  Pattern link_pattern = null;
  	  Pattern url_pattern = null;
  	  Pattern img_pattern = null;
  	  Matcher link_matcher = null;
  	  Matcher url_matcher = null;
  	  Matcher img_matcher = null;

      while((line = input.readLine()) != null){
  		count++;

  		/*
  		compile <a href> regex pattern to search for links
  		(.*?) represents the URL in the tag
  		*/
  		link_pattern = Pattern.compile("href=\"(.*?)\"");

  		//define what we need to scan
  		link_matcher = link_pattern.matcher(line);

  		//if pattern match detected
  		if(link_matcher.find()){
  			//print out counter as line number
  			System.out.println("Line number " + count);

  			//Print URL
  			System.out.println("URL: " + link_matcher.group(1));

  			//compile absolute path search
  			url_pattern = Pattern.compile("(http)|(https)|(ftp)");
  			url_matcher = url_pattern.matcher(line);

  			img_pattern = Pattern.compile("<img");
  			img_matcher = img_pattern.matcher(line);

  			//if http/https/ftp is detected and the link isn't an img alt link
  			if(url_matcher.find() && ! img_matcher.find()){
  				System.out.println("Absolute path detected");
  			}
  			//if the link is relative
  			else{
  				System.out.println("Relative path detected");
  			}

  			//skip a line
  			System.out.println();

		    }
      }
    }
    //close the input buffer if still open
    finally{
      if (input != null){
        input.close();
      }
    }
  }
}
