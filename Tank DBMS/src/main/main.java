package main;
import java.sql.*;
import java.util.Scanner;


public class main {
	
	
	public static void boundary() {
		
		
		System.out.println("\n" +"----------------");
	}
	
	
	public static void establish_connection() {
	
	   Connection conn = null;
       try {
           Class.forName("org.sqlite.JDBC");
           conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
           System.out.println("Opened database successfully");
       } catch (ClassNotFoundException | SQLException e) {
           System.err.println("Problem encountered: " + e.getMessage());
       } finally {
           try {
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException ex) {
               System.err.println("Error closing connection: " + ex.getMessage());
           }
       }
	}
	
	
	
	public static void Query1() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement()) {
	        String sql = "SELECT COUNT (*) AS count " +
	                "FROM Tank " +
	                "WHERE generation = 1 AND (num_crew==4) " +
	                "UNION " +
	                "SELECT COUNT (*) AS count " +
	                "FROM Tank " +
	                "WHERE generation = 1 AND (num_crew>2)";
	        ResultSet rs = stmt.executeQuery(sql);
	        int i = 0;
	        System.out.println("\n" + "Query 1"); 
	        System.out.println ("Number of Generation 1 tanks that have crew of exactly 4 members, at least 2 members:");
	        while (rs.next()) {
	            int count = rs.getInt("count");
	            System.out.println("Count for query part " + (++i) + ": " + count);
	        }
	        rs.close();
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
	
	public static void Query2() {
	    int count = 0;
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count " +
	                                          "FROM tank " +
	                                          "WHERE fyear >= 1950 AND fyear <= 1995 AND lyear >= 2023")) {
	        if (rs.next()) {
	            count = rs.getInt("count");
	            System.out.println("\n" + "Query 2");
	            System.out.println("Number of tanks built between year 1950 and year 1995 that are still in service: " + count);
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
	public static void Query3() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT name " +
	                                          "FROM tank " +
	                                          "WHERE country = 'USA' AND (engine LIKE '%Continental%' OR engine LIKE '%Honeywell%') AND lyear < 2023 " +
	                                          "ORDER BY name asc"))
	    {
	    	System.out.println("\n" + "Query 3");
	        System.out.println("List of all tanks made in USA that use Continental or Honeywell Engine that are retired in ascending order: \n");
	        while (rs.next()) {
	            String name = rs.getString("name");
	            System.out.println(name);
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
	public static void Query4() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count " +
	                                          "FROM tank " +
	                                          "WHERE war_id=4 AND lyear>=2023")) {
	        if (rs.next()) {
	            int count = rs.getInt("count");
	            System.out.println("\n" + "Query 4");
	            System.out.println("How many tanks were used in Vietnam war that are still in operation/service: " + count);
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
	public static void Query5() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT name, fuel_cap " +
	                                          "FROM tank " +
	                                          "WHERE fuel_cap >=120 AND fuel_cap <=240 AND lyear<2023 " +
	                                          "ORDER BY name ASC")) {
	    	System.out.println("\n" + "Query 5");
	        System.out.println("All tanks that are no longer in service with a fuel capacity between 120 and 240:");
	        while (rs.next()) {
	            String name = rs.getString("name");
	            int fuelCap = rs.getInt("fuel_cap");
	            System.out.println(name + "'s "  + "Fuel Capacity = "  + fuelCap);
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
	public static void Query6() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT name, mass, length " +
	                                          "FROM tank " +
	                                          "WHERE mass>=50 AND length>=9.5 AND country = 'USA' " +
	                                          "ORDER BY name ASC")) {
	    	System.out.println("\n" + "Query 6");
	    	System.out.println("All tanks that have a mass of at least 50, length of at least 9.5  and are made in the Country USA");
	        System.out.println("Format: Name, Mass, Length");
	        while (rs.next()) {
	            String name = rs.getString("name");
	            double mass = rs.getDouble("mass");
	            double length = rs.getDouble("length");
	            System.out.println(name + ", " + mass + ", " + length);
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}
	
	public static void Query7() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT AVG(service_year) AS average " +
	                                          "FROM tank " +
	                                          "WHERE country = 'USA'")) {
	    	System.out.println("\n" + "Query 7");
	        if (rs.next()) {
	            double average = rs.getDouble("average");
	            System.out.println("Average age of tanks made in Country USA: " + average);
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
	public static void Query8() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT name, mass FROM tank " +
	                                           "WHERE fyear <= 1990 AND fyear >= 1960 AND country = 'USA' " +
	                                           "ORDER BY mass DESC " +
	                                           "LIMIT 3")) {
	    	System.out.println("\n" + "Query 8");
	        System.out.println("The top 3 heaviest tanks made in the USA between 1960 and 1990:");
	        while (rs.next()) {
	            String name = rs.getString("name");
	            int mass = rs.getInt("mass");
	            System.out.println(name + " (mass: " + mass + ")");
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

		
	public static void Query9() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT name, COUNT(*) AS count " +
	                                           "FROM tank " +
	                                           "WHERE fyear = 1959 AND war_id = 2 " +
	                                           "GROUP BY name")) {
	    	System.out.println("\n" + "Query 9");
	        System.out.println("Total number of tanks produced in the year 1959 that were used in WW2:");
	        while (rs.next()) {
	            String name = rs.getString("name");
	            int count = rs.getInt("count");
	            System.out.println(name + " (count= " + count + ")");
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
	public static void Query10() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT type, COUNT(DISTINCT name) AS count " +
	                                           "FROM tank " +
	                                           "WHERE country = 'Germany' AND lyear < 2023 " +
	                                           "GROUP BY type " +
	                                           "UNION " +
	                                           "SELECT 'Total', SUM(subquery.count) AS count " +
	                                           "FROM (" +
	                                           "  SELECT COUNT(DISTINCT name) AS count " +
	                                           "  FROM tank " +
	                                           "  WHERE country = 'Germany' AND lyear < 2023 " +
	                                           "  GROUP BY type" +
	                                           ") AS subquery")) {
	    	System.out.println("\n" + "Query 10");
	        System.out.println("How many different types of tanks does Germany have there are not in service, listed by type:");
	        while (rs.next()) {
	            String type = rs.getString("type");
	            int count = rs.getInt("count");
	            System.out.println(type + " (count= " + count + ")");
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
		
	public static void Query11() {
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab4.db");
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT generation, ROUND(AVG(fuel_cap), 2) AS fuel_avg " +
	                                            "FROM tank " +
	                                            "GROUP BY generation")) {
	    	System.out.println("\n" + "Query 11");
	        System.out.println("Average fuel consumption of tanks for each tank generation:");
	        while (rs.next()) {
	            String generation = rs.getString("generation");
	            double fuelAvg = rs.getDouble("fuel_avg");
	            System.out.println("Generation " + generation + ": " + fuelAvg);
	        }
	    } catch (SQLException e) {
	        System.err.println("Problem encountered: " + e.getMessage());
	    }
	}

	
	
    public static void main(String[] args) {
    	
    	establish_connection() ;
    	 System.out.println("\n" + "Welcome to the Tank DBMS:\n "); 
    	 
    	 System.out.println("Options: "); 
    	 System.out.println("1 = Number of Generation 1 tanks that have crew of exactly 4 members, at least 2 members "); 
    	 System.out.println("2 = Number of tanks built between year 1950 and year 1995 that are still in service: "); 
    	 System.out.println("3 = List of all tanks made in USA that use Continental or Honeywell Engine that are retired in ascending order: ");
    	 System.out.println("4 = How many tanks were used in Vietnam war that are still in operation/service: ");
    	 System.out.println("5 = All tanks that are no longer in service with a fuel capacity between 120 and 240: ");
    	 System.out.println("6 = All tanks that have a mass of at least 50, length of at least 9.5  and are made in the Country USA ");
    	 System.out.println("7 = Average age of tanks made in Country USA: ");
    	 System.out.println("8 = Which are the top three heaviest tanks made between the years 1960 and 1990 in the USA: ");
    	 System.out.println("9 = Total amount of tanks produced in the year 1959 that were used in WW2? ");
    	 System.out.println("10 = How many different types of tanks does Germany have there are not in service, list the types: ");
    	 System.out.println("11 = What is the average fuel consumption of tanks from each tank generation: ");
    	 System.out.println("12 = See all Queries at once ");

    
    	 Scanner scanner = new Scanner(System.in);
    	 String input = "";

    	 while (!input.equalsIgnoreCase("q")) {
    	     System.out.print("\n");
    	     System.out.print( "Please Enter a number between 1 and 12 here (enter q to quit): ");
        	
    	     input = scanner.nextLine();

    	     if (input.equalsIgnoreCase("q")) {
    	    	 System.out.print("q pressed: Program Exited");
    	         break;
    	     }

    	     try {
    	         int number = Integer.parseInt(input);
    	       
        
        switch (number) {
        case 1:
            Query1();
            boundary();
            break;
        case 2:
            Query2();
            boundary();
            break;
        case 3:
            Query3();
            boundary();
            break;
        case 4:
            Query4();
            boundary();
            break;
        case 5:
            Query5();
            boundary();
            break;
        case 6:
            Query6();
            boundary();
            break;
        case 7:
            Query7();
            boundary();
            break;
        case 8:
            Query8();
            boundary();
            break;
        case 9:
            Query9();
            boundary();
            break;
        case 10:
            Query10();
            boundary();
            break;
        case 11:
            Query11();
            boundary();
            break;
        case 12:
        	boundary();
        	System.out.print("\n"+ "Start: Printing all Queries");
        	System.out.print("\n");
        	boundary();
        	Query1();
        	boundary();
        	Query2();
        	boundary();
        	Query3();
        	boundary();
        	Query4();
        	boundary();
        	Query5();
        	boundary();
        	Query6();
        	boundary();
        	Query7();
        	boundary();
        	Query8();
        	boundary();
        	Query9();
        	boundary();
        	Query10();
        	boundary();
        	Query11();
        	boundary();
        	System.out.print("\n"+ "End of all Queries");
        	System.out.print("\n");
        	boundary();
        	break;
        default:
            System.out.println("Invalid input!");
        }
    	     } catch (NumberFormatException e) {
    	         System.out.println("Invalid input: Please enter a number between 1-11 or use 'q' quit.");
    	     }
    	 }

    	 scanner.close();
    }}


/*Query1();
Query2();
Query3();
Query4();
Query5();
Query6();
Query7();
Query8();
Query9();
Query10();
Query11();
*/


