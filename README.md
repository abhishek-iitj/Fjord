# Fjord
IEM International Smart Space Apps Challenge 2018

## Abstract: 

We are solving [Problem Statement 4](http://iedc.iemecell.com/). 

Target Device: Android Platform

APIs being used: 
- [AQICN](https://aqicn.org/api/)
- [Breezometer](https://breezometer.com/)
- [Google Maps API](https://developers.google.com/maps/documentation/android-sdk/intro)

## Final Submission
The app can be downloaded from [here](https://drive.google.com/open?id=1v0VdeCLMAaVC7Q988B3UWPa1mME6Tq7C)

### Video Demo 
This [video](https://www.youtube.com/watch?v=HVMM1gi6uO8&t=26s) shows how you can install and use the app. You need to give it the location permissions by yourself(shown in video). 

At this stage it is doing the following this: 
- Detects the location(latitude/longitude) of the user via Cell Network and/or GPS
- Passes the location to AQICN API
- Gathers the JSON data from AQICN API for the nearest station available
- Shows the AQI and concentration of major air pollutants 

### A rough demo of the workflow
![alt text](https://github.com/abhishek-iitj/Fjord/blob/master/iem_demo.jpg)


