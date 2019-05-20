package ru.softwerke.practice.app2019.utils;

import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.model.BillItem;
import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.model.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ModelUtils {
    public static Bill bill(){
        List<BillItem> billItems = new ArrayList<>();
        billItems.add(new BillItem(1, 2, BigDecimal.valueOf(2020.2)));
        billItems.add(new BillItem(2, 1, BigDecimal.valueOf(2000)));
        return new Bill(1, billItems, LocalDateTime.now());
    }

    public static Device device() {
        return new Device(DeviceType.SMARTWATCHES, BigDecimal.valueOf(2020.50), "Mi Band 2",
                "Черный", 0, LocalDate.now(), "Xiaomi");
    }
}
