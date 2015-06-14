# Clubbr
Social Location Based Network for activities 

Project for Software Development and Projectmanagemant(2015) at University of Innsbruck


App Idea:
------------
Simple social activity application for Android devices. Join Events, meet up with your friends and plan your daily activities.



How to use the app
------------
After starting the app, you can either register or, if you have already created a user, just login.
On the next screen, you can select the time, when the app should log you out, if no time is selected, after 2 hours it logs you out automatically.
Now you are on the main screen, starting with a view on a map.

Map features the location of yourself (marked as a red arrow), the location of your friends (blue symbol with an avatar in the middle) and the location
of events (grey with star). 

Sometimes it takes a few seconds to update information, but in this case switching to other tabs may help or you can refresh manually from the 
settings located in the top right corner.

The settings also include options to add friends and add events.

Beside of the map tab there are other tabs, one shows your friends and the other one the events.

Friends Tab:
.    - Lists your friends, online friends are shown first, then your offline friends
.    - Online friends are sorted by distance, nearest on top
.    - on clicking on friend, you can either chat with her/him, show her/his location or delete her/him from friends
.    - this tab also features an add friend button

Event Tab:
.   - Events are shown in list, sorted by distance again
.   - features the name of the event, a short description and the number of attendees
.   - you can join events and show the location on the map
.   - again, there is an add event button



Build Guide
------------

App:
With AndroidStudio:
Open Android Studio, click checkout from Version Control, login and clone this git Repository. Wait a few seconds for gradle to sync and then you are ready to go.

With Gradle:
goto SMRTMS/app/ then execute gradle build
your apk file will be in SMRTMS/app/build/outputs/apk/app-release-unsigned.apk


Server:
you need to have a running SQL server, then you have to execute the sql script in
SMRTMS_Server/db_script.sql then goto SMRTMS_Server/src/server and run gradle fat Jar
. You will now find a jar file in the dir repo/ which you can execute via java -jar SMRTMS_Server_Jar.jar



