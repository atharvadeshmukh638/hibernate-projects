package com.hibernate_Hospital;
import java.util.List;
import java.util.Scanner;

public class MainApp {
	public static void main(String[] args) {
		PatientDAO dao = new PatientDAO();
		Scanner scanner = new Scanner(System.in);
		int choice = 0;

		System.out.println("======================================");
		System.out.println("   Patient MANAGEMENT SYSTEM   ");
		System.out.println("======================================");

		do {
			System.out.println("\n========== MENU ==========");
			System.out.println("1.  Insert a new Patient");
			System.out.println("2.  Fetch All");
			System.out.println("3.  Fetch by ID");
			System.out.println("4.  Update BillAmount (by ID)");
			System.out.println("5.  Delete by ID");
			System.out.println("6.  Insert 10 Records (Batch)");
			System.out.println("7.  Update Ward (by ID)");
			System.out.println("8.  Delete 'General' Records (HQL)");
			System.out.println("9.  Fetch Native - BillAmount > 50000");
			System.out.println("10. Fetch Native - Desc Order");
			System.out.println("11. Fetch Native - Highest BillAmount");
			System.out.println("12. Fetch Native - Total BillAmount Sum");
			System.out.println("13. Fetch Native - Ward Wise Count");
			System.out.println("14. Fetch Native - BillAmount Between 40000 and 80000");
			System.out.println("15. Fetch HQL - Name starting with 'A'");
			System.out.println("16. Fetch HQL - BillAmount > Average");
			System.out.println("17. Fetch HQL - Second Highest BillAmount");
			System.out.println("18. Fetch HQL - Ward IN ('ICU', 'General', 'OPD')");
			System.out.println("19. Fetch HQL - Ward Wise Average");
			System.out.println("20. Bulk Update HQL - Increase 'ICU' BillAmount by 10%");
			System.out.println("21. Exit");
			System.out.print("Enter your choice (1-21): ");

			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
				continue;
			}

			switch (choice) {
			case 1:
				System.out.print("Enter Name: ");
				String f1 = scanner.nextLine();
				System.out.print("Enter Ward: ");
				String f2 = scanner.nextLine();
				System.out.print("Enter BillAmount: ");
				double pVal = Double.parseDouble(scanner.nextLine());
				Patient newObj = new Patient(f1, f2, pVal);
				dao.createPatient(newObj);
				System.out.println("Inserted successfully!");
				break;
			case 2:
				List<Patient> all = dao.fetchAll();
				if (all.isEmpty())
					System.out.println("No records found.");
				for (Patient e : all)
					System.out.println(e);
				break;
			case 3:
				System.out.print("Enter ID: ");
				int id3 = Integer.parseInt(scanner.nextLine());
				Patient fetch3 = dao.fetchById(id3);
				System.out.println(fetch3 != null ? fetch3 : "Not found.");
				break;
			case 4:
				System.out.print("Enter ID: ");
				int id4 = Integer.parseInt(scanner.nextLine());
				System.out.print("Enter new BillAmount: ");
				double val4 = Double.parseDouble(scanner.nextLine());
				dao.updateBillAmount(id4, val4);
				System.out.println("Update execution completed.");
				break;
			case 5:
				System.out.print("Enter ID: ");
				int id5 = Integer.parseInt(scanner.nextLine());
				dao.delete(id5);
				System.out.println("Delete execution completed.");
				break;
			case 6:
				dao.insertTenRecords();
				System.out.println("10 Records Inserted successfully.");
				break;
			case 7:
				System.out.print("Enter ID to update: ");
				int id7 = Integer.parseInt(scanner.nextLine());
				Patient obj7 = dao.fetchById(id7);
				if (obj7 != null) {
					System.out.print("Enter new Ward: ");
					obj7.setWard(scanner.nextLine());
					dao.updateWard(obj7);
					System.out.println("Group updated.");
				} else {
					System.out.println("ID not found.");
				}
				break;
			case 8:
				dao.deleteGeneralRecords();
				System.out.println("Deleted all 'General' records.");
				break;
			case 9:
				for (Patient e : dao.fetchNativeGreaterThan50k())
					System.out.println(e);
				break;
			case 10:
				for (Patient e : dao.fetchNativeDescOrder())
					System.out.println(e);
				break;
			case 11:
				System.out.println("Highest BillAmount: " + dao.fetchNativeHighestValue());
				break;
			case 12:
				System.out.println("Total Sum: " + dao.fetchNativeTotalSum());
				break;
			case 13:
				for (Object[] row : dao.fetchNativeGroupWiseCount()) {
					System.out.println("Ward: " + row[0] + " | Count: " + row[1]);
				}
				break;
			case 14:
				for (Patient e : dao.fetchNativeValueBetween())
					System.out.println(e);
				break;
			case 15:
				for (Patient e : dao.fetchHqlNamesStartingWithA())
					System.out.println(e);
				break;
			case 16:
				for (Patient e : dao.fetchHqlGreaterThanAvg())
					System.out.println(e);
				break;
			case 17:
				System.out.println("Second Highest: " + dao.fetchHqlSecondHighest());
				break;
			case 18:
				for (Patient e : dao.fetchHqlInGroups())
					System.out.println(e);
				break;
			case 19:
				for (Object[] row : dao.fetchHqlGroupWiseAvg()) {
					System.out.println("Ward: " + row[0] + " | Avg BillAmount: " + row[1]);
				}
				break;
			case 20:
				dao.bulkUpdateSpecificGroup();
				System.out.println("Bulk updated 'ICU' records.");
				break;
			case 21:
				System.out.println("Exiting the application... Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		} while (choice != 21);

		scanner.close();
		HibernateUtil.shutdown();
	}
}
