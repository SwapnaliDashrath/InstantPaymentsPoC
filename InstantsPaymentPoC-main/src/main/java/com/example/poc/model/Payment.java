package com.example.poc.model;

import java.math.BigDecimal;

public class Payment {
    private String transactionId;
    private Party payer;
    private Party payee;
    private String paymentInstruction;
    private String executionDate;
    private BigDecimal amount;
    private String currency;
    private String creationTimestamp;

    public Payment() {}

    // getters and setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public Party getPayer() { return payer; }
    public void setPayer(Party payer) { this.payer = payer; }

    public Party getPayee() { return payee; }
    public void setPayee(Party payee) { this.payee = payee; }

    public String getPaymentInstruction() { return paymentInstruction; }
    public void setPaymentInstruction(String paymentInstruction) { this.paymentInstruction = paymentInstruction; }

    public String getExecutionDate() { return executionDate; }
    public void setExecutionDate(String executionDate) { this.executionDate = executionDate; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getCreationTimestamp() { return creationTimestamp; }
    public void setCreationTimestamp(String creationTimestamp) { this.creationTimestamp = creationTimestamp; }
}
