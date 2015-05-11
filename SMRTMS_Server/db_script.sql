DROP database IF EXISTS SMRTMS;
CREATE database IF NOT EXISTS SMRTMS;
USE SMRTMS;

CREATE TABLE IF NOT EXISTS User 
(	ID int unique auto_increment,
	Username char(50),
  	Email char(50),
    	Password char(20),
	Position int, 
	Avatar char(15)
);
    
CREATE TABLE IF NOT EXISTS User_Friends
(	Friender_ID int,
	Friendee_ID int,
    Tracking_Flag bool
);

CREATE TABLE IF NOT EXISTS `Event`
(
	ID int unique auto_increment,
    `Name` char(50),
    Description text,
    Position int,
    Attendees int
);

CREATE TABLE IF NOT EXISTS Event_Attendees
(
	User_ID int,
    Event_ID int
);

INSERT INTO User
(Username, Email, `Password`, Position, Avatar) VALUES
("Hubert", "h@gmail.com", "123456", 3189, "f34tg45gh34.png");

INSERT INTO Event_Attendees (User_ID, Event_ID) VALUES (1, 2);
