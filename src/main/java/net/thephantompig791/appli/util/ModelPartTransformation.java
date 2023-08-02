package net.thephantompig791.appli.util;

public class ModelPartTransformation {
    private String modelPart, type;
    private Float value;

    public ModelPartTransformation(String modelPart, String type, Float value) {
        this.modelPart = modelPart;
        this.type = type;
        this.value = value;
    }

    public String getModelPart() {
        return this.modelPart;
    }
    public String getType() {
        return this.type;
    }
    public Float getValue() {
        return this.value;
    }
}
