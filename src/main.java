
/* JAMES CURTIS ST-JEAN
 * AN XML READER PARSING THROUGH STACK OVERFLOW's RSS FEED
 * WE PARSE THE "TITLE" AND "LOCATION", ORGANIZE IT AND OUTPUT FROM 
 * URL: https://stackoverflow.com/jobs/feed?l=Bridgewater%2c+MA%2c+USA&u=Miles&d=50
 * AS A WINDOW.
 */

import java.awt.Desktop;
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Scanner;

import java.util.*;

public class main {

    public static void main(String[] args) {    	
    	String languageOfChoice = getUserLanguage();
    	String webUrl = buildWebsiteLink(languageOfChoice);
    	LinkedList<String> packagedData = readRSSFeed(webUrl);
    	String parsedRssFeed = packagedData.removeLast();
    	System.out.println(parsedRssFeed);   	
    	displayJobLocations(packagedData);   	
    }
    
    public static String getUserLanguage()
    {
    	System.out.println("Please enter one language you are familiar with?  Here is a list of inputs for you"
    			+ "C++ , C# ,  Python , Java , JavaScript, html, php, jquery, c, sql, objective-c\n");
    	System.out.println("Be sure to IGNORE spaces.");
    	
    	Scanner inputStream = new Scanner(System.in);  
    	String userInput = inputStream.next().toLowerCase();    	
    	inputStream.close();
    	
    	return userInput;
    }
    
    public static String buildWebsiteLink(String languageCriteria)
    {   
    	String criteriaModified;
    	String csharp = "c#";
    	String cplusplus = "c++";
    	
    	System.out.println("Looking for jobs nearby that fit your language criteria , " + languageCriteria.toUpperCase() + " ...\n");
    	
    	//special character manipulation for web url
    	if(languageCriteria.equals(cplusplus))
    	{
    		criteriaModified = "c%2B%2B";
    	}
    	else if (languageCriteria.equals(csharp))
    		criteriaModified = "c%23";
    	else 
    		criteriaModified = languageCriteria;
    	    	
    	String url = "https://stackoverflow.com/jobs/feed?l=Bridgewater%2c+MA%2c+USA&u=Miles&d=50&tl=";
    	url += criteriaModified;
    	return url;
    }  
    
    public static LinkedList<String> readRSSFeed(String urlAddress){
        try{
            URL rssUrl = new URL (urlAddress);
            BufferedReader XML = new BufferedReader(new InputStreamReader(rssUrl.openStream()));
            String sourceCode = "";
            String line;

            LinkedList<String> locationList = new LinkedList<String>();
      
            while ((line = XML.readLine()) != null) {

                int titleStartIndex = 0,
                	titleEndIndex = 0,
                	linkStartIndex = 0,
                	linkEndIndex = 0,
                	locationStartIndex = 0,
                	locationEndIndex = 0,
                	pubDateStartIndex = 0,
                	pubDateEndIndex = 0;
                	
                //we need this to align the dates with the proper elements
                int publishDateStallIndex = 0;
                
                int listOrderVal = 1;
                
                while (titleStartIndex >= 0) {
                    titleStartIndex = line.indexOf("<title>", titleEndIndex);
                	linkStartIndex = line.indexOf("<link>", linkEndIndex);
                	pubDateStartIndex = line.indexOf("<pubDate>", pubDateEndIndex);
                	locationStartIndex = line.indexOf("<location", locationEndIndex);
  

                	
                    if (titleStartIndex >= 0) {
                        //allows proper formatting
                        if(publishDateStallIndex >= 2)
                    	{
                    		sourceCode += Integer.toString(listOrderVal) + ". ";
                    		listOrderVal++;
                    	}
                        
                        titleEndIndex = line.indexOf("</title>", titleStartIndex);
                        sourceCode +=  line.substring(titleStartIndex + "<title>".length(), titleEndIndex) + "\n";
                                            
                    }

                    
                    if (locationStartIndex >= 0) {
                    	
                    	locationEndIndex = line.indexOf("</location>", locationStartIndex);
                        locationList.add(line.substring(locationStartIndex + "<location".length(), locationEndIndex) + "\n");
                    	}
                    
                    if (linkStartIndex >= 0) {
                    	linkEndIndex = line.indexOf("</link>", linkStartIndex);
                        sourceCode += line.substring(linkStartIndex + "<link>".length(), linkEndIndex) + "\n";
                    	
                        //allows proper formatting of breakpoints
                        if(publishDateStallIndex < 2)
                    	{
                    		sourceCode += "\n";
                    	}
                    }
                    
                   
                    
                   
                    if(publishDateStallIndex >= 2)
                    {
                    	//Z value is parsed out by including it
                    	pubDateEndIndex = line.indexOf("Z</pubDate>", pubDateStartIndex);
                        sourceCode += line.substring(pubDateStartIndex + "<pubDate>".length(), pubDateEndIndex) + "\n\n";
                    
                    
                    }
                    
                    publishDateStallIndex++;
                }
               
            }
            XML.close();
            
            locationList = formatList(locationList);
            locationList = removeDuplicates(locationList);
            locationList.add(sourceCode);            
            return locationList;
            
        } catch (MalformedURLException ue){
            System.out.println("Malformed URL");
        } catch (IOException ioe){
            System.out.println("Something went wrong reading the contents");
        }
        return null;
    }
    
    public static LinkedList<String> formatList(LinkedList<String> list)
    {
    	int size = list.size();
    	String listElement;
    	
    	for(int i = 0; i < size; i++)
    	{
    		listElement = list.get(i);    		
    		String[] splicedElement = listElement.split(">");
    		list.set(i, splicedElement[1]);
    	}
    	
    	return list;
    }
    
    public static LinkedList<String> removeDuplicates(LinkedList<String> list)
    {
    	int size = list.size();
    	
    	LinkedList<String> newList = new LinkedList<String>();
    	newList.addLast(list.get(0));
    	
    	for(int i = 1; i < size; i++)
    	{   		
    		if(newList.contains(list.get(i)) == false)
    		{
    			newList.add(list.get(i));
    		}
    	}
    	return newList;
    }
    
    public static void displayJobLocations(LinkedList<String> locationList)
    {
    	int size = locationList.size();
    	
    	for(int i = 0; i < size; i++)
    	{
    		String cityAndState = locationList.get(i);
    		cityAndState = cityAndState.replaceAll(" ", "");
    		cityAndState.trim();
    		String url = "http://www.google.com/maps/place/" + cityAndState;   	
        	
    	    try {
    	        Desktop.getDesktop().browse(new URL(url).toURI());
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
        	
    	}	
    	
    	System.out.println("Please check your browser to see the different locations of your jobs.");
    }
}