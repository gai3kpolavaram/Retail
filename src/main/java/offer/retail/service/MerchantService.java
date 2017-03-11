package offer.retail.service;

import offer.retail.dao.OfferDAO;
import offer.retail.dao.OfferDAOImpl;
import offer.retail.model.Offer;

import java.util.Optional;

public class MerchantService {



    private OfferDAO offerDAO;

    public MerchantService() {
        offerDAO = new OfferDAOImpl();
    }

    public long createOffer(long merchantId, Offer offer) {
        return offerDAO.insertOffer(merchantId, offer);
    }

    public boolean updateOffer(String merchantId, String offerId, Offer offer) {
        return offerDAO.updateOffer(merchantId, offerId, offer);
    }

    public Optional<Offer> fetchOffer(String merchantId, String offerId) {
         return offerDAO.selectOffer(merchantId, offerId);

    }

}
