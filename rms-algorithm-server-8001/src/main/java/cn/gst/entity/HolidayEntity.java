package cn.gst.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author 柏琪
 * @date 2024-05-06 19:29
 */
@Data
public class HolidayEntity {
    private LocalDate date;
    private String name;
    private boolean isOffDay;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOffDay() {
        return isOffDay;
    }

    public void setOffDay(boolean offDay) {
        isOffDay = offDay;
    }
}
