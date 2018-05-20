# RSS-Job-Finder
A Stack Overflow RSS feed scraper that also turns it into filtered jobs within a specified area and displays the cities in which they are located.

This Project is build in 3 parts. First I built an RSS feed scraper using some ideas from the internet to pull and organize RSS Feed information into lists for use. Then I made it display that information as text, and the last thing I did was make the browser open each city where the jobs are located in google maps.

The RSS feed url can be changed in code or modified to be manual, but code is tailored specifically for the (2018) Stack Overflow RSS feed.

FLOW OF THE PROGRAM :

1. Read in user language that they are looking for jobs in. (It bases it off of Stack Overflows job listings)
2. Store input into a linked list of information on each job and location.
3. Format the list, removing duplicates.
4. At the end of the list add one extra link for the locations
5. Opens browser windows for each unique city location of the jobs.

CONSTRAINTS : 

1. I had to read the information using the RSS feed so i couldn't pull from HTML. I was strictly working with XML
2. I had to display the map information from the locations. Unfortunately Minigeo at the time (2018) didn't work and
   other map software had too much complexity in the time frame that I had.
   
SCREENSHOTS : 
   Show the unit test and the behavior of the code.
   IDE used is "Eclipse Neon"
