package com.example.poc;

import java.util.List;
import java.util.Set;

public class BlacklistEngine {
    private final Set<String> names;
    private final Set<String> countries;
    private final Set<String> banks;
    private final List<String> instructions;
    public BlacklistEngine(Set<String> names, Set<String> countries, Set<String> banks, List<String> instructions) {
        this.names = lower(names);
        this.countries = upper(countries);
        this.banks = lower(banks);
        this.instructions = lowerList(instructions);
    }
    public FraudResult check(Payment payment) {
        boolean nameBlocked = names.contains(payment.getPayer().getName().toLowerCase()) ||
                               names.contains(payment.getPayee().getName().toLowerCase());
        boolean countryBlocked = countries.contains(payment.getPayer().getCountry().toUpperCase()) ||
                                  countries.contains(payment.getPayee().getCountry().toUpperCase());
        boolean bankBlocked = banks.contains(payment.getPayer().getBank().toLowerCase()) ||
                               banks.contains(payment.getPayee().getBank().toLowerCase());
        boolean instrBlocked = instructions.stream().anyMatch(instr -> payment.getPaymentInstruction().toLowerCase().contains(instr));
        boolean blocked = nameBlocked || countryBlocked || bankBlocked || instrBlocked;
        return new FraudResult(payment.getTransactionId(), blocked ? "REJECT" : "APPROVE",
                               blocked ? "Suspicious payment" : "Nothing found, all okay");
    }
    private Set<String> lower(Set<String> values) { return values.stream().map(String::toLowerCase).collect(java.util.stream.Collectors.toSet()); }
    private Set<String> upper(Set<String> values) { return values.stream().map(String::toUpperCase).collect(java.util.stream.Collectors.toSet()); }
    private List<String> lowerList(List<String> values) { return values.stream().map(String::toLowerCase).toList(); }
}
