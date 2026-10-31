package de.htw_berlin.pickyourread.enums;

public enum Format {
    Paperback("pages"), Hardcover("pages"), Audiobook("hours");

    private String quantifier;

    private Format(String quantifier) {
        this.quantifier = quantifier;
    }

    public String getQuantifier() {
        return quantifier;
    }
}
