package com.jensen.nivell.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alert implements IDocumentPersisted {

    public static final String PLATFORM_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private String uid;
    private String tankReference;
    private BigDecimal level;
    private Date time;

    public Alert(String tankReference, BigDecimal level, String time) {

        this.tankReference = (tankReference!=null)? "tank::" + tankReference:null;
        this.level = level;

        DateFormat format = new SimpleDateFormat(PLATFORM_DATE_FORMAT);
        Date parsedDate;
        try{
            parsedDate = format.parse(time);
        }catch (Exception ex) {
            parsedDate = null;
        }

        this.time = parsedDate;
    }

    @Override
    public String getLookupKey(){
        return null;
    }

    @NotNull(message = "Time value is null or incorrect; correct format is yyyy-MM-dd hh:mm:ss")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @NotNull(message = "Tank liquid level can't be null")
    @DecimalMin(value = "0.00", message = "Liquid level must be a positive value")
    public BigDecimal getLevel() {
        return level;
    }

    public void setLevel(BigDecimal level) {
        this.level = level;
    }

    @NotNull(message = "Alert must specify tank reference")
    public String getTankReference() {
        return tankReference;
    }

    public void setTankReference(String tankReference) {
        this.tankReference = tankReference;
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

        Alert alert = (Alert) o;

        if (level != null ? !level.equals(alert.level) : alert.level != null) return false;
        if (tankReference != null ? !tankReference.equals(alert.tankReference) : alert.tankReference != null)
            return false;
        if (time != null ? !time.equals(alert.time) : alert.time != null) return false;
        if (uid != null ? !uid.equals(alert.uid) : alert.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (tankReference != null ? tankReference.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "uid='" + uid + '\'' +
                ", tankReference='" + tankReference + '\'' +
                ", level=" + level +
                ", time=" + time +
                '}';
    }
}
