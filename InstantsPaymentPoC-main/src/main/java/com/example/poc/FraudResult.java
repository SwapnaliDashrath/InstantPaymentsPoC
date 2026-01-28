package com.example.poc;

import java.util.Map;

public class FraudResult {
    private String transactionId;
    private String decision;
    private String message;
    private Map<String, Boolean> checks;
    public FraudResult() {}
    public FraudResult(String transactionId, String decision, String message) {
        this.transactionId = transactionId; this.decision = decision; this.message = message;
    }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Map<String, Boolean> getChecks() { return checks; }
    public void setChecks(Map<String, Boolean> checks) { this.checks = checks; }
}
