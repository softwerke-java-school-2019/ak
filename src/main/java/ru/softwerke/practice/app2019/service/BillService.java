package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.model.Bill;

import java.util.List;

public interface BillService {
    Bill saveBill(Bill bill);

    Bill getBillById(int id);

    List<Bill> getBills(BillFilter filter);

}
