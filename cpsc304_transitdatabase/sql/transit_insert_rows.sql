alter session set NLS_DATE_FORMAT='DD-MON-YYYY';
commit;

insert into Users values
( 001, 'Hee Su Kim', 10);

insert into Users values
( 002, 'Jessica Que', 30);

insert into Users values
( 003, 'Seongjun Lee', 50);

insert into Users values
( 004, 'Christopher Tong', 70);

insert into FareCardHas values
( 111111, 5.50, 001);

insert into FareCardHas values
( 222222, 55.55, 002);

insert into FareCardHas values
( 333333, 130.00, 003 );

insert into FareCardHas values
( 444444, 8.00, 004 );

insert into FareCardHas values
( 555555, 90.00, 003 );

insert into FareCardHas values
( 666666, 89.10, 004 );

insert into FareCardHas values
( 777777, 5.00, 004 );

insert into Tap values
( '02-DEC-1997', 111111, 123456 );

insert into Tap values
( '03-DEC-1997', 111111, 654321 );

insert into Tap values
( '04-JAN-2005', 222222, 123456 );

insert into Tap values
( '02-FEB-2018', 333333, 123456 );

insert into Tap values
( '30-OCT-2017', 555555, 987654 );

insert into Tap values
( '02-DEC-2010', 666666, 456789 );

insert into Tap values
( '14-MAR-2008', 777777, 654321 );

insert into Tap values
( '14-MAR-2009', 777777, 123456 );

insert into Tap values
( '02-JAN-2018', 555555, 654321 );

insert into Tap values
( '31-DEC-1997', 111111, 123456 );

insert into Tap values
( '05-DEC-1998', 111111, 123456 );

insert into Tap values
( '02-DEC-1999', 111111, 456789 );

insert into Address values
( 'Vancouver', 'Apple Dr.', 101, 1);

insert into Address values
( 'North Van', 'Banana St.', 202, 2);

insert into Address values
( 'North Van', 'Grape St.', 303, 2);

insert into Address values
( 'North Van', 'Grape St.', 305, 2);

insert into Address values
( 'New West', 'Cherry St.', 404, 3);

insert into Address values
( 'Vancouver', 'Melon Dr.', 505, 1);

insert into StopHas values
( 123456, 'Vancouver', 'Apple Dr.', 101 );

insert into StopHas values
( 654321, 'North Van', 'Banana St.', 202 );

insert into StopHas values
( 987654, 'New West', 'Cherry St.', 404 );

insert into StopHas values
( 456789, 'Vancouver', 'Melon Dr.', 505 );

insert into Route values
( 99, 'Express To UBC' );

insert into Route values
( 49, 'Metrotown' );

insert into Route values
( 10, 'Downtown' );

insert into Route values
( 11, 'Granville Island' );

insert into RouteHasStop values
( 99, 123456, 1 );

insert into RouteHasStop values
( 99, 654321, 2 );

insert into RouteHasStop values
( 99, 987654, 3 );

insert into RouteHasStop values
( 49, 456789, 1 );

insert into RouteHasStop values
( 49, 123456, 2 );

insert into RouteHasStop values
( 10, 123456, 1 );

insert into RouteHasStop values
( 10, 654321, 2 );

insert into RouteHasStop values
( 11, 456789, 1 );

insert into RouteHasStop values
( 11, 987654, 2 );

insert into Vehicle values
( 01010, 1996, 99 );

insert into Vehicle values
( 02020, 2015, 49 );

insert into Vehicle values
( 03030, 2010, 10 );

insert into Vehicle values
( 04040, 2000, 11 );

insert into Bus values
( 01010, 'ABC123', 'electric' );

insert into Bus values
( 02020, 'DEF123', 'dual-mode' );

insert into Bus values
( 03030, 'GHI123', 'motor' );

insert into Train values
( 04040, 'high-speed' );

commit work;
