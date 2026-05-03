package pl.ggoryl.thmcoin;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

@Plugin(id = "thmcoin", name = "THMCoin", version = "1.0")
public class THMCoin {

    private static THMCoin api;
    private final Logger logger;
    private final Path dataDirectory;
    private DataManager dataManager;

    @Inject
    public THMCoin(Logger logger, @DataDirectory Path dataDirectory) {
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        api = this;
        this.dataManager = new DataManager(dataDirectory);
        logger.info("THMCoin API zostalo zainicjowane!");
    }

    public static THMCoin getAPI() {
        return api;
    }

    public double get(UUID uuid) {
        return dataManager.getBalance(uuid);
    }

    public void put(UUID uuid, double amount) {
        double current = get(uuid);
        dataManager.setBalance(uuid, current + amount);
    }

    public void set(UUID uuid, double amount) {
        dataManager.setBalance(uuid, amount);
    }
}