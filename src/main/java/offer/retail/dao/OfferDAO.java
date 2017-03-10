package offer.retail.dao;

import offer.retail.model.Offer;

import java.util.Optional;

public interface OfferDAO {

    long insertOffer(String merchantID, Offer offer);

    boolean updateOffer(String merchantID, String offerId, Offer offer);

    Optional<Offer> selectOffer(String offerId, String merchantId);

}
