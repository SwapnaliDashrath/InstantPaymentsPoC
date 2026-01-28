package com.example.poc.fraud;

import com.example.poc.model.Party;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;

public class BlacklistEngine {

    private final Set<String> names = new HashSet<>();
    private final Set<String> banks = new HashSet<>();
    private final Set<String> countries = new HashSet<>();
    private final Set<String> instructions = new HashSet<>();

    public BlacklistEngine() {
        load("/blacklists.json");
    }

    public void load(String resource) {
        try (InputStream is = BlacklistEngine.class.getResourceAsStream(resource)) {
            if (is == null) return;
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            ObjectMapper m = new ObjectMapper();
            Map map = m.readValue(json, Map.class);
            if (map.containsKey("names")) names.addAll(((List)map.get("names")).stream().map(s->((String)s).toLowerCase()).collect(Collectors.toSet()));
            if (map.containsKey("banks")) banks.addAll(((List)map.get("banks")).stream().map(s->((String)s).toLowerCase()).collect(Collectors.toSet()));
            if (map.containsKey("countries")) countries.addAll(((List)map.get("countries")).stream().map(s->((String)s).toUpperCase()).collect(Collectors.toSet()));
            if (map.containsKey("instructions")) instructions.addAll(((List)map.get("instructions")).stream().map(s->((String)s).toLowerCase()).collect(Collectors.toSet()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Boolean> runChecks(Party payer, Party payee, String instruction) {
        Map<String, Boolean> checks = new LinkedHashMap<>();
        checks.put("blacklistedPayerName", isNameBlacklisted(payer.getName()));
        checks.put("blacklistedPayeeName", isNameBlacklisted(payee.getName()));
        checks.put("blacklistedPayerBank", isBankBlacklisted(payer.getBank()));
        checks.put("blacklistedPayeeBank", isBankBlacklisted(payee.getBank()));
        checks.put("blacklistedPayerCountry", isCountryBlacklisted(payer.getCountry()));
        checks.put("blacklistedPayeeCountry", isCountryBlacklisted(payee.getCountry()));
        checks.put("blacklistedInstruction", isInstructionBlacklisted(instruction));
        return checks;
    }

    public boolean isNameBlacklisted(String name) {
        if (name == null) return false;
        return names.contains(name.trim().toLowerCase());
    }
    public boolean isBankBlacklisted(String bank) {
        if (bank == null) return false;
        return banks.contains(bank.trim().toLowerCase());
    }
    public boolean isCountryBlacklisted(String country) {
        if (country == null) return false;
        return countries.contains(country.trim().toUpperCase());
    }
    public boolean isInstructionBlacklisted(String instr) {
        if (instr == null) return false;
        String low = instr.toLowerCase();
        return instructions.stream().anyMatch(low::contains);
    }
}
