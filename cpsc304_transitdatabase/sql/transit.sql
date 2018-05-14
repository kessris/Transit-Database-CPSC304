DROP TABLE Train;
DROP TABLE Bus;
DROP TABLE Vehicle;
DROP TABLE RouteHasStop;
DROP TABLE Route;
DROP TABLE StopHas;
DROP TABLE Address;
DROP TABLE Tap;
DROP TABLE FareCardHas;
DROP TABLE Users;

CREATE TABLE Users(
	UserID int,
	Name varchar(20),
	Age int,
	PRIMARY KEY (UserId));
	
GRANT SELECT on Users to public;
			
CREATE TABLE FareCardHas(
	CardNumber int,
	Balance float CHECK (Balance>=0),
	UserID int, 
	PRIMARY KEY (CardNumber),
	FOREIGN KEY (UserID) references Users(UserID) ON DELETE SET NULL);
	
GRANT SELECT on FareCardHas to public;
	
CREATE TABLE Tap(
	TimeTap date,
	CardNumber int,
	StopNumber int,
	PRIMARY KEY (TimeTap, CardNumber),
	FOREIGN KEY (CardNumber) references FareCardHas(CardNumber));
	
GRANT SELECT on Tap to public;
		
CREATE TABLE Address (
	City varchar(10),
	StreetName varchar(10),
	StreetNumber int,
	TransitZone int, 
	PRIMARY KEY(City, StreetName, StreetNumber));
	
GRANT SELECT on Address to public;
		
CREATE TABLE StopHas (
	StopNumber int,
	City varchar(10) NOT NULL,
	StreetName varchar(10) NOT NULL,
	StreetNumber int NOT NULL,
	PRIMARY KEY (StopNumber),
	FOREIGN KEY (City, StreetName, StreetNumber) references Address(City, StreetName, StreetNumber));
	
GRANT SELECT on StopHas to public;
						
				
CREATE TABLE Route(
	RouteNumber int,
	RouteName varchar(20),
	PRIMARY KEY(RouteNumber));
	
GRANT SELECT on Route to public;
			
CREATE TABLE RouteHasStop (
	RouteNumber int NOT NULL,
	StopNumber int NOT NULL,
	orderNumber int,
	PRIMARY KEY(RouteNumber, StopNumber),
	FOREIGN KEY(RouteNumber) references Route(RouteNumber) ON DELETE CASCADE,
	FOREIGN KEY(StopNumber) references StopHas(StopNumber) ON DELETE CASCADE);
	
GRANT SELECT on RouteHasStop to public;

CREATE TABLE Vehicle (
	VehicleNumber int,
	YearNumber int, 
	RouteNumber int UNIQUE,
	PRIMARY KEY(VehicleNumber),
	FOREIGN KEY(RouteNumber) references Route(RouteNumber));
	
GRANT SELECT on Vehicle to public;

CREATE TABLE Bus (
	VehicleNumber int,
	LicenseNumber varchar(20) UNIQUE,
	BusType varchar(20),
	PRIMARY KEY(VehicleNumber),
	FOREIGN KEY(VehicleNumber) references Vehicle(VehicleNumber) ON DELETE CASCADE);
	
GRANT SELECT on Bus to public;
			
CREATE TABLE Train (
	VehicleNumber int,
	TrainType varchar(20),
	PRIMARY KEY(VehicleNumber),
	FOREIGN KEY(VehicleNumber) references Vehicle(VehicleNumber) ON DELETE CASCADE);

GRANT SELECT on Train to public;