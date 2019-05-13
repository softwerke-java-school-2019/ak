package ru.softwerke.practice.app2019.controller.rest.handlers;

import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.model.BillItem;
import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.service.BillFilter;
import ru.softwerke.practice.app2019.service.BillService;
import ru.softwerke.practice.app2019.service.ClientService;
import ru.softwerke.practice.app2019.service.DeviceService;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.utils.ModelValidator;
import ru.softwerke.practice.app2019.utils.ParsingUtil;
import ru.softwerke.practice.app2019.utils.QueryValidator;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BillWebHandler {

    private BillService billService;
    private ClientService clientService;
    private DeviceService deviceService;

    @Inject
    public BillWebHandler(BillService billService, ClientService clientService, DeviceService deviceService) {
        this.billService = billService;
        this.clientService = clientService;
        this.deviceService = deviceService;
    }

    public List<Bill> getBills(Long clientId,
                               String deviceIdsStr,
                               String dateTimeStr,
                               String dateTimeFromStr,
                               String dateTimeToStr,
                               BigDecimal totalPrice,
                               BigDecimal totalPriceFrom,
                               BigDecimal totalPriceTo,
                               String sortBy,
                               int count,
                               int pageNumber) {
        List<Long> deviceIds = ParsingUtil.getDeviceIds(deviceIdsStr);
        LocalDateTime dateTimeFrom = ParsingUtil.getLocalDateTime(dateTimeFromStr);
        LocalDateTime dateTimeTo = ParsingUtil.getLocalDateTime(dateTimeToStr);
        LocalDateTime dateTime = ParsingUtil.getLocalDateTime(dateTimeStr);
        List<SortConditional> sortConditionals = ParsingUtil.getSortParams(sortBy);

        BillFilter filter = new BillFilter()
                .withClientId(clientId)
                .withDateTime(dateTime)
                .withDateTimeFrom(dateTimeFrom)
                .withDateTimeTo(dateTimeTo)
                .withDeviceIds(deviceIds)
                .withTotalPrice(totalPrice)
                .withTotalPriceFrom(totalPriceFrom)
                .withTotalPriceTo(totalPriceTo)
                .withSortParams(sortConditionals)
                .withCount(count)
                .withPageNumber(pageNumber - 1);
        ModelValidator.validateEntity(filter);
        return billService.getBills(filter);
    }

    public Bill createBill(Bill bill) {
        QueryValidator.checkEmptyRequest(bill);
        ModelValidator.validateEntity(bill);
        Client client = clientService.getClientById(bill.getClientId());
        QueryValidator.checkIfNotFound(client, String.format("Customer with id %s doesn't exist", bill.getClientId()));
        for (BillItem billItem : bill.getBillItems()) {
            ModelValidator.validateEntity(billItem);
            Device device = deviceService.getDeviceById(billItem.getDeviceId());
            QueryValidator.checkIfNotFound(device, String.format("Device with id %s doesn't exist", billItem.getDeviceId()));
        }
        return billService.saveBill(bill);
    }

    public Bill getBill(long id) {
        Bill bill = billService.getBillById(id);
        QueryValidator.checkIfNotFound(bill, String.format("Bill with id %s doesn't exist", id));
        return bill;
    }
}
