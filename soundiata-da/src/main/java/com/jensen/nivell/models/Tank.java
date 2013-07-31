package com.jensen.nivell.models;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Tank implements IDocumentPersisted{

    private String identifier;
    private String name;
    private BigDecimal currentLevel;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal size;

    public Tank(String identifier, String name, BigDecimal size, BigDecimal currentLevel, BigDecimal latitude, BigDecimal longitude) {

        this.identifier = identifier;
        this.name = name;
        this.currentLevel = currentLevel;
        this.latitude = latitude;
        this.longitude = longitude;
        this.size = size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    @Override
    public String getPersistenceKey(){
        return identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(BigDecimal currentLevel) {
        this.currentLevel = currentLevel;
    }

    @DecimalMin(value = "-90.00", message = "Latitude must be greater than -90.00")
    @DecimalMax(value = "90.00", message = "Latitude must be lower than 90.00")
    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @DecimalMin(value = "0.00", message = "Size must be a positive value")
    public BigDecimal getSize() {
        return size;
    }

    @DecimalMin(value = "-180.00", message = "Longitude must be greater than -180.00")
    @DecimalMax(value = "180.00", message = "Longitude must be lower than 180.00")
    public BigDecimal getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tank tank = (Tank) o;

        if (currentLevel != null ? !currentLevel.equals(tank.currentLevel) : tank.currentLevel != null) return false;
        if (identifier != null ? !identifier.equals(tank.identifier) : tank.identifier != null) return false;
        if (latitude != null ? !latitude.equals(tank.latitude) : tank.latitude != null) return false;
        if (longitude != null ? !longitude.equals(tank.longitude) : tank.longitude != null) return false;
        if (name != null ? !name.equals(tank.name) : tank.name != null) return false;
        if (size != null ? !size.equals(tank.size) : tank.size != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (currentLevel != null ? currentLevel.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", currentLevel=" + currentLevel +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", size=" + size +
                '}';
    }
}
