package com.jensen.nivell.models;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Station {

    private String name;
    private List<Tank> tanks;

    @NotNull(message = "Station is null, please give a station name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public void setTanks(List<Tank> tanks) {
        this.tanks = tanks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (!name.equals(station.name)) return false;
        if (tanks != null ? !tanks.equals(station.tanks) : station.tanks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (tanks != null ? tanks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", tanks=" + tanks +
                '}';
    }
}
