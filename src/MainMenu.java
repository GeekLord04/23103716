import DAO.ExperimentDAO;
import DAO.ResearcherDAO;
import DAO.SampleDAO;
import Model.Experiment;
import Model.Researcher;
import Model.Sample;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExperimentDAO experimentDAO = new ExperimentDAO();
        SampleDAO sampleDAO = new SampleDAO();
        ResearcherDAO researcherDAO = new ResearcherDAO();

        while (true) {
            System.out.println("\nResearch Lab Management System");
            System.out.println("1. Manage Experiments");
            System.out.println("2. Manage Samples");
            System.out.println("3. Manage Researchers");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manageExperiments(scanner, experimentDAO);
                    break;
                case 2:
                    manageSamples(scanner, sampleDAO);
                    break;
                case 3:
                    manageResearchers(scanner, researcherDAO);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageExperiments(Scanner scanner, ExperimentDAO experimentDAO) {
        System.out.println("1. Add Experiment");
        System.out.println("2. View Experiments");
        System.out.println("3. Update Experiment");
        System.out.println("4. Delete Experiment");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter Experiment Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Description: ");
                String description = scanner.nextLine();
                System.out.print("Enter Start Date (YYYY-MM-DD): ");
                Date startDate = Date.valueOf(scanner.nextLine());
                System.out.print("Enter End Date (YYYY-MM-DD): ");
                Date endDate = Date.valueOf(scanner.nextLine());

                Experiment experiment = new Experiment(name, description, startDate, endDate);
                experimentDAO.addExperiment(experiment);
                break;
            case 2:
                List<Experiment> exp = experimentDAO.getAllExperiments();
                if (!exp.isEmpty()) {
                    for (int i = 0; i < exp.size(); i++) {
                        System.out.println(exp.get(i));
                    }
                } else {
                    System.out.println("No Experiments found");
                }
                break;
            case 3:
                System.out.print("Enter Experiment ID to update: ");
                int updateId = scanner.nextInt();
                scanner.nextLine();

                Experiment experimentToUpdate = experimentDAO.getExperiment(updateId);
                if (experimentToUpdate != null) {
                    System.out.print("Enter New Name (or press Enter to skip): ");
                    String newName = scanner.nextLine();
                    if (!newName.isEmpty()) experimentToUpdate.setName(newName);

                    System.out.print("Enter New Description (or press Enter to skip): ");
                    String newDescription = scanner.nextLine();
                    if (!newDescription.isEmpty()) experimentToUpdate.setDescription(newDescription);

                    System.out.print("Enter New Start Date (or press Enter to skip): ");
                    String newStartDate = scanner.nextLine();
                    if (!newStartDate.isEmpty()) experimentToUpdate.setStartDate(Date.valueOf(newStartDate));

                    System.out.print("Enter New End Date (or press Enter to skip): ");
                    String newEndDate = scanner.nextLine();
                    if (!newEndDate.isEmpty()) experimentToUpdate.setEndDate(Date.valueOf(newEndDate));

                    experimentDAO.updateExperiment(experimentToUpdate);
                } else {
                    System.out.println("Experiment not found.");
                }
                break;
            case 4:
                System.out.print("Enter Experiment ID to delete: ");
                int deleteId = scanner.nextInt();
                experimentDAO.deleteExperiment(deleteId);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void manageSamples(Scanner scanner, SampleDAO sampleDAO) {
        System.out.println("1. Add Sample");
        System.out.println("2. View Samples");
        System.out.println("3. Update Sample");
        System.out.println("4. Delete Sample");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter Experiment ID: ");
                int experimentId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Sample Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Sample Type: ");
                String type = scanner.nextLine();
                System.out.print("Enter Quantity: ");
                int quantity = scanner.nextInt();

                Sample sample = new Sample(experimentId, name, type, quantity);
                sampleDAO.addSample(sample);
                break;
            case 2:
                List<Sample> s = sampleDAO.getAllSamples();
                if (!s.isEmpty()) {
                    for (int i = 0; i < s.size(); i++) {
                        System.out.println(s.get(i));
                    }
                } else {
                    System.out.println("No Sample found");
                }
                break;
            case 3:
                System.out.print("Enter Sample ID to update: ");
                int updateId = scanner.nextInt();
                scanner.nextLine();

                Sample sampleToUpdate = sampleDAO.getSample(updateId);
                if (sampleToUpdate != null) {
                    System.out.print("Enter New Name (or press Enter to skip): ");
                    String newName = scanner.nextLine();
                    if (!newName.isEmpty()) sampleToUpdate.setName(newName);

                    System.out.print("Enter New Type (or press Enter to skip): ");
                    String newType = scanner.nextLine();
                    if (!newType.isEmpty()) sampleToUpdate.setType(newType);

                    System.out.print("Enter New Quantity (or press Enter to skip): ");
                    String newQuantity = scanner.nextLine();
                    if (!newQuantity.isEmpty()) sampleToUpdate.setQuantity(Integer.parseInt(newQuantity));

                    sampleDAO.updateSample(sampleToUpdate);
                } else {
                    System.out.println("Sample not found.");
                }
                break;
            case 4:
                System.out.print("Enter Sample ID to delete: ");
                int deleteId = scanner.nextInt();
                sampleDAO.deleteSample(deleteId);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void manageResearchers(Scanner scanner, ResearcherDAO researcherDAO) {
        System.out.println("1. Add Researcher");
        System.out.println("2. View Researchers");
        System.out.println("3. Update Researcher");
        System.out.println("4. Delete Researcher");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter Researcher Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();
                System.out.print("Enter Phone Number: ");
                String phoneNumber = scanner.nextLine();
                System.out.print("Enter Specialization: ");
                String specialization = scanner.nextLine();

                Researcher newResearcher = new Researcher(name, email, phoneNumber, specialization);
                researcherDAO.addResearcher(newResearcher);
                break;
            case 2:
                List<Researcher> researcher = researcherDAO.getAllResearchers();
                if (!researcher.isEmpty()) {
                    for (int i = 0; i < researcher.size(); i++) {
                        System.out.println(researcher.get(i));
                    }
                } else {
                    System.out.println("No Researcher found");
                }
                break;
            case 3:
                System.out.print("Enter Researcher ID to update: ");
                int updateId = scanner.nextInt();
                scanner.nextLine();

                Researcher researcherToUpdate = researcherDAO.getResearcher(updateId);
                if (researcherToUpdate != null) {
                    System.out.print("Enter New Name (or press Enter to skip): ");
                    String newName = scanner.nextLine();
                    if (!newName.isEmpty()) researcherToUpdate.setName(newName);

                    System.out.print("Enter New Email (or press Enter to skip): ");
                    String newEmail = scanner.nextLine();
                    if (!newEmail.isEmpty()) researcherToUpdate.setEmail(newEmail);

                    System.out.print("Enter New Phone Number (or press Enter to skip): ");
                    String newPhoneNumber = scanner.nextLine();
                    if (!newPhoneNumber.isEmpty()) researcherToUpdate.setPhoneNumber(newPhoneNumber);

                    System.out.print("Enter New Specialization (or press Enter to skip): ");
                    String newSpecialization = scanner.nextLine();
                    if (!newSpecialization.isEmpty()) researcherToUpdate.setSpecialization(newSpecialization);

                    researcherDAO.updateResearcher(researcherToUpdate);
                } else {
                    System.out.println("Researcher not found.");
                }
                break;
            case 4:
                System.out.print("Enter Researcher ID to delete: ");
                int deleteId = scanner.nextInt();
                researcherDAO.deleteResearcher(deleteId);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}

