package offer.retail.dao;

import offer.retail.model.Offer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Component
public class OfferDAOImpl implements  OfferDAO{


    private Map<String, Offer> merchantOfferMap = new HashMap<String, Offer>();
    private long idCounter = 1;

    public long insertOffer(String merchantID, Offer offer) {
        Offer newOffer = new Offer(idCounter, offer.getName(), offer.getDescription(), new Long(merchantID), offer.getCurrency().getCurrencyCode(), offer.getOfferPrice());
        merchantOfferMap.put(merchantID+"-"+idCounter, newOffer);
        idCounter++;
        return newOffer.getId();
    }

    public boolean updateOffer(String merchantID, String offerId, Offer offer) {
        boolean returnValue;
        if (merchantOfferMap.containsKey(merchantID+"-"+offerId)) {
            merchantOfferMap.put(merchantID+"-"+offerId, new Offer(new Long(offerId).longValue(), offer.getName(), offer.getDescription(), new Long(merchantID), offer.getCurrency().getCurrencyCode(), offer.getOfferPrice()));
            return true;
        } else {
            return false;
        }
    }

    public Optional<Offer> selectOffer(String merchantId, String offerId) {
        if (merchantOfferMap.containsKey(merchantId+"-"+offerId)) {
            return Optional.of(merchantOfferMap.get(merchantId+"-"+offerId));
        } else {
            return Optional.empty();
        }
    }

}
