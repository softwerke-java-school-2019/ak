package ru.softwerke.practice.app2019;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import ru.softwerke.practice.app2019.service.ClientDataService;
import ru.softwerke.practice.app2019.service.ClientDataServiceImpl;
import ru.softwerke.practice.app2019.service.DeviceDataService;
import ru.softwerke.practice.app2019.service.DeviceDataServiceImpl;
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
                bind(deviceDataService(storageDevice)).to(DeviceDataService.class);
                bind(clientDataService(storageClient)).to(ClientDataService.class);
            }
        });

        //register(MultiPartFeature.class);
    }

    private DeviceDataService deviceDataService(Storage storage) {
        return new DeviceDataServiceImpl(storage);
    }

    private ClientDataService clientDataService(Storage storage) { return new ClientDataServiceImpl(storage); }
}