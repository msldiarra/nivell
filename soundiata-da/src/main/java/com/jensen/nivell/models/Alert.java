package com.jensen.nivell.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Alert implements IDocumentPersisted {

    public static final String PLATFORM_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private String tankIdentifier;
    private BigDecimal level;
    private Date time;

    public Alert(String tankIdentifier, BigDecimal level, String time) {

        this.tankIdentifier = tankIdentifier;
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
    public String getPersistenceKey(){
        return tankIdentifier + "::" + time.getTime();
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

    @NotNull(message = "Alert must specify tank identifier")
    public String getTankIdentifier() {
        return tankIdentifier;
    }

    public void setTankIdentifier(String tankIdentifier) {
        this.tankIdentifier = tankIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alert alert = (Alert) o;

        if (level != null ? !level.equals(alert.level) : alert.level != null) return false;
        if (tankIdentifier != null ? !tankIdentifier.equals(alert.tankIdentifier) : alert.tankIdentifier != null)
            return false;
        if (time != null ? !time.equals(alert.time) : alert.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tankIdentifier != null ? tankIdentifier.hashCode() : 0;
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "tankIdentifier='" + tankIdentifier + '\'' +
                ", level=" + level +
                ", time=" + time +
                '}';
    }
}
