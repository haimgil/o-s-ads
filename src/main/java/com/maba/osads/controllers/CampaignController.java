package com.maba.osads.controllers;

import com.maba.osads.persistence.Campaign;
import com.maba.osads.persistence.CampaignRepository;
import com.maba.osads.persistence.Product;
import com.maba.osads.services.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v0")
public class CampaignController {

    private static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    private CampaignRepository repository;
    @Autowired
    private CampaignService campaignService;

    @PostMapping("/advertise/create")
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        if (campaign == null){
            return ResponseEntity.badRequest().build();
        }
        String name = campaign.getName();
        Date start_date = campaign.getStartDate();
        String productIds = campaign.getProductIds();
        Long bid = campaign.getBid();
        if (name == null || start_date == null || productIds == null || bid == null) {
            ResponseEntity.badRequest().body("Either name, startDate, bid and productIds list must be provided");
        }
        if (productIds == null || productIds.isEmpty()) {
            logger.warn("Are you sure you want to create campaign with no products specified?!?!");
        }
        Campaign createdCampaign = campaignService.create(name, start_date, productIds, bid);

        return ResponseEntity.ok(createdCampaign);
    }

    @GetMapping("/advertise/serveAd")
    public ResponseEntity<Product> serveAds(@RequestParam String category) {
        Map<Product, List<String>> resultMap = campaignService.findHighestBidCampaignProduct(category);
        if (resultMap == null){
            return ResponseEntity.notFound().build();
        }
        //In this point the map populated
        Product promotedProduct = resultMap.keySet().iterator().next();
        List<String> campaignAndBid = resultMap.get(promotedProduct);

        return ResponseEntity.ok()
                .header("campaign_name", campaignAndBid.get(0))
                .header("bid", String.valueOf(campaignAndBid.get(1)))
                .body(promotedProduct);
    }
}
