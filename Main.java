import java.util.ArrayList;
import java.util.Scanner;

// Main class: runs the CampusFix application.
public class Main {

    private static final Scanner input = new Scanner(System.in);

    // ArrayList stores the reports while the program is running.
    private static final ArrayList<Report> reports = new ArrayList<>();

    // Arrays demonstrate the use of arrays and keep choices organized.
    private static final String[] ISSUE_CATEGORIES = {
        "Electrical", "Plumbing", "Cleanliness", "Other"
    };

    private static final String[] REPORT_STATUSES = {
        "Pending", "In Progress", "Resolved"
    };

    private static int nextId = 1;

    // Demo credentials. Change these before presenting or sharing the project.
    private static final String ADMIN_USERNAME = "Trixie";
    private static final String ADMIN_PASSWORD = "haynako123";

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("          CAMPUSFIX SYSTEM");
        System.out.println("======================================");

        int choice;

        do {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Reporter");
            System.out.println("2. Admin");
            System.out.println("3. Exit");

            choice = readInt("Choose your role: ");

            switch (choice) {
                case 1:
                    // Polymorphism: a User reference points to a Reporter object.
                    User reporterUser = new Reporter("Campus Reporter");
                    reporterUser.showWelcome();
                    reporterUser.openMenu();
                    break;

                case 2:
                    Admin adminUser = adminLogin();

                    if (adminUser != null) {
                        // Polymorphism: the same User type can refer to an Admin.
                        User loggedInUser = adminUser;
                        loggedInUser.showWelcome();
                        loggedInUser.openMenu();
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using CampusFix!");
                    break;

                default:
                    System.out.println("Invalid choice. Select 1, 2, or 3.");
            }

        } while (choice != 3);

        input.close();
    }

    // =========================================================
    // ABSTRACTION: User is an abstract parent class.
    // INHERITANCE: Reporter and Admin inherit from User.
    // ENCAPSULATION: User data is private and accessed by methods.
    // =========================================================
    static abstract class User {
        private String displayName;

        public User(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            if (displayName != null && !displayName.trim().isEmpty()) {
                this.displayName = displayName.trim();
            }
        }

        public abstract void showWelcome();

        public abstract void openMenu();
    }

    // Reporter is a child class of User.
    static class Reporter extends User {

        public Reporter(String displayName) {
            super(displayName);
        }

        // POLYMORPHISM: overrides the abstract method in User.
        @Override
        public void showWelcome() {
            System.out.println("\nWelcome, " + getDisplayName() + "!");
            System.out.println("You can submit an issue or check its status.");
        }

        @Override
        public void openMenu() {
            reporterMenu();
        }
    }

    // Admin is another child class of User.
    static class Admin extends User {

        public Admin(String displayName) {
            super(displayName);
        }

        // POLYMORPHISM: Admin provides its own implementation.
        @Override
        public void showWelcome() {
            System.out.println("\nWelcome, " + getDisplayName() + "!");
            System.out.println("You have access to the Admin Dashboard.");
        }

        @Override
        public void openMenu() {
            adminMenu();
        }
    }

    // =========================================================
    // ENCAPSULATION: Report fields are private.
    // CONSTRUCTOR: Initializes each new report.
    // =========================================================
    static class Report {
        private int id;
        private String reporter;
        private String location;
        private String category;
        private String description;
        private String status;

        public Report(int id, String reporter, String location,
                      String category, String description) {
            this.id = id;
            this.reporter = reporter;
            this.location = location;
            this.category = category;
            this.description = description;
            this.status = REPORT_STATUSES[0]; // Initial status: Pending
        }

        public int getId() {
            return id;
        }

        public String getReporter() {
            return reporter;
        }

        public String getLocation() {
            return location;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            for (String validStatus : REPORT_STATUSES) {
                if (validStatus.equalsIgnoreCase(status)) {
                    this.status = validStatus;
                    return;
                }
            }

            throw new IllegalArgumentException("Invalid report status.");
        }
    }

    // =========================================================
    // REPORTER SECTION
    // =========================================================
    private static void reporterMenu() {
        int choice;

        do {
            System.out.println("\n========== REPORTER MENU ==========");
            System.out.println("1. Submit an Issue Report");
            System.out.println("2. Check Report Status");
            System.out.println("3. Back to Main Menu");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    submitReport();
                    break;
                case 2:
                    checkReportStatus();
                    break;
                case 3:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Select 1, 2, or 3.");
            }
        } while (choice != 3);
    }

    // =========================================================
    // ADMIN LOGIN
    // =========================================================
    private static Admin adminLogin() {
        System.out.println("\n========== ADMIN LOGIN ==========");

        int attempts = 3;

        while (attempts > 0) {
            System.out.print("Username: ");
            String username = input.nextLine().trim();

            System.out.print("Password: ");
            String password = input.nextLine();

            if (username.equals(ADMIN_USERNAME)
                    && password.equals(ADMIN_PASSWORD)) {
                System.out.println("\nLogin successful!");
                return new Admin("Administrator");
            }

            attempts--;
            System.out.println("Incorrect username or password.");
            System.out.println("Attempts remaining: " + attempts);
        }

        System.out.println("Access denied. Returning to main menu.");
        return null;
    }

    // =========================================================
    // ADMIN DASHBOARD
    // =========================================================
    private static void adminMenu() {
        int choice;

        do {
            System.out.println("\n========== ADMIN DASHBOARD ==========");
            System.out.println("1. View All Reports");
            System.out.println("2. Search Report by ID");
            System.out.println("3. Update Report Status");
            System.out.println("4. Delete a Report");
            System.out.println("5. View Report Summary");
            System.out.println("6. Logout");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewReports();
                    break;
                case 2:
                    searchReport();
                    break;
                case 3:
                    updateStatus();
                    break;
                case 4:
                    deleteReport();
                    break;
                case 5:
                    viewSummary();
                    break;
                case 6:
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Select 1 to 6.");
            }
        } while (choice != 6);
    }

    // =========================================================
    // SUBMIT REPORT - REPORTER
    // =========================================================
    private static void submitReport() {
        System.out.println("\n========== SUBMIT ISSUE REPORT ==========");

        String reporter = readText("Reporter name: ");
        String location = readText("Campus location: ");

        System.out.println("\nAvailable Issue Categories:");
        for (int i = 0; i < ISSUE_CATEGORIES.length; i++) {
            System.out.println((i + 1) + ". " + ISSUE_CATEGORIES[i]);
        }

        int categoryChoice;
        do {
            categoryChoice = readInt("Choose issue category: ");

            if (categoryChoice < 1 || categoryChoice > ISSUE_CATEGORIES.length) {
                System.out.println("Invalid category. Choose from 1 to "
                        + ISSUE_CATEGORIES.length + ".");
            }
        } while (categoryChoice < 1 || categoryChoice > ISSUE_CATEGORIES.length);

        String category = ISSUE_CATEGORIES[categoryChoice - 1];
        String description = readText("Issue description: ");

        Report report = new Report(
            nextId++, reporter, location, category, description
        );

        reports.add(report);

        System.out.println("\nReport submitted successfully!");
        System.out.println("Your Report ID is: " + report.getId());
        System.out.println("Status: " + report.getStatus());
        System.out.println("Please save your Report ID.");
    }

    // =========================================================
    // CHECK STATUS - REPORTER
    // =========================================================
    private static void checkReportStatus() {
        System.out.println("\n========== CHECK REPORT STATUS ==========");

        int id = readInt("Enter your Report ID: ");
        Report report = findReport(id);

        if (report == null) {
            System.out.println("Report not found. Check your Report ID.");
            return;
        }

        System.out.println("\nReport ID: " + report.getId());
        System.out.println("Location: " + report.getLocation());
        System.out.println("Category: " + report.getCategory());
        System.out.println("Status: " + report.getStatus());
    }

    // =========================================================
    // VIEW ALL REPORTS - ADMIN
    // =========================================================
    private static void viewReports() {
        System.out.println("\n========== ALL ISSUE REPORTS ==========");

        if (reports.isEmpty()) {
            System.out.println("No reports have been submitted yet.");
            return;
        }

        for (Report report : reports) {
            displayReport(report);
        }

        System.out.println("Total reports: " + reports.size());
    }

    // =========================================================
    // SEARCH REPORT - ADMIN
    // =========================================================
    private static void searchReport() {
        System.out.println("\n========== SEARCH REPORT ==========");

        int id = readInt("Enter Report ID: ");
        Report report = findReport(id);

        if (report == null) {
            System.out.println("Report not found.");
        } else {
            displayReport(report);
        }
    }

    // =========================================================
    // UPDATE STATUS - ADMIN
    // =========================================================
    private static void updateStatus() {
        System.out.println("\n========== UPDATE REPORT STATUS ==========");

        int id = readInt("Enter Report ID: ");
        Report report = findReport(id);

        if (report == null) {
            System.out.println("Report not found.");
            return;
        }

        System.out.println("Current status: " + report.getStatus());
        System.out.println("\nAvailable statuses:");

        for (int i = 0; i < REPORT_STATUSES.length; i++) {
            System.out.println((i + 1) + ". " + REPORT_STATUSES[i]);
        }

        int choice = readInt("Choose new status: ");

        if (choice < 1 || choice > REPORT_STATUSES.length) {
            System.out.println("Invalid status choice.");
            return;
        }

        try {
            report.setStatus(REPORT_STATUSES[choice - 1]);
            System.out.println("Status updated successfully!");
            System.out.println("New status: " + report.getStatus());
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to update status: " + e.getMessage());
        }
    }

    // =========================================================
    // DELETE REPORT - ADMIN
    // =========================================================
    private static void deleteReport() {
        System.out.println("\n========== DELETE REPORT ==========");

        int id = readInt("Enter Report ID to delete: ");
        Report report = findReport(id);

        if (report == null) {
            System.out.println("Report not found.");
            return;
        }

        displayReport(report);

        System.out.print("Confirm deletion? (Y/N): ");
        String confirmation = input.nextLine().trim();

        if (confirmation.equalsIgnoreCase("Y")) {
            reports.remove(report);
            System.out.println("Report deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // =========================================================
    // REPORT SUMMARY - ADMIN
    // =========================================================
    private static void viewSummary() {
        System.out.println("\n========== REPORT SUMMARY ==========");

        int pending = 0;
        int inProgress = 0;
        int resolved = 0;

        for (Report report : reports) {
            if (report.getStatus().equals(REPORT_STATUSES[0])) {
                pending++;
            } else if (report.getStatus().equals(REPORT_STATUSES[1])) {
                inProgress++;
            } else if (report.getStatus().equals(REPORT_STATUSES[2])) {
                resolved++;
            }
        }

        System.out.println("Total reports: " + reports.size());
        System.out.println("Pending: " + pending);
        System.out.println("In Progress: " + inProgress);
        System.out.println("Resolved: " + resolved);
    }

    // =========================================================
    // FIND REPORT BY ID
    // =========================================================
    private static Report findReport(int id) {
        for (Report report : reports) {
            if (report.getId() == id) {
                return report;
            }
        }

        return null;
    }

    // =========================================================
    // DISPLAY REPORT DETAILS
    // =========================================================
    private static void displayReport(Report report) {
        System.out.println("\n--------------------------------------");
        System.out.println("Report ID: " + report.getId());
        System.out.println("Reporter: " + report.getReporter());
        System.out.println("Location: " + report.getLocation());
        System.out.println("Category: " + report.getCategory());
        System.out.println("Description: " + report.getDescription());
        System.out.println("Status: " + report.getStatus());
        System.out.println("--------------------------------------");
    }

    // =========================================================
    // INPUT VALIDATION METHODS
    // =========================================================
    private static String readText(String prompt) {
        String value;

        do {
            System.out.print(prompt);
            value = input.nextLine().trim();

            if (value.isEmpty()) {
                System.out.println("This field cannot be empty.");
            }
        } while (value.isEmpty());

        return value;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // EXCEPTION HANDLING: prevents the program from crashing
                // when the user enters letters or an invalid whole number.
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
}
