package com.jensen.nivell.models;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

public class Tank implements IDocumentPersisted{

    private String uid;
    private String reference; // lookup key without space used in sms alert
    private String name;
    private BigDecimal currentLevel;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal size;
    private List<Alert> alerts;

    public Tank(String reference, String name, BigDecimal size, BigDecimal currentLevel, BigDecimal latitude, BigDecimal longitude) {

        this.reference = reference;
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
    public String getLookupKey(){
        return "tank::"+ reference;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = "tank::" + reference;
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

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tank tank = (Tank) o;

        if (alerts != null ? !alerts.equals(tank.alerts) : tank.alerts != null) return false;
        if (currentLevel != null ? !currentLevel.equals(tank.currentLevel) : tank.currentLevel != null) return false;
        if (reference != null ? !reference.equals(tank.reference) : tank.reference != null) return false;
        if (latitude != null ? !latitude.equals(tank.latitude) : tank.latitude != null) return false;
        if (longitude != null ? !longitude.equals(tank.longitude) : tank.longitude != null) return false;
        if (name != null ? !name.equals(tank.name) : tank.name != null) return false;
        if (size != null ? !size.equals(tank.size) : tank.size != null) return false;
        if (uid != null ? !uid.equals(tank.uid) : tank.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (currentLevel != null ? currentLevel.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (alerts != null ? alerts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "uid='" + uid + '\'' +
                ", reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                ", currentLevel=" + currentLevel +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", size=" + size +
                ", alerts=" + alerts +
                '}';
    }
}
