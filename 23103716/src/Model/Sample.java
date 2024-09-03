package Model;

public class Sample {
    private int sampleId;
    private int experimentId;
    private String name;
    private String type;
    private int quantity;

    public Sample() {}

    public Sample(int experimentId, String name, String type, int quantity) {
        this.experimentId = experimentId;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }

    public int getSampleId() {
        return sampleId;
    }

    public void setSampleId(int sampleId) {
        this.sampleId = sampleId;
    }

    public int getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(int experimentId) {
        this.experimentId = experimentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Sample ID: " + sampleId + ", Experiment ID: " + experimentId +
                ", Name: " + name + ", Type: " + type + ", Quantity: " + quantity;
    }
}

