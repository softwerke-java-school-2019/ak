package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.date.DateDeserializer;
import ru.softwerke.practice.app2019.model.date.DateTimeDeserializer;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ParsingUtil {
    public static LocalDate getLocalDate(String dateStr){
        LocalDate date = null;
        if (dateStr != null) {
            date = LocalDate.parse(dateStr, DateDeserializer.formatter);
        }
        return date;
    }

    public static LocalDateTime getLocalDateTime(String dateTimeStr){
        LocalDateTime dateTime = null;
        if (dateTimeStr != null) {
            dateTime = LocalDateTime.parse(dateTimeStr, DateTimeDeserializer.formatter);
        }
        return dateTime;
    }

    public static List<SortConditional> getSortParams(String sortBy){
        if (sortBy == null) {
            return Collections.emptyList();
        }
        List<SortConditional> sortConditionals = new ArrayList<>();
        String[] params = sortBy.split(",");
        for (String param : params){
            SortConditional.Order order;
            String field;
            if (param.charAt(0) == '-') {
                field = param.substring(1);
                order = SortConditional.Order.DESC;
            } else {
                field = param;
                order = SortConditional.Order.ASC;
            }
            sortConditionals.add(new SortConditional(field, order));
        }

        return sortConditionals;
    }

    public static List<UUID> getDeviceIds(String deviceIdsStr){
        if (deviceIdsStr == null){
            return Collections.emptyList();
        }
        List<UUID> deviceIds = new ArrayList<>();
        String[] params = deviceIdsStr.split(",");
        for (String param : params){
            deviceIds.add(UUID.fromString(param));
        }
        return deviceIds;
    }


}
