/*JAMES CURTIS ST-JEAN
 * UNIT TESTS FOR MAIN PROGRAM
 * TO ENFORCE RSS URL IS CORRECT 
 * AS WELL AS MAKE SURE FINAL TEXT IS NOT NULL
 */
import static org.junit.Assert.*;

import java.awt.Desktop;
import java.net.URL;
import java.util.LinkedList;

import org.junit.Test;

public class mainTest {

	@Test
	public void test() {
		
		String languageOfChoice = main.getUserLanguage();
		assertTrue(languageOfChoice != null);
		
		String webUrl = main.buildWebsiteLink(languageOfChoice);
		String test_url = "https://stackoverflow.com/jobs/feed?l=Bridgewater%2c+MA%2c+USA&u=Miles&d=50&tl=";
		
		assertEquals(test_url, webUrl.contains(test_url));
				
		LinkedList<String> packagedData = main.readRSSFeed(webUrl);
		String parsedRssFeed = packagedData.removeLast();
		
		assertTrue(parsedRssFeed != null);
		
		
	}
}
