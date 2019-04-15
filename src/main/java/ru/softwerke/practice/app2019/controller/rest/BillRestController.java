package ru.softwerke.practice.app2019.controller.rest;

import ru.softwerke.practice.app2019.model.Bill;
import ru.softwerke.practice.app2019.model.BillItem;
import ru.softwerke.practice.app2019.service.BillDataService;
import ru.softwerke.practice.app2019.service.BillFilter;
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

    @Inject
    public BillRestController(BillDataService billDataService) {
        this.billDataService = billDataService;
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
        return billDataService.saveBill(bill);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bill getBill(@PathParam("id") UUID id) {
        return billDataService.getBillById(id);
    }

}
