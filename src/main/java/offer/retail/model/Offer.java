package offer.retail.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
public class Offer {

    private long id;
    private String name;
    private String description;
    private long merchantId;
    private Currency currency;
    private BigDecimal offerPrice;

    public Offer() {}

    public Offer(long id, String name, String description, long merchantId, String currency, BigDecimal offerPrice) {
        this.id = id;
        this.merchantId = merchantId;
        this.name = name;
        this.description = description;
        this.currency = Currency.getInstance(currency);
        this.offerPrice = offerPrice;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

}
