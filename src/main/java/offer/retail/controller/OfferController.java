package offer.retail.controller;
import offer.retail.exception.ResourceNotFoundException;
import offer.retail.model.Offer;
import offer.retail.service.MerchantService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController @RequestMapping("/merchant")
public class OfferController {

    private MerchantService merchantService;

    public OfferController () {
        merchantService = new MerchantService();
    }

    @RequestMapping(path = "/{merchantId}/offer",method = RequestMethod.POST)
    public ResponseEntity<?>  createOffer(UriComponentsBuilder b, @RequestBody Offer offer, @PathVariable String merchantId) {

        long offerId = merchantService.createOffer(merchantId, offer);
        UriComponents uriComponents =
                b.path("/merchant/{merchantId}/offer/{id}").buildAndExpand(merchantId,offerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        //return merchantService.createOffer(merchantId, offer);
    }

    @RequestMapping(path = "/{merchantId}/offer/{offerId}",method = RequestMethod.GET)
    public Offer fetchOffer(@PathVariable String merchantId, @PathVariable  String offerId) {
        Optional<Offer> offer = merchantService.fetchOffer(merchantId, offerId);
        if (offer.isPresent()) {
            return offer.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(path = "/{merchantId}/offer/{offerId}",method = RequestMethod.PUT)
    public void updateOffer(@PathVariable String merchantId, @PathVariable String offerId,@RequestBody Offer offer) {
        if(!merchantService.updateOffer(merchantId, offerId, offer)) {
            throw new ResourceNotFoundException();
        }
    }
}
