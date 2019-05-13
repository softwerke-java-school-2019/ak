package ru.softwerke.practice.app2019;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.softwerke.practice.app2019.controller.rest.handlers.BillWebHandler;
import ru.softwerke.practice.app2019.controller.rest.handlers.ClientWebHandler;
import ru.softwerke.practice.app2019.controller.rest.handlers.DeviceWebHandler;
import ru.softwerke.practice.app2019.service.*;
import ru.softwerke.practice.app2019.storage.RuntimeStorage;
import ru.softwerke.practice.app2019.storage.Storage;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ShopApplication extends ResourceConfig {
    public ShopApplication() {
        packages("ru.softwerke.practice.app2019;com.fasterxml.jackson.jaxrs");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                Storage storageDevice = new RuntimeStorage();
                bind(storageDevice).to(Storage.class);
                Storage storageClient = new RuntimeStorage();
                bind(storageClient).to(Storage.class);
                Storage storageBill = new RuntimeStorage();
                bind(storageBill).to(Storage.class);
                Storage storageColor = new RuntimeStorage();
                bind(storageColor).to(Storage.class);
                bind(deviceService(storageDevice)).to(DeviceService.class);
                bind(clientService(storageClient)).to(ClientService.class);
                bind(billService(storageBill)).to(BillService.class);
                bind(colorService(storageColor)).to(ColorService.class);
                bind(clientWebHandler(storageClient)).to(ClientWebHandler.class);
                bind(deviceWebHandler(storageDevice, storageColor)).to(DeviceWebHandler.class);
                bind(billWebHandler(storageBill, storageClient,storageDevice)).to(BillWebHandler.class);
            }
        });

    }

    private DeviceService deviceService(Storage storage) {
        return new DeviceServiceImpl(storage);
    }

    private ClientService clientService(Storage storage) { return new ClientServiceImpl(storage); }

    private BillService billService(Storage storage) { return new BillServiceImpl(storage); }

    private ColorService colorService(Storage storage) { return new ColorServiceImpl(storage); }

    private ClientWebHandler clientWebHandler(Storage storage) {
        return new ClientWebHandler(clientService(storage));
    }

    private DeviceWebHandler deviceWebHandler(Storage storageDevice, Storage storageColor) {
        return new DeviceWebHandler(deviceService(storageDevice), colorService(storageColor));
    }

    private BillWebHandler billWebHandler(Storage storageBill, Storage storageClient, Storage storageDevice){
        return new BillWebHandler(billService(storageBill), clientService(storageClient), deviceService(storageDevice));
    }
}