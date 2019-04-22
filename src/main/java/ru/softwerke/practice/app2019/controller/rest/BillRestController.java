package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.model.BillItem;
import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.model.Device;
import ru.softwerke.practice.app2019.service.BillDataService;
import ru.softwerke.practice.app2019.service.BillFilter;
import ru.softwerke.practice.app2019.service.ClientDataService;
import ru.softwerke.practice.app2019.service.DeviceDataService;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Path("bill")
public class BillRestController {
    private BillDataService billDataService;
    private ClientDataService clientDataService;
    private DeviceDataService deviceDataService;

    @Inject
    public BillRestController(BillDataService billDataService, ClientDataService clientDataService, DeviceDataService deviceDataService) {
        this.billDataService = billDataService;
        this.clientDataService = clientDataService;
        this.deviceDataService = deviceDataService;
    }

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bill> getBills(@QueryParam("clientId") UUID clientId,
                               @QueryParam("deviceIds") String deviceIdsStr,
                               @QueryParam("dateTimeFrom") String dateTimeFromStr,
                               @QueryParam("dateTimeTo") String dateTimeToStr,
                               @QueryParam("totalPriceFrom") BigDecimal totalPriceFrom,
                               @QueryParam("totalPriceTo") BigDecimal totalPriceTo,
                               @QueryParam("sortBy") String sortBy,
                               @DefaultValue("50") @QueryParam("count") int count,
                               @DefaultValue("0") @QueryParam("pageNumber") int pageNumber) {
        List<UUID> deviceIds = ParsingUtil.getDeviceIds(deviceIdsStr);
        LocalDateTime dateTimeFrom = ParsingUtil.getLocalDateTime(dateTimeFromStr);
        LocalDateTime dateTimeTo = ParsingUtil.getLocalDateTime(dateTimeToStr);
        List<SortConditional> sortConditionals = ParsingUtil.getSortParams(sortBy);

        BillFilter filter = new BillFilter()
                .withClientId(clientId)
                .withDateTimeFrom(dateTimeFrom)
                .withDateTimeTo(dateTimeTo)
                .withDeviceIds(deviceIds)
                .withTotalPriceFrom(totalPriceFrom)
                .withTotalPriceTo(totalPriceTo)
                .withSortParams(sortConditionals)
                .withCount(count)
                .withPageNumber(pageNumber);

        return billDataService.getBills(filter);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bill> getBills() {
        return billDataService.getBills();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bill createBill(Bill bill) {
        QueryValidator.checkEmptyRequest(bill);
        ModelValidator.validateEntity(bill);
        Client client = clientDataService.getClientById(bill.getClientId());
        QueryValidator.checkIfNotFound(client, String.format("Client with id %s doesn't exist", bill.getClientId()));
        for (BillItem billItem : bill.getBillItems()){
            ModelValidator.validateEntity(billItem);
            Device device = deviceDataService.getDeviceById(billItem.getDeviceId());
            QueryValidator.checkIfNotFound(device, String.format("Device with id %s doesn't exist", billItem.getDeviceId()));
        }
        return billDataService.saveBill(bill);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bill getBill(@PathParam("id") UUID id) {
        Bill bill = billDataService.getBillById(id);
        QueryValidator.checkIfNotFound(bill, String.format("Bill with id %s doesn't exist", id));
        return bill;
    }

}
