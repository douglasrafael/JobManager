package com.fsdeveloper.jobmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Class the balance
 *
 * @author Created by Douglas Rafael on 22/05/2016.
 * @version 1.0
 */
public class Balance implements Serializable {

    private static final long serialVersionUID = 6210053259676173037L;

    private String dateStart;
    private String dateEnd;
    private Double inputValue;
    private Double outputValue;
    private Double totalValue;
    private Double AverageInput;
    private Double AverageOutput;
    private Double AverageProfit;
    private List<Job> currentBalance;
    private List<Job> notCurrentBalance;

    public Balance() {
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Double getInputValue() {
        return inputValue;
    }

    public void setInputValue(Double inputValue) {
        this.inputValue = inputValue;
    }

    public Double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(Double outputValue) {
        this.outputValue = outputValue;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getAverageInput() {
        return AverageInput;
    }

    public void setAverageInput(Double averageInput) {
        AverageInput = averageInput;
    }

    public Double getAverageOutput() {
        return AverageOutput;
    }

    public void setAverageOutput(Double averageOutput) {
        AverageOutput = averageOutput;
    }

    public List<Job> getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(List<Job> currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<Job> getNotCurrentBalance() {
        return notCurrentBalance;
    }

    public void setNotCurrentBalance(List<Job> notCurrentBalance) {
        this.notCurrentBalance = notCurrentBalance;
    }

    public Double getAverageProfit() {
        return AverageProfit;
    }

    public void setAverageProfit(Double averageProfit) {
        AverageProfit = averageProfit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (dateStart != null ? !dateStart.equals(balance.dateStart) : balance.dateStart != null)
            return false;
        if (dateEnd != null ? !dateEnd.equals(balance.dateEnd) : balance.dateEnd != null)
            return false;
        if (inputValue != null ? !inputValue.equals(balance.inputValue) : balance.inputValue != null)
            return false;
        if (outputValue != null ? !outputValue.equals(balance.outputValue) : balance.outputValue != null)
            return false;
        return totalValue != null ? totalValue.equals(balance.totalValue) : balance.totalValue == null;

    }

    @Override
    public int hashCode() {
        int result = dateStart != null ? dateStart.hashCode() : 0;
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (inputValue != null ? inputValue.hashCode() : 0);
        result = 31 * result + (outputValue != null ? outputValue.hashCode() : 0);
        result = 31 * result + (totalValue != null ? totalValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", inputValue=" + inputValue +
                ", outputValue=" + outputValue +
                ", totalValue=" + totalValue +
                ", AverageInput=" + AverageInput +
                ", AverageOutput=" + AverageOutput +
                ", AverageProfit=" + AverageProfit +
                ", currentBalance=" + currentBalance +
                ", notCurrentBalance=" + notCurrentBalance +
                '}';
    }
}
