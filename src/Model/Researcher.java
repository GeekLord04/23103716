package Model;

public class Researcher {
    private int researcherId;
    private String name;
    private String email;
    private String phoneNumber;
    private String specialization;

    public Researcher() {}

    public Researcher(String name, String email, String phoneNumber, String specialization) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
    }

    public int getResearcherId() {
        return researcherId;
    }

    public void setResearcherId(int researcherId) {
        this.researcherId = researcherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Researcher ID: " + researcherId + ", Name: " + name +
                ", Email: " + email + ", Phone Number: " + phoneNumber +
                ", Specialization: " + specialization;
    }
}

