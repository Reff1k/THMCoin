package pl.ggoryl.thmcoin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {
    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Map<UUID, Double> balances = new HashMap<>();

    public DataManager(Path dataDirectory) {
        this.file = dataDirectory.resolve("balances.json").toFile();
        load();
    }

    private void load() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return;
        }
        try (Reader reader = new FileReader(file)) {
            Map<UUID, Double> loaded = gson.fromJson(reader, new TypeToken<Map<UUID, Double>>(){}.getType());
            if (loaded != null) balances = loaded;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(balances, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, 0.0);
    }

    public void setBalance(UUID uuid, double amount) {
        balances.put(uuid, amount);
        save();
    }
}