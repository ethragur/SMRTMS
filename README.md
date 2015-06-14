# Clubbr
Social Location Based Network for activities 

Project for Software Development and Projectmanagemant(2015) at University of Innsbruck


App Idea:
------------
Simple social activity application for Android devices. Join Events, meet up with your friends and plan your daily activities.


Build Guide
-------------

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



