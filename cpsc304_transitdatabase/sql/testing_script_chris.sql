CREATE VIEW RouteStopAddress(RouteNumber, TransitZone) AS SELECT rhs.RouteNumber, a.TransitZone FROM RouteHasStop rhs, StopHas sh, Address a WHERE rhs.StopNumber = sh.StopNumber AND sh.City = a.City AND sh.StreetName = a.StreetName AND sh.StreetNumber = a.StreetNumber;


DELETE FROM Address WHERE city = 'New West';


INSERT into Address Values 
('City', 'Street', 808, 3);

INSERT INTO StopHas Values 
(999999, 'City', 'Street', 808);

INSERT INTO RouteHasStop Values
(10, 999999, 3);

SELECT DISTINCT RouteNumber 
FROM RouteHasStop rhs 
WHERE NOT EXISTS (SELECT a.TransitZone 
FROM Address a 
WHERE NOT EXISTS (SELECT rsa.RouteNumber 
FROM RouteStopAddress rsa 
WHERE rhs.RouteNumber = rsa.RouteNumber 
AND rsa.TransitZone = a.TransitZone));


DROP VIEW RouteStopAddress;
