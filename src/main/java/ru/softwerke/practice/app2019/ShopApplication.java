package ru.softwerke.practice.app2019;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
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
            }
        });

    }

    private DeviceService deviceService(Storage storage) {
        return new DeviceServiceImpl(storage);
    }

    private ClientService clientService(Storage storage) { return new ClientServiceImpl(storage); }

    private BillService billService(Storage storage) { return new BillServiceImpl(storage); }

    private ColorService colorService(Storage storage) { return new ColorServiceImpl(storage); }
}