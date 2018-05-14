import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * 1) source jdbc.env	
 * 2) javac JavaConnection.java
 * 3) java JavaConnection
 */

// import oracle.jdbc.driver.DatabaseError;

public class JavaConnection {

	public static Connection connection;

	public static void main(String[] args) {
		//JavaConnection jc = new JavaConnection();
		connection = createConnection();
		//while(!getMenuSelection(connection));
		JavaUI ui = new JavaUI();
		//closeConnection(connection);
	}
	
	public static Connection createConnection() {
		System.out.println("Starting Java Connection");
		
		Connection connection = null;
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug","ora_k0b0b","a36134147");
			// connection = DriverManager.getConnection("jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug","ora_n1y0b","a34016155");
			System.out.println("Connection established");
			
			//jdbc:oracle:thin@localhost:1522:ug
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ug","ora_n1y0b","a34016155");
		} catch (SQLRecoverableException e){
			System.out.println("SQL Recoverable Exception");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		} 
		return connection;
	}
	

	public static boolean getMenuSelection() {
		/*
		boolean isDone = false;
		printMenu();
		
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    switch (input) {
	        case 1: 
	        	fetchUser(connection);
	            break;
	        case 2: 
	        	insertUser(connection);
	        	break;
	        case 3: 
	        	fetchFareCard(connection);
	        	break;
	        case 4: 
	        	insertFareCard(connection);
	        	break;
	        case 5: 
	        	fetchTap(connection);
	        	break;
	        case 6: 
	        	insertTap(connection);
	        	break;
	        case 7: 
	        	vehicleInRoute(connection);
	        	break;
	        case 8: 
	        	stopInRoute(connection);
	        	break;
	        case 9: 
	        	closeStops(connection);
	        	break;
	        case 10: 
	        	routeOfVehicle(connection);
	        	break;
	        case 11: 
	        	modifyUserInfo(connection);
	        	break;
	        case 12: 
	        	userFareCard(connection);
	        	break;
			case 13:
				addBalance(connection);
				break;
			case 14:
				deleteTransit(connection);
				break;
			case 15:
				deleteStop(connection);
				break;
			case 16:
				deleteUser(connection);
				break;
			case 17:
				insertTransit(connection);
				break;
			case 18:
				insertStop(connection);
				break;
			case 19:
				exchangeBusRoute(connection);
				break;
			case 20:
				modifyStop(connection);
				break;
			case 21:
				fetchVehicle(connection);
				break;
			case 22:
				fetchAllUser(connection);
				break;
			case 23:
				insertRoute(connection);
				break;
			case 24:
				fetchStops(connection);
				break;
			case 25:
				stopNotVisited(connection);
				break;
			case 26:
				userAtStopAtTime(connection);
				break;
			case 27:
				userOnVehicle(connection);
				break;
			case 28:
				deleteRouteHasStop(connection);
				break;
	        case 100:
	        	isDone = true;
	        	break;
			case 101:
				numberStopsOnRoute(connection);
				break;
			case 102:
				selectAgeRange(connection);
				break;
			case 103:
				getAddressesRoutesPassesBy(connection);
				break;
			case 104:
				richestInAgeRanges(connection);
	        default: 
	        	System.out.println("Invalid input...");
	        	break;
	    }
	    */
		boolean isDone = true;
	    return isDone;
	}
	
	public static ArrayList<String> returnAllStops(){

		ArrayList<String> outputList = new ArrayList<String>();
		String query =
				"SELECT * "
				+ "FROM StopHas ";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String output = "Stop Number: " + resultSet.getInt("StopNumber") + " Address: " + resultSet.getInt("StreetNumber") + " " + resultSet.getString("StreetName") + ", " + resultSet.getString("City");
				outputList.add(output);
			}
			return outputList;
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			String output = "ERROR: Invalid input";
			ArrayList<String> outputListBad = new ArrayList<String>();
			outputListBad.add(output);
			return outputListBad;
		} finally {
			System.out.println("\n");
		}
	}


	public static String deleteRouteHasStop(Connection connection, int route, int stop) {
		System.out.println("\n");

		System.out.println("Input Route Number: ");
		//Scanner sc = new Scanner(System.in);
		//int route = sc.nextInt();
		System.out.println("Input Stop Number: ");
		//Scanner s = new Scanner(System.in);
		//int stop = s.nextInt();

		String deleteQuery =
				"DELETE FROM RouteHasStop WHERE RouteNumber = "+route+" AND StopNumber ="+stop;

		Statement statement = null;
		try {
			//statement = connection.prepareStatement(fetchQuery);
			statement = connection.createStatement();
			int rowCount = statement.executeUpdate(deleteQuery);
			if (rowCount==0) {
				System.out.println("Such Stop does not exist in Route. Aborting...");
				System.out.println("\n");
				
				return "ERROR: Such Stop does not exist in Route. Aborting...";
			}
			System.out.println("Successfully deleted RouteHasStop information.");
			System.out.println("\n");
			return "Successfully deleted RouteHasStop information.";
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			return "ERROR: Invalid request.";
		}
	}
	
	public static String insertRouteHasStop(int routeNumber, int stopNumber, int orderNumber) {
		
		String query =
				"INSERT INTO RouteHasStop " +
						"(RouteNumber, StopNumber, orderNumber) " +
						"VALUES (?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, routeNumber);
			preparedStatement.setInt(2, stopNumber);
			preparedStatement.setInt(3, orderNumber);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			System.out.println("Successfully connected route to stop.");
			System.out.println("\n");
			return "Successfully connected route to stop.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Failed to connect route and stop.";
		}
	}

	private static void userOnVehicle(Connection connection){
		System.out.println("\n");
		Scanner sc = new Scanner(System.in);
		System.out.println("UserID: ");
		int user = sc.nextInt();
		System.out.println("\n");
		System.out.println("Vehicle Number: ");
		int vNum = sc.nextInt();
		System.out.println("\n");

		String query =
				"SELECT V.VehicleNumber, R.StopNumber " +
						"FROM Vehicle V, Tap T, FareCardHas F, RouteHasStop R " +
						"WHERE F.CardNumber=T.CardNumber AND F.UserID="+user+" AND T.StopNumber=R.StopNumber AND " +
						"V.RouteNumber=R.RouteNumber AND V.VehicleNumber="+vNum;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println("Yes. User "+user+" could have been on Vehicle "+vNum+" since User got on from Stop "+resultSet.getString("StopNumber"));
				return;
			}
			System.out.println("No. User "+user+" was NOT on Vehicle "+vNum);
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		} finally {
			System.out.println("\n");
		}
	}

	private static void userAtStopAtTime(Connection connection){
		System.out.println("\n");
		Scanner sc = new Scanner(System.in);
		System.out.println("UserID: ");
		int user = sc.nextInt();
		System.out.println("\n");
		System.out.println("Stop Number: ");
		int stop = sc.nextInt();
		System.out.println("\n");

		System.out.println("From: ");
		System.out.println("Year (0~8099): ");
		int yr = sc.nextInt();
		System.out.println("Month (0~11): ");
		int m = sc.nextInt();
		System.out.println("Day (1~31): ");
		int d = sc.nextInt();

		System.out.println("\n");
		System.out.println("To: ");
		System.out.println("Year (0~8099): ");
		int yr2 = sc.nextInt();
		System.out.println("Month (0~11): ");
		int m2 = sc.nextInt();
		System.out.println("Day (1~31): ");
		int d2 = sc.nextInt();
		System.out.println("\n");

		String temp = String.format("%04d", yr)+"-"+m+"-"+d;
		Date fromDate = Date.valueOf(temp);
		temp = String.format("%04d", yr2)+"-"+m2+"-"+d2;
		Date toDate = Date.valueOf(temp);

		String query =
				"SELECT T.TimeTap " +
						"FROM Tap T, FareCardHas F " +
						"WHERE F.CardNumber=T.CardNumber AND F.UserID="+user+" AND T.StopNumber="+stop;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				if ((resultSet.getDate("TimeTap").after(fromDate) &&
						resultSet.getDate("TimeTap").before(toDate)) ||
						resultSet.getDate("TimeTap").equals(fromDate) ||
						resultSet.getDate("TimeTap").equals(toDate)) {
					System.out.println("Yes. User "+user+" was at Stop "+stop+" on "+resultSet.getDate("TimeTap").toString());
					return;
				}
			}
			System.out.println("No. User "+user+" was NOT at Stop "
					+stop+" between "+fromDate.toString()+" and "+toDate.toString());
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		} finally {
			System.out.println("\n");
		}
	}

	private static void stopNotVisited(Connection connection){
		System.out.println("\n");
		Scanner sc = new Scanner(System.in);
		System.out.println("UserID: ");
		int user = sc.nextInt();
		System.out.println("\n");

		String query =
				"SELECT StopNumber " +
						"FROM StopHas " +
						"MINUS " +
						"SELECT T.StopNumber " +
						"FROM Tap T, FareCardHas F " +
						"WHERE F.CardNumber=T.CardNumber AND F.UserID="+user;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println("Stop: "+resultSet.getString("StopNumber"));
			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		} finally {
			System.out.println("\n");
		}
	}

	private static void modifyStop(Connection connection) {
		System.out.println("\n");

		String city = null;
		String streetName = "";
		int streetNum = 0;
		int stop;

		System.out.println("Stop Number you wish to modify: ");
		Scanner sc = new Scanner(System.in);
		stop = sc.nextInt();

		String query =
				"SELECT City, StreetName, StreetNumber FROM StopHas WHERE StopNumber = " + stop;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (!resultSet.isBeforeFirst()) {
				System.out.println("Stop Does Not Exist. Aborting...");
				System.out.println("\n");
				return;
			} else {
				while (resultSet.next()) {
					city = resultSet.getString("City");
					streetName = resultSet.getString("StreetName");
					streetNum = resultSet.getInt("StreetNumber");
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}

		System.out.println("Field you wish to modify (Type 'City'/'StreetName'/'StreetNumber'/'All'): ");
		Scanner s = new Scanner(System.in);
		String field = s.nextLine();
		System.out.println("\n");


		if (field.equals("City")) {
			System.out.println("City: ");
			Scanner a = new Scanner(System.in);
			city = a.nextLine();
			System.out.println("\n");
		} else if (field.equals("StreetName")) {
			System.out.println("Street Name: ");
			Scanner b = new Scanner(System.in);
			streetName = b.nextLine();
			System.out.println("\n");
		} else if (field.equals("StreetNumber")) {
			System.out.println("Street Number: ");
			Scanner c = new Scanner(System.in);
			streetNum = c.nextInt();
			System.out.println("\n");
		} else if (field.equals("All")) {
			System.out.println("City: ");
			Scanner a = new Scanner(System.in);
			city = a.nextLine();
			System.out.println("\n");
			System.out.println("Street Name: ");
			Scanner b = new Scanner(System.in);
			streetName = b.nextLine();
			System.out.println("\n");
			System.out.println("Street Number: ");
			Scanner c = new Scanner(System.in);
			streetNum = c.nextInt();
			System.out.println("\n");
		} else {
			System.out.println("Wrong Input. Aborting ...");
			System.out.println("\n");
		}

		String q =
				"SELECT City, StreetName, StreetNumber FROM Address WHERE City = '" + city + "' AND StreetName = '" + streetName + "' AND StreetNumber = " + streetNum;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(q);
			if (!resultSet.isBeforeFirst()) {
				//Add address
				System.out.println("Zone: ");
				Scanner a = new Scanner(System.in);
				int zone = a.nextInt();
				System.out.println("\n");
				String insertQuery =
						"INSERT INTO Address" +
								"(City, StreetName, StreetNumber, TransitZone)" +
								"VALUES (?, ?, ?, ?)";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
					preparedStatement.setString(1, city);
					preparedStatement.setString(2, streetName);
					preparedStatement.setInt(3, streetNum);
					preparedStatement.setInt(4, zone);
					preparedStatement.executeUpdate();
					preparedStatement.close();
				} catch (SQLException e) {
					System.out.println("Address not created..");
				}
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}

		String updateStop = "UPDATE StopHas SET City = '" + city + "', StreetName='" + streetName + "', StreetNumber=" + streetNum + " WHERE StopNumber = " + stop;
	}

	/*
	 * DO THIS
	 */
	
	public static String exchangeBusRoute(int bOne, int bTwo) {
		//Scanner sc = new Scanner(System.in);
		System.out.println("\n");
		String output = "";
		
		System.out.println("Bus#1 (Vehicle Number): ");
		//int bOne = sc.nextInt();
		System.out.println("Bus#2 (Vehicle Number): ");
		//Scanner s = new Scanner(System.in);
		//int bTwo = s.nextInt();

		if (bOne == bTwo) {
			System.out.println("Bus#1 and Bus#2 are identical. Try again. Aborting...");
			System.out.println("\n");
			String failed = "FAILED";
			String message = "Bus#1 and Bus#2 are identical. Try again. Aborting...";
			//output[0] = failed;
			//output[1] = message;
			output = "ERROR: Bus#1 and Bus#2 are identical. Try again. Aborting...";
 			return output;
		}

		int route1 = 0;
		int route2 = 0;

		String bus =
				"SELECT RouteNumber FROM Vehicle WHERE VehicleNumber = "+bOne;
		String bus2 =
				"SELECT RouteNumber FROM Vehicle WHERE VehicleNumber = "+bTwo;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(bus);
			if (!resultSet.isBeforeFirst()) {
				System.out.println("Bus#1 does not exist. Aborting...");
				System.out.println("\n");
				String message = "Bus#1 does not exist. Aborting...";
				output = "Bus#1 does not exist. Aborting...";
	 			return output;
			}else {
				while (resultSet.next()) {
					route1 = resultSet.getInt("RouteNumber");
				}
			}
		}catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(bus2);
			if (!resultSet.isBeforeFirst()) {
				System.out.println("Bus#2 does not exist. Aborting...");
				System.out.println("\n");
				String message = "Bus #2 does not exist. Aborting...";
				output = "ERROR: Bus #2 does not exist. Aborting...";
				return output;
			}else {
				while (resultSet.next()) {
					route2 = resultSet.getInt("RouteNumber");
				}
			}
		}catch (SQLException e ) {
			String message = "Exception error";
			output = "ERROR: Invalid input";
			System.out.println("SQL Exception");
			e.printStackTrace();
			return output;
		}
		String updateNull =
				"UPDATE Vehicle SET RouteNumber = NULL WHERE VehicleNumber = "+bOne;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(updateNull);
			int rowCount = statement.executeUpdate(updateNull);
			if (rowCount==0) {
				System.out.println("Bus#1 does not exist. Aborting...");
				System.out.println("\n");
				String message = "Bus#1 does not exist. Aborting...";
				output = "ERROR: Bus#1 does not exist. Aborting...";
				return output;
			}
		} catch (SQLException e ) {
			String message = "Exception error";
			output = "ERROR: Invalid input";
			System.out.println("SQL Exception");
			e.printStackTrace();
			return output;
		}
		updateNull =
				"UPDATE Vehicle SET RouteNumber = NULL WHERE VehicleNumber = "+bTwo;
		try {
			statement = connection.prepareStatement(updateNull);
			int rowCount = statement.executeUpdate(updateNull);
			if (rowCount==0) {
				System.out.println("Bus#2 does not exist. Aborting...");
				System.out.println("\n");
				String message = "Bus#2 does not exist. Aborting...";
				output = "ERROR: Bus #2 does not exist. Aborting...";
				return output;
			}
		} catch (SQLException e ) {
			String message = "Exception error";
			output = "ERROR: Invalid input";
			System.out.println("SQL Exception");
			e.printStackTrace();
			return output;
		}
		String updateBusOne =
				"UPDATE Vehicle SET RouteNumber = "+route2+" WHERE VehicleNumber = "+bOne;
		PreparedStatement q = null;
		try {
			q = connection.prepareStatement(updateBusOne);
			int rowCount = q.executeUpdate(updateBusOne);
		} catch (SQLException e ) {
			String message = "Exception error";
			output = "ERROR: Invalid input";
			System.out.println("SQL Exception");
			e.printStackTrace();
			return output;
		}
		String updateBusTwo =
				"UPDATE Vehicle SET RouteNumber = "+route1+" WHERE VehicleNumber = "+bTwo;
		PreparedStatement x = null;
		try {
			x = connection.prepareStatement(updateBusTwo);
			int rowCount = x.executeUpdate(updateBusTwo);
		} catch (SQLException e ) {
			String message = "Exception error";
			output = "ERROR: Invalid input";
			System.out.println("SQL Exception");
			e.printStackTrace();
			return output;
		}
		output = "Success! Query was successful";
		return output;
	}
	
	public static ArrayList<String> fetchStops() {
		System.out.println("\n");
		String fetchQuery =
				"SELECT * " +
						"FROM StopHas ";
		ArrayList<String> outputList = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			if (!resultSet.isBeforeFirst()) {
				System.out.println("Empty Stop Information. Aborting...");
				System.out.println("\n");
				outputList.add("Empty Stop Information. Aborting...");
				return outputList;
			}
			while (resultSet.next()) {
				int s = resultSet.getInt("StopNumber");
				String c = resultSet.getString("City");
				String name = resultSet.getString("StreetName");
				int num = resultSet.getInt("StreetNumber");

				System.out.println("Stop Number: "+String.format("%06d", s));
				System.out.println("City: " + c);
				System.out.println("Street Name: " + name);
				System.out.println("Street Number: " + num);
				System.out.println("\n");
				
				String text = "Stop Number: "+String.format("%06d", s) + " City: " + c + " Street Name: " + name + " Street Number: " + num;
				outputList.add(text);
				// DO THIS
			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			ArrayList<String> errorList = new ArrayList<String>();
	    	errorList.add("ERROR: Invalid input");
	    	return errorList;
		}
		return outputList;
	}

	//
	public static String insertRoute(int rNum, String name) {
		//Scanner sc = new Scanner(System.in);
		System.out.println("\n");

		System.out.println("Route Number: ");
		//int rNum = sc.nextInt();
		System.out.println("Route Name: ");
		//Scanner s = new Scanner(System.in);
		//String name = s.nextLine();
		String route =
				"INSERT INTO Route" +
						"(RouteNumber, RouteName)" +
						"VALUES (?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(route);
			preparedStatement.setInt(1, rNum);
			preparedStatement.setString(2, name);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			System.out.println("Successfully added Route.");
			System.out.println("\n");
			return "Successfully added Route.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Failed to add route.";
		}
	}
	
	public static String insertStop(int sNum, String city, String streetName, int streetNum, int zone) {
		//Scanner sc = new Scanner(System.in);
		System.out.println("\n");

		System.out.println("Stop Number (6 digits): ");
		System.out.println("City: ");
		//Scanner s = new Scanner(System.in);
		System.out.println("Street Name: ");
		//Scanner q = new Scanner(System.in);
		System.out.println("Street Number: ");
		//Scanner g = new Scanner(System.in);
		String fetchQuery =
				"SELECT * FROM Address WHERE City = '"+city+"' AND StreetName = '"+streetName+"' AND StreetNumber = "+streetNum;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			if (!resultSet.isBeforeFirst()) {
				System.out.println("Zone: ");
				String address =
						"INSERT INTO Address" +
								"(City, StreetName, StreetNumber, TransitZone)" +
								" VALUES (?, ?, ?, ?)";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(address);
					preparedStatement.setString(1, city);
					preparedStatement.setString(2, streetName);
					preparedStatement.setInt(3, streetNum);
					preparedStatement.setInt(4, zone);
					preparedStatement.executeUpdate();
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}

		String stop =
				"INSERT INTO StopHas" +
						"(StopNumber, City, StreetName, StreetNumber)" +
						" VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(stop);
			preparedStatement.setInt(1, sNum);
			preparedStatement.setString(2, city);
			preparedStatement.setString(3, streetName);
			preparedStatement.setInt(4, streetNum);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			System.out.println("Successfully added Stop.");
			System.out.println("\n");
			return "Successfully added Stop.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR: Invalid input";
		}
	}

	// DO THIS
	public static String insertTransit(int vNum, int yr, int route, String tType) {
		//Scanner sc = new Scanner(System.in);
		System.out.println("\n");

		System.out.println("Vehicle Number: ");
		//vNum = sc.nextInt();
		System.out.println("Route Number: ");
		//route = sc.nextInt();
		String fetchQuery =
				"SELECT * " +
						"FROM Route WHERE RouteNumber = "+route;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			if (!resultSet.isBeforeFirst()) {
				System.out.println("Such route does not exist. Please add the corresponding route first. Aborting...");
				System.out.println("\n");
				return "ERROR: Such route does not exist. Please add the corresponding route first. Aborting...";
			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			return "ERROR: Invalid input";
		}
		System.out.println("Year Built: ");

		String insertQuery =
				"INSERT INTO Vehicle" +
						"(VehicleNumber, YearNumber, RouteNumber)" +
						"VALUES (?, ?, ?)";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, vNum);
			preparedStatement.setInt(2, yr);
			preparedStatement.setInt(3, route);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("There can only be one vehicle per Route. Please try again. Aborting...");
			System.out.println("\n");
			return "ERROR: There can only be one vehicle per Route. Please try again. Aborting...";
		}

		System.out.println("Type of vehicle(type 'Bus' or 'Train'): ");
		Scanner s = new Scanner(System.in);
		String type = s.nextLine();

		if (!type.equals("Bus")  && !type.equals("Train")) {
			System.out.println("\n");
			System.out.println("Wrong input. Aborting...");
			return "ERROR: Wrong input. Aborting...";
		}

		if (type.equals("Bus")) {
			System.out.println("Bus type: ");
			Scanner q = new Scanner(System.in);
			String bType = q.nextLine();
			System.out.println("License Plate: ");
			Scanner g = new Scanner(System.in);
			String license = g.nextLine();
			String busQuery =
					"INSERT INTO Bus" +
							"(VehicleNumber, LicenseNumber, BusType)" +
							"VALUES (?, ?, ?)";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(busQuery);
				preparedStatement.setInt(1, vNum);
				preparedStatement.setString(2, license);
				preparedStatement.setString(3, bType);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				System.out.println("Successfully added Bus in database.");
				System.out.println("\n");
				return "Successfully added Bus in database.";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Train type: ");
			String train =
					"INSERT INTO Train" +
							"(VehicleNumber, TrainType)" +
							"VALUES (?, ?)";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(train);
				preparedStatement.setInt(1, vNum);
				preparedStatement.setString(2, tType);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				System.out.println("Successfully added Train in database.");
				System.out.println("\n");
				return "Successfully added Train in database.";
			} catch (SQLException e) {
				e.printStackTrace();
				return "ERROR: Invalid input";
			}
		}
		return "Successfully added Train in database.";
	}

	public static ArrayList<String> fetchAllUser() {
		System.out.println("\n");
		ArrayList<String> outputList = new ArrayList<String>();
		String fetchQuery =
				"SELECT * " +
						"FROM Users ";

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			if (!resultSet.isBeforeFirst()) {
				System.out.println("Empty User Information. Aborting...");
				System.out.println("\n");
				outputList.add("Empty User Information. Aborting...");
				return outputList;
			}
			while (resultSet.next()) {
				int id = resultSet.getInt("UserID");
				String name = resultSet.getString("Name");
				int age = resultSet.getInt("Age");

				System.out.println("UserID: "+String.format("%03d", id));
				System.out.println("Name: " + name);
				System.out.println("Age: " + age);
				System.out.println("\n");
				
				String output = "UserID: " +String.format("%03d", id) + "Name: " + name + "Age: " + age;
				outputList.add(output);
			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			ArrayList<String> errorList = new ArrayList<String>();
	    	errorList.add("ERROR: Invalid input");
	    	return errorList;
		}
		return outputList;
	}

	public static ArrayList<String> fetchVehicle() {
		System.out.println("\n");
		String fetchQuery =
				"SELECT * " +
						"FROM Vehicle ";

		ArrayList<String> outputList = new ArrayList<String>();
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			while (resultSet.next()) {
				int vNum = resultSet.getInt("VehicleNumber");
				int yr = resultSet.getInt("YearNumber");
				int route = resultSet.getInt("RouteNumber");
				String v = "Bus";

				String q = "SELECT VehicleNumber " +
						"FROM Bus WHERE VehicleNumber = "+vNum;
				Statement s = connection.createStatement();
				ResultSet resultSet1 = s.executeQuery(q);
				if (!resultSet1.isBeforeFirst())
					v = "Train";

				System.out.println("Vehicle Number: "+String.format("%05d", vNum));
				System.out.println("Year: " + yr);
				System.out.println("Route: " + route);
				System.out.println("Type: " + v);
				System.out.println("\n");
				
				
				outputList.add("Vehicle Number: " +String.format("%05d", vNum) + " Year: " + yr + " Route: " + route + " Type: " + v);
			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			ArrayList<String> errorList = new ArrayList<String>();
	    	errorList.add("ERROR: Invalid input");
	    	return errorList;
		}
		return outputList;
	}

	public static String deleteUser(int user) {
		System.out.println("\n");

		System.out.println("Input UserID of a user you wish to delete: ");
		//Scanner sc = new Scanner(System.in);
		//int user = sc.nextInt();
		System.out.println("\n");

		String fetchQuery =
				"DELETE FROM Users WHERE UserID = "+user;

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(fetchQuery);
			int rowCount = statement.executeUpdate(fetchQuery);
			if (rowCount==0) {
				System.out.println("Such user does not exist. Aborting...");
				System.out.println("\n");
				return "Such user does not exist. Aborting...";
			}
			System.out.println("Successfully deleted User information.");
			System.out.println("\n");
			return "Successfully deleted User information.";
			
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			return "ERROR: Invalid request";
		}
	}

	public static String deleteStop(int stop) {
		System.out.println("\n");

		System.out.println("Input StopNumber you wish to delete from stop information: ");
		//Scanner sc = new Scanner(System.in);
		//int stop = sc.nextInt();
		System.out.println("\n");

		String fetchQuery =
				"DELETE FROM StopHas WHERE StopNumber = "+stop;

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(fetchQuery);
			int rowCount = statement.executeUpdate(fetchQuery);
			if (rowCount==0) {
				System.out.println("Such stop does not exist. Aborting...");
				System.out.println("\n");
				return "Such stop does not exist. Aborting...";
			}
			System.out.println("Successfully deleted Stop information.");
			System.out.println("\n");
			return "Successfully deleted Stop information.";
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			return "ERROR: Invalid request";
		}
	}
	

	public static String deleteTransit(int vNum) {
		System.out.println("\n");

		System.out.println("Input VehicleNumber of a bus/train you wish to delete from transit information: ");
		//Scanner sc = new Scanner(System.in);
		//int vNum = sc.nextInt();
		System.out.println("\n");

		String fetchQuery =
				"DELETE FROM Vehicle WHERE VehicleNumber = "+vNum;

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(fetchQuery);
			int rowCount = statement.executeUpdate(fetchQuery);
			if (rowCount==0) {
				System.out.println("Such vehicle does not exist. Aborting...");
				System.out.println("\n");
				return "Such vehicle does not exist. Aborting...";
			}
			System.out.println("Successfully deleted Vehicle information.");
			System.out.println("\n");
			return "Successfully deleted Vehicle information.";
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			return "ERROR: Invalid request";
		}
	}

	//
	public static String addBalance(int card, float add) {
		System.out.println("\n");

		System.out.println("FareCard Number: ");
		//Scanner sc = new Scanner(System.in);
		//int card = sc.nextInt();
		System.out.println("\n");

		float balance = 0;

		Statement statementz = null;
		try {
			String query = "SELECT Balance " +
					"FROM FareCardHas "+
					"WHERE CardNumber = "+card;
			statementz = connection.createStatement();
			ResultSet resultSet = statementz.executeQuery(query);
			if (!resultSet.next()) {
				System.out.println("CardNumber does not exist. Aborting.");
				System.out.println("\n");
				return "CardNumber does not exist. Aborting.";
			}
			balance = resultSet.getFloat("Balance");
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}

		System.out.println("Current Balance: "+String.format("%.2f", balance));
		System.out.println("Amount you wish to add: ");
		System.out.println("\n");
		float newBalance = add+balance;

		String fetchQuery =
				"UPDATE FareCardHas SET Balance = "+newBalance + " WHERE CardNumber = "+card;
		
		String fetchQuery2 = 
				"SELECT balance FROM FareCardHas WHERE CardNumber = " + card;

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(fetchQuery);
			int rowCount = statement.executeUpdate(fetchQuery);
			if (rowCount==0) {
				System.out.println("Fare Card does not exist. Aborting...");
				System.out.println("\n");
				return "Fare Card does not exist. Aborting...";
			}
			System.out.println("Successfully reloaded balance on Fare Card.");
			System.out.println("\n");
			statement.executeUpdate(fetchQuery2);
			
			ResultSet resultSet = statement.executeQuery(fetchQuery2);
	        while (resultSet.next()) {
	        	float money = (float) Math.round(resultSet.getFloat(1)*100)/100;
	        	return "Successfully reloaded balance on Fare Card.\nBalance on Fare Card " + card + " is $" + money;

	        }
	        	
			return "Successfully reloaded balance on Fare Card.";
			
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			return "ERROR: Balance cannot become negative";
		}
	}

	public static ArrayList<String> userFareCard(String id) {
		System.out.println("\n");
		
		ArrayList<String> outputList = new ArrayList<String>();

		System.out.println("Input UserID: ");
		//Scanner sc = new Scanner(System.in);
		//String id = sc.nextLine();
		System.out.println("\n");

		String fetchQuery =
				"SELECT CardNumber, Balance " +
						"FROM FareCardHas "+
						"WHERE UserID = "+id;

		Statement statement = null;

		System.out.println("UserID "+id+" has the following FareCards: ");
		System.out.println("\n");
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			while (resultSet.next()) {
				int c = resultSet.getInt("CardNumber");
				float b = resultSet.getFloat("Balance");

				System.out.println("CardNumber "+c+" with Balance "+String.format("%.2f", b));
				String output = "CardNumber "+c+" with Balance "+String.format("%.2f", b);
				outputList.add(output);
			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			ArrayList<String> errorList = new ArrayList<String>();
	    	errorList.add("ERROR: Invalid input");
	    	return errorList;
		}
		return outputList;
	}

	
	// THIS Needs fixing
	/*
	public static String modifyUserInfo(Connection connection) {
		System.out.println("\n");

		System.out.println("UserID: ");
		//Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		System.out.println("\n");

		System.out.println("Info you wish to change (Type either 'Name' or 'Age'): ");
		Scanner s = new Scanner(System.in);
		String info = s.nextLine();
		System.out.println("\n");

		int age = 0;
		String name = "";
		String fetchQuery="";

		if (info.equals("Name")) {
			System.out.println("New name: ");
			Scanner a = new Scanner(System.in);
			name = a.nextLine();
			System.out.println("\n");
			fetchQuery =
					"UPDATE Users SET "+info + " = '"+name + "' WHERE UserID = "+id;
		} else if (info.equals("Age")) {
			System.out.println("New age: ");
			Scanner a = new Scanner(System.in);
			age = a.nextInt();
			if (age < 0) {
				System.out.println("Age cannot be a negative number. Try again. Aborting...");
				System.out.println("\n");
				return "Age cannot be a negative number. Try again. Aborting...";
			}
			System.out.println("\n");
			fetchQuery =
					"UPDATE Users SET "+info + " = "+age + "WHERE UserID = "+id;
		} else {
			System.out.println("Error Occurred; User did not type either 'Name' or 'Age'. Try Again. Aborting.");
			System.out.println("\n");
			return "Error Occurred; User did not type either 'Name' or 'Age'. Try Again. Aborting.";
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(fetchQuery);
			int rowCount = statement.executeUpdate(fetchQuery);

			if (rowCount == 0) {
				System.out.println("UserID does not exist. Aborting..");
				System.out.println("\n");
				return "UserID does not exist. Aborting..";
			}

			System.out.println("Successfully updated user info.");
			System.out.println("\n");
			return "Successfully updated user info.";

		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		return "Successfully updated user info.";
	}
	*/

	public static String routeOfVehicle(int vNum) {
		System.out.println("\n");

		System.out.println("Input VehicleNumber of a bus or a train that you wish to check its route (NOT a license plate): ");
		//Scanner sc = new Scanner(System.in);
		//int vNum = sc.nextInt();
		System.out.println("\n");

		String fetchQuery =
				"SELECT RouteNumber " +
						"FROM Vehicle "+
						"WHERE VehicleNumber = "+vNum;

		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			while (resultSet.next()) {
				int r = resultSet.getInt("RouteNumber");

				System.out.println("Bus/Train with VehicleNumber, "+vNum+" is in Route "+r);
				System.out.println("\n");
				return "Bus/Train with VehicleNumber, "+vNum+" is in Route "+r;
			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			return "ERROR: Invalid request";
		}
		return "ERROR: Vehicle number doesnt exist. ";
	}
	
	public static String closeStops(int zone) {
		System.out.println("\n");
		
		System.out.println("Input Zone that you're currently at: ");
		//Scanner sc = new Scanner(System.in);
	    //int zone = sc.nextInt();
	    System.out.println("\n");
	    
	    String fetchQuery = 
			"SELECT S.StopNumber, S.StreetNumber, S.StreetName, S.City " + 
			"FROM StopHas S, Address A "+
			"WHERE A.TransitZone = " + zone+" AND S.StreetNumber=A.StreetNumber AND S.StreetName=A.StreetName AND S.City=A.City";
	    
	    Statement statement = null;
	    try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	int stop = resultSet.getInt("StopNumber");
	        	String streetName = resultSet.getString("StreetName");
	        	int stNum = resultSet.getInt("StreetNumber");
	        	String city = resultSet.getString("City");
	        	
	        	System.out.println("Close Stops Near you are:");
	        	System.out.println("Stop Number "+stop+", located at "+stNum+" "+streetName+", "+city);
	        	System.out.println("\n");
	        	return "Stop Number "+stop+", located at "+stNum+" "+streetName+", "+city;
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	return "ERROR: Invalid request";
	    } 
	    return "Error";
	}
	
	
	public static String stopInRoute(int stop, int route) {
		System.out.println("\n");
		
		System.out.println("Input Stop Number (int): ");
		//Scanner sc = new Scanner(System.in);
	    //int stop = sc.nextInt();
	    
	    System.out.println("Input Route Number you wish to check (int): ");
	    //int route = sc.nextInt();
	    
	    String fetchQuery = 
			"SELECT RouteNumber " + 
			"FROM RouteHasStop "+
			" WHERE StopNumber = " + stop;
	    
	    Statement statement = null;
	    try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        boolean temp = false;
	        while (resultSet.next()) {
	        	int r = resultSet.getInt("RouteNumber");
	        	if (route == r) {
	        		System.out.println("Yes. Stop "+stop+" is in Route "+route+".");
	        		temp = true;
	        		System.out.println("\n");
	        		return "Yes. Stop "+stop+" is in Route "+route+".";
	        		//break;
	        	}
	        }
	        if (temp == false) {
	        	System.out.println("No. Stop "+stop+" is NOT in Route "+route+".");
	        	System.out.println("\n");
	        	return "No. Stop "+stop+" is NOT in Route "+route+".";
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	return "ERROR: Invalid request";
	    } 
	    return "Yes. Stop "+stop+" is in Route "+route+".";
	}
	
	
	public static String vehicleInRoute(int vehicle, int route) {
		System.out.println("\n");
		
		System.out.println("Input Vehicle Number (int): ");
		//Scanner sc = new Scanner(System.in);
	    //int vehicle = sc.nextInt();
	    
	    System.out.println("Input Route Number you wish to check (int): ");
	    //int route = sc.nextInt();
	    
	    String fetchQuery = 
			"SELECT RouteNumber " + 
			"FROM Vehicle "+
			"WHERE VehicleNumber = " + vehicle;
	    
	    Statement statement = null;
	    try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        boolean temp = false;
	        while (resultSet.next()) {
	        	int r = resultSet.getInt("RouteNumber");
	        	if (route == r) {
	        		System.out.println("Yes. Vehicle "+vehicle+" is in Route "+route+".");
	        		temp = true;
	        		System.out.println("\n");
	        		return "Yes. Vehicle "+vehicle+" is in Route "+route+".";
	        		//break;
	        	}
	        }
	        if (temp == false) {
	        	System.out.println("No. Vehicle "+vehicle+" is NOT in Route "+route+".");
	        	System.out.println("\n");
	        	return "No. Vehicle "+vehicle+" is NOT in Route "+route+".";
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	return "ERROR: Invalid request";
	    } 
	    return "Yes. Vehicle "+vehicle+" is in Route "+route+".";
	}
	
	
	public static ArrayList<String> fetchFareCard() {
		System.out.println("\n");
	    String fetchQuery = 
			"SELECT *" + 
			"FROM FareCardHas ";
	    
	    ArrayList<String> outputList = new ArrayList<String>();
	    
	    Statement statement = null;
	    try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	int cardNum = resultSet.getInt("CardNumber");
	        	float balance = resultSet.getFloat("Balance");
	        	int userID = resultSet.getInt("UserID");
	        	System.out.println("FareCard Number: "+cardNum);
	        	System.out.println("Balance: " + String.format("%.2f", balance));
	        	System.out.println("UserID: " + userID);
	        	System.out.println("\n");
	        	String output = "FareCard Number: " + cardNum + " Balance: " + String.format("%.2f", balance) + " UserID: " + userID;
	        	outputList.add(output);
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	ArrayList<String> errorList = new ArrayList<String>();
	    	errorList.add("ERROR: Invalid input");
	    	return errorList;
	    } 
	    return outputList;
	}

	public String fetchName(int id){
		String fetchQuery =
				"SELECT *" +
						"FROM Users " +
						"WHERE UserID = " + id;
		Statement statement = null;
		String name = "";
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(fetchQuery);
			while (resultSet.next()) {
				name = resultSet.getString("Name");

			}
		} catch (SQLException e ) {
			System.out.println("SQL Exception");
			e.printStackTrace();
			return "ERROR: Invalid request";
		}
		return name;
	}

	public static String fetchUser(int input) {
		
		System.out.println("\n");
		System.out.println("Input user ID (int): ");
		//Scanner sc = new Scanner(System.in);
	    //int input = sc.nextInt();
	    String fetchQuery = 
			"SELECT *" + 
			"FROM Users " + 
			"WHERE UserID = " + input;
	    
	    String text = "";
	    Statement statement = null;
	    try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
			if (!resultSet.isBeforeFirst()) {
				System.out.println("Such user does not exist. Aborting...");
				System.out.println("\n");
				return "Such user does not exist. Aborting...";
			}
	        while (resultSet.next()) {
	        	int userId = resultSet.getInt("UserID");
	        	String name = resultSet.getString("Name");
	        	int age = resultSet.getInt("Age");
	        	System.out.println("User ID: " + userId);
	        	System.out.println("Name: " + name);
	        	System.out.println("Age: " + age);
	        	System.out.println("\n");
	        	text = "User ID: " + userId + " Name: " + name + " Age: " + age;
	        	return text;
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	return "ERROR: Invalid request";
	    } 
	    return text;
	}
	
	
	public static ArrayList<String> fetchTap(int input) {
		ArrayList<String> outputList = new ArrayList<String>();
		
		System.out.println("\n");
		System.out.println("Input FardCard Number (int): ");
		//Scanner sc = new Scanner(System.in);
	    //int input = sc.nextInt();
	    String fetchQuery = 
			"SELECT *" + 
			"FROM Tap " + 
			"WHERE CardNumber = " + input;
	    
	    Statement statement = null;
	    try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	Date time = resultSet.getDate("TimeTap");
	        	int stopNum = resultSet.getInt("StopNumber");
	        	System.out.println("Time Tapped: " + time);
	        	System.out.println("Stop Number: " + stopNum);
	        	System.out.println("\n");
	        	
	        	outputList.add("Time Tapped: " + time + " Stop Number + " + stopNum);
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	ArrayList<String> errorList = new ArrayList<String>();
	    	outputList.add("ERROR: Invalid request");
	    	return errorList;
	    } 
	    
	    return outputList; 
	}
	
	
	public static String insertUser(int userId, String name, int age) {

		String output = "";
		System.out.println("\n");
		//Scanner sc = new Scanner(System.in);
		System.out.println("User ID to insert: ");
	    //int userId = sc.nextInt();
	   // sc.nextLine();
	    
	    System.out.println("User Name: ");
	   // String name = sc.nextLine();
	    //sc.nextLine();
	    
	    System.out.println("User Age to insert: ");
	    //int age = sc.nextInt();
	    
	    System.out.println("\n");
	    String insertQuery = 
	    		"INSERT INTO Users" +
	    		"(UserID, Name, Age)" + 
	    		"VALUES (?, ?, ?)";
	    
	    PreparedStatement preparedStatement = null;
	    
	    try {
		    preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, "\"" + name + "\"");
		    preparedStatement.setInt(3, age);
		    preparedStatement.executeUpdate();
		    preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR: Invalid request";
		} finally {
			System.out.println("Inserting UserID: " + userId + " Name: " + name + " Age: " + age + " into Users Table");
			System.out.println("\n");
			output = "Inserting UserID: " + userId + " Name: " + name + " Age: " + age + " into Users Table";
			return output;
		}
	    
	}
	
	public static String insertTap(int cardNum, int stopNum, int yr, int m, int d) {
		String output = "";
		
		
		//Scanner sc = new Scanner(System.in);
		System.out.println("\n");
		System.out.println("FardCard to insert Info to: ");
	    //int cardNum = sc.nextInt();
	    //sc.nextLine();
	    
	    System.out.println("Stop the FareCard was Tapped At: ");
	    //int stopNum = sc.nextInt();
	    //sc.nextLine();
	    
	    System.out.println("Year Tapped (0~8099): ");
	    //int yr = sc.nextInt();
	    System.out.println("Month Tapped (0~11): ");
	    //int m = sc.nextInt();
	    System.out.println("Day Tapped (1~31): ");
	    //int d = sc.nextInt();
	    System.out.println("\n");
	    
	    String temp = yr+"-"+m+"-"+d;
	    java.sql.Date date = java.sql.Date.valueOf(temp);
	    
	    String insertQuery = 
	    		"INSERT INTO Tap" +
	    		"(TimeTap, CardNumber, StopNumber)" + 
	    		"VALUES (?, ?, ?)";
	    
	    PreparedStatement preparedStatement = null;
	    
	    try {
		    preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setDate(1, date);
			preparedStatement.setInt(2, cardNum);
		    preparedStatement.setInt(3, stopNum);
		    preparedStatement.executeUpdate();
		    preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR: Invalid request";
		} finally {
			System.out.println("Inserting Tap at Time: " + date + " to CardNum: " + cardNum + " at Stop: " + stopNum + " into Tap Table");
			System.out.println("\n");
			
			output = "Inserting Tap at Time: " + date + " to CardNum: " + cardNum + " at Stop: " + stopNum + " into Tap Table";
			
		}
	    return output;
	    
	}
	
	
	public static String insertFareCard(int cardNum, float balance, int userID) {
		String output = "";
		System.out.println("\n");
		//Scanner sc = new Scanner(System.in);
		System.out.println("FareCard Number to insert: ");
	    //int cardNum = sc.nextInt();
	    //sc.nextLine();
	    
	    System.out.println("Balance: ");
	    //float balance = sc.nextFloat();
	    //sc.nextLine();
	    
	    System.out.println("UserID whom it belongs to: ");
	    //int userID = sc.nextInt();
	    System.out.println("\n");
	    
	    String insertQuery = 
	    		"INSERT INTO FareCardHas" +
	    		"(CardNumber, Balance, UserID)" + 
	    		"VALUES (?, ?, ?)";
	    
	    PreparedStatement preparedStatement = null;
	    
	    try {
		    preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, cardNum);
			preparedStatement.setFloat(2, balance);
		    preparedStatement.setInt(3, userID);
		    preparedStatement.executeUpdate();
		    preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR: Invalid request";
		} finally {
			System.out.println("Inserting FareCard: " + cardNum + " Balance: " + balance + " UserID: " + userID + " into FareCardHas Table");
			System.out.println("\n");
			output = "Inserting FareCard: " + cardNum + " Balance: " + balance + " UserID: " + userID + " into FareCardHas Table";
		}
	    return output;
	    
	}
	
	// Aggregation query: pick one query that requires the use of aggregation (min,	max, average, or count are all fine).
	public static String numberStopsOnRoute(int routeNumber){

		String output = "";
		System.out.println("\n");
		//Scanner sc = new Scanner(System.in);
		System.out.println("What route number do you want to count the stops for?");
	    //int routeNumber = sc.nextInt();
	    //sc.nextLine();
		
		String fetchQuery = 
			"SELECT COUNT('StopNumber') " +
			"FROM RouteHasStop " +
			"WHERE RouteNumber = " + routeNumber;
		
		Statement statement = null;
		int numberOfStops = 0;
	    try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	numberOfStops = resultSet.getInt(1);
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	return "ERROR: Invalid request";
	    } finally {
			if (numberOfStops > 0) { // The route has at least 1 stop
				System.out.println("Route number: " + routeNumber + " has " + numberOfStops + " number of stops total.");
				output = "Route number: " + routeNumber + " has " + numberOfStops + " number of stops total.";
			}
			else {
				System.out.println("Route number: " + routeNumber + " doesnt exist or has 0 stops.");
			}
			System.out.println("\n");
		}
		return output;
	}
	
	/*
	 * 
	 */
	public static String selectAgeRange(int ageMin, int ageMax){
		System.out.println("\n");
		//Scanner sc = new Scanner(System.in);
		System.out.println("What is the minimum age you are looking for (inclusive)?");
	    //int ageMin = sc.nextInt();
	    //sc.nextLine();
		System.out.println("What is the maximum age you are looking for (inclusive)?");
	    //int ageMax = sc.nextInt();
	    //sc.nextLine();
		
		String fetchQuery =
				"SELECT name " +
				"FROM Users " +
				"WHERE age >= " + ageMin + "AND age <= " + ageMax;
		
		Statement statement = null;
		String output = "";
		try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
			 output = "People with the ages between " + ageMin + " and " + ageMax + " are :";
			System.out.println("People with the ages between " + ageMin + " and " + ageMax + " are :");
	        while (resultSet.next()) {
	        	System.out.println(resultSet.getString("Name"));
	        	output = output + " " + resultSet.getString("Name");
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	return "ERROR: Invalid request";
	    } finally {
			System.out.println("\n");
		}
		return output;
	}
	
	public static String routesAllZones(){
		String view = 
				"CREATE VIEW RouteStopAddress(RouteNumber, TransitZone) AS " + 
				"SELECT rhs.RouteNumber, a.TransitZone " + 
				"FROM RouteHasStop rhs, StopHas sh, Address a " +
				"WHERE rhs.StopNumber = sh.StopNumber AND sh.City = a.City AND sh.StreetName = a.StreetName AND sh.StreetNumber = a.StreetNumber";
		
		String query =
				"SELECT DISTINCT RouteNumber " + 
				"FROM RouteHasStop rhs " +
				"WHERE NOT EXISTS (SELECT a.TransitZone " +
					"FROM Address a " +
					"WHERE NOT EXISTS (SELECT rsa.RouteNumber " +
						"FROM RouteStopAddress rsa " + 
						"WHERE rhs.RouteNumber = rsa.RouteNumber " +
						"AND rsa.TransitZone = a.TransitZone))";
		
		Statement statement = null;
		String output = "";
		try {
	    	statement = connection.createStatement();
	    	statement.executeUpdate(view);
	        ResultSet resultSet = statement.executeQuery(query);
	        output = "Routes that visit all TransitZones are: ";
	        while (resultSet.next()) {
	        	output = output + " " + resultSet.getString("RouteNumber");
	        }
			statement.executeUpdate("DROP VIEW RouteStopAddress");
	    } catch (SQLException e ) {
	    	e.printStackTrace();
	    	return "ERROR: Invalid request";
	    } finally {
			System.out.println("\n");
		}
		if (output.length() <= 40) {
			return "No routes visit all TransitZones";
		}
		else {
			return output;
		}
	}
	
	public static String insertAddress(String city, String streetName, int streetNumber, int transitZone) {
		String query =
				"INSERT INTO Address" +
						"(City, StreetName, StreetNumber, TransitZone)" +
						"VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, city);
			preparedStatement.setString(2, streetName);
			preparedStatement.setInt(3, streetNumber);
			preparedStatement.setInt(4, transitZone);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			System.out.println("Successfully added address.");
			System.out.println("\n");
			return "Successfully added address.";
		} catch (SQLException e) {
			e.printStackTrace();
			return "ERROR: Failed to add address.";
		}
	}
	
	public static String richestInAgeRanges(int age1, int age2, String maxOrMin){
		
		if (age1 >= age2) {
			return "ERROR: Age category 2 must be larger than (exclusive) than age category 1";
		}
		
		System.out.println("\n");
		//Scanner sc = new Scanner(System.in);
		System.out.println("Finds the richest or poorest age group by comparing the average value in each farecard. \nGroup 1 (1 of 3) includes ages from 0 to: (exclusive)");
	    //int age1 = sc.nextInt();
	   // sc.nextLine();
	    System.out.println("Group 2 (2 of 3) includes ages from " + age1 + " (inclusive) to: (exclusive)");
	    //int age2 = sc.nextInt();
	   // sc.nextLine();
	    //while (age1 >= age2) {
	    //	System.out.println("Second age must be greater or equal to the first age. Enter again");
	   // 	age2 = sc.nextInt();
	   // }
	    System.out.println("Group 3 (3 of 3) includes ages from (inclusive) " + age2 + " to the oldest person ");
	    System.out.println("Do you want the MAX or MIN of this group?");
	    //String maxOrMin = sc.nextLine();
	    /*
	    while (!maxOrMin.equals("MAX") && !maxOrMin.equals("MIN")) {
	    	System.out.println("Please type MAX or MIN, input was not one of these");
	    	maxOrMin = sc.nextLine();
	    }
	    */
	    
	    String view1 = 
	    		"CREATE VIEW AgeGroupSmallest(Age, Balance) AS " +
	    			"SELECT u.Age, f.Balance " +
	    			"FROM FareCardHas f, Users u " +
	    			"WHERE f.UserID = u.UserID AND u.Age < " + age1;
	    
	    String view2 = 
	    		"CREATE VIEW AgeGroupMedium(Age, Balance) AS " +
	    			"SELECT u.Age, f.Balance " +
	    			"FROM FareCardHas f, Users u " +
	    			"WHERE f.UserID = u.UserID AND u.Age >= " + age1 + "AND u.age < " + age2;
	    
	    String view3 = 
	    		"CREATE VIEW AgeGroupLargest(Age, Balance) AS " +
	    			"SELECT u.Age, f.Balance " +
	    			"FROM FareCardHas f, Users u " +
	    			"WHERE f.UserID = u.UserID AND u.Age >= " + age2;
	    
	    String view4 = 
		    	"CREATE VIEW AverageBalances(MinAge, MaxAge, Balance) AS " +	
		    		"SELECT MIN(age), MAX(age), AVG(balance) " +
		    		"FROM AgeGroupSmallest " +
		    		"UNION " +
		    		"SELECT MIN(age), MAX(age), AVG(balance) " +
		    		"FROM AgeGroupLargest " +
		    		"UNION " +
					"SELECT MIN(age), MAX(age), AVG(balance) " +
					"FROM AgeGroupMedium ";
	    String query = "";
	    if (maxOrMin.equals("MAX")) {
	    	query = 
	    		"SELECT * FROM AverageBalances " +
	    		"WHERE balance IN (" +
	    			"SELECT DISTINCT MAX(balance) " +
	    			"FROM AverageBalances" +
	    		")";	
	    }
	    else if (maxOrMin.equals("MIN")) {
	    	query = 
	    		"SELECT * FROM AverageBalances " +
	    		"WHERE balance IN (" +
	    			"SELECT DISTINCT MIN(balance) " +
	    			"FROM AverageBalances" +
	    		")";	
	    }

		Statement statement = null;
		try {
	    	statement = connection.createStatement();
	    	statement.executeUpdate(view1);
	    	statement.executeUpdate(view2);
	    	statement.executeUpdate(view3);
	    	statement.executeUpdate(view4);
	        ResultSet resultSet = statement.executeQuery(query);
	        while (resultSet.next()) {
	        	System.out.println("You wanted to query for the group with the " + maxOrMin + " Farecard Average.\nThe youngest person in the age group is " + resultSet.getInt(1) + " and the oldest person is " + resultSet.getInt(2) + ". This group has an average fare card balance of " + resultSet.getFloat(3) + "$"); //+ resultSet.getInt("Balance"));
	        	float money = (float) Math.round(resultSet.getFloat(3)*100)/100;
	        			// Math.round(resultSet.getFloat(3)*100)/100;

	        	String output = "You wanted to query for the group with the " + maxOrMin + " Farecard Average.\nThe youngest person in the age group is " + resultSet.getInt(1) + " and the oldest person is " + resultSet.getInt(2) + ".\nThis group has an average fare card balance of " + "$" + money; 
	        	/*
	        	output[0] = Integer.toString(resultSet.getInt(1));
	        	output[1] = Integer.toString(resultSet.getInt(2));
	        	output[2] = Float.toString(resultSet.getFloat(3));
	        	*/
	        	statement.executeUpdate("DROP VIEW AgeGroupSmallest");
		    	statement.executeUpdate("DROP VIEW AgeGroupLargest");
		    	statement.executeUpdate("DROP VIEW AgeGroupMedium");
		    	statement.executeUpdate("DROP VIEW AverageBalances");
	        	return output;
	        }
	        statement.executeUpdate("DROP VIEW AgeGroupSmallest");
	    	statement.executeUpdate("DROP VIEW AgeGroupLargest");
	    	statement.executeUpdate("DROP VIEW AgeGroupMedium");
	    	statement.executeUpdate("DROP VIEW AverageBalances");
	        
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	return "ERROR: Invalid request";
	    } finally {
			System.out.println("\n");
		}
		return "ERROR: Invalid request";
	}
	
	public static ArrayList<String> getRouteHasStop(){
		ArrayList<String> outputList = new ArrayList<String>();
		String fetchQuery =
				"SELECT RouteNumber, StopNumber " + 
				"FROM RouteHasStop";
		
		Statement statement = null;
		try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	String output = "The route number " + resultSet.getString(1) + " connects with stop number " + resultSet.getInt(2);
	        	outputList.add(output);
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	ArrayList<String> errorList = new ArrayList<String>();
	    	errorList.add("ERROR: Invalid request");
	    } finally {
			System.out.println("\n");
		}
		return outputList;
	}
	
	public static ArrayList<String> getAddressesRoutesPassesBy(){
		System.out.println("\n");
		System.out.println("Displaying all addresses each route passes by");
		ArrayList<String> outputList = new ArrayList<String>();
		String fetchQuery =
				"SELECT Route.RouteName, Route.RouteNumber, StopHas.StreetNumber, StopHas.StreetName, StopHas.City, StopHas.StopNumber " +
				"FROM Route " +
				"INNER JOIN RouteHasStop ON Route.RouteNumber=RouteHasStop.RouteNumber " + 
				"INNER JOIN StopHas ON StopHas.StopNumber=RouteHasStop.StopNumber";
		
		Statement statement = null;
		try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	System.out.println("Route Name: " + resultSet.getString("RouteName") + " Street Number: " + resultSet.getInt("StreetNumber") + " Street Name: " + resultSet.getString("StreetName") + " City: " + resultSet.getString("City"));
	        	String output = "The route " + resultSet.getString("RouteName") + " " + resultSet.getInt("RouteNumber") + " goes by " + resultSet.getInt("StreetNumber") + " " + resultSet.getString("StreetName") + ", " + resultSet.getString("City") + ". Stop Number: " + resultSet.getInt("StopNumber");
	        	outputList.add(output);
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	ArrayList<String> errorList = new ArrayList<String>();
	    	errorList.add("ERROR: Invalid request");
	    } finally {
			System.out.println("\n");
		}
		return outputList;
	}
	
	public static String youngestOldest(String ageBound) {
		System.out.println("\n");
		System.out.println("Displaying youngest or oldest age");
		String output = "";
		String fetchQuery = "";
		if (ageBound.equals("MAX")) {
			fetchQuery =
					"SELECT MAX(Age) AS " +
					"OldestUser " +
					"FROM Users";
		}
		else if (ageBound.equals("MIN")) {
			fetchQuery =
					"SELECT MIN(Age) AS " +
					"YoungestUser " +
					"FROM Users";
		} else {
			return "ERROR: Must properly specify if a MAX or MIN";
		}
		Statement statement = null;
		try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	if (ageBound.equals("MAX")) {
	        		return "The maximum age user is " + resultSet.getInt(1) + " years old";
	        	}
	        	else if (ageBound.equals("MIN")) {
	        		return "The minimum age user is " + resultSet.getInt(1) + " years old";
	        	}
	        	return "The " + ageBound + " age user is " + resultSet.getInt(1) + " years old";
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    	return "ERROR: Invalid input";
	    } finally {
			System.out.println("\n");
		}
		return "ERROR: Invalid input";
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	
	/*
	public static void isVehicleInRoute(Connection connection){
		System.out.println("\n");
		System.out.println("Vehicle Number to check: ");
		
		Scanner sc = new Scanner(System.in);
		int vehicleNumber = sc.nextInt();
		sc.nextLine();
		System.out.println("Route Number to check: ");
		int routeNumber = sc.nextInt();
		sc.nextLine();
		
		String fetchQuery =
				"SELECT VehicleNumber" +
				"FROM Vehicle" +
				"WHERE RouteNumber = " + routeNumber;
		
		int vehicleNumberCheck = -1;
		
		Statement statement = null;
		try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	vehicleNumberCheck = resultSet.getInt("VehicleNumber");
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    } finally {
			System.out.println("\n");
		}
		if (vehicleNumberCheck == vehicleNumber) {
			System.out.println("Vehicle Number " + vehicleNumber + " takes Route Number " + routeNumber);
		}
		else {
			System.out.println("Vehicle Number " + vehicleNumber + " does not take Route Number " + routeNumber);
		}
	}
	
	public static void displayUsersTaps(Connection connection){
		System.out.println("\n");
		System.out.println("Enter a user ID to display their tap info from: ");
		
		Scanner sc = new Scanner(System.in);
		int userID = sc.nextInt();
	    sc.nextLine();
		
		String fetchQuery =
				"SELECT Users.Name, Users.UserID, Tap.TimeTap, Tap.CardNumber, Tap.StopNumber " +
				"FROM FareCardHas "
				+ "INNER JOIN Tap ON FareCardHas.CardNumber = Tap.CardNumber"
				+ "WHERE FareCardHas.UserID = " + userID;
		
		Statement statement = null;
		try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	System.out.println("User's Name: " + resultSet.getString("Name") + " User's ID: " + resultSet.getInt("UserID") + " TimeTap: " + resultSet.getDate("TimeTap") + " Card Number: " + resultSet.getInt("CardNumber") + " Stop Number: " + resultSet.getInt("StopNumber"));
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    } finally {
			System.out.println("\n");
		}
	}
	
	public static void displayUserTaps(Connection connection){
		System.out.println("\n");
		System.out.println("Displaying all users");
		
		String fetchQuery =
				"SELECT * " +
				"FROM Users";
		
		Statement statement = null;
		try {
	    	statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(fetchQuery);
	        while (resultSet.next()) {
	        	System.out.println("User's ID: " + resultSet.getInt("UserID") + " User's Name: " + resultSet.getInt("Name") + " Age: " + resultSet.getInt("Age");
	        }
	    } catch (SQLException e ) {
	    	System.out.println("SQL Exception");
	    	e.printStackTrace();
	    } finally {
			System.out.println("\n");
		}
	}
	*/
	
	public static void printMenu() {
		System.out.println("Transit Database Menu. Type a number: ");
		System.out.println("1. Fetch a Info of a Particular User");
		System.out.println("2. Insert a User");
		System.out.println("3. Fetch all FareCards Info");
		System.out.println("4. Insert a FareCard");
		System.out.println("5. Fetch a Tap Info of a FareCard");
		System.out.println("6. Insert a Tap Info of a FareCard");
		System.out.println("7.  Check if a vehicle is in a particular route");
		System.out.println("8.  Check if a stop is in a particular route");
		System.out.println("9.  List close stops near current location");
		System.out.println("10.  Get a route of a particular bus/train");
		System.out.println("11.  Modify user information");
		System.out.println("12.  View user's Fare Cards' information");
		System.out.println("13.  Add balance to a Fare Card");
		System.out.println("14.  Delete transit information");
		System.out.println("15.  Delete stop information");
		System.out.println("16.  Delete user information");
		System.out.println("17.  Add transit information");
		System.out.println("18.  Add a stop");
		System.out.println("19.  Transfer/copy Route between two buses");
		System.out.println("20.  Modify stop information");
		System.out.println("21.  Fetch all transit(vehicle) information");
		System.out.println("22.  Fetch all user information");
		System.out.println("23.  Insert a Route");
		System.out.println("24.  Fetch all Stops");
		System.out.println("25.  Stops that have never been visited by a user");
		System.out.println("26.  Check if a user was at a particular stop between a given time period");
		System.out.println("27.  Ask whether a user was on a particular vehicle");
		System.out.println("28.  Delete Info that there's a Stop in a Route");

		System.out.println("100. Quit");
		System.out.println("\nComplex Queries:");
		System.out.println("101. Count the number of stops for a route number");
		System.out.println("102. Get users within a age range");
		System.out.println("103. Get all the addresses passed by in a route");
		System.out.println("104. Richest in age group");
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}