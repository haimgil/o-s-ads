package com.maba.osads.services;


import com.maba.osads.helper.IdsHelper;
import com.maba.osads.persistence.Campaign;
import com.maba.osads.persistence.CampaignRepository;
import com.maba.osads.persistence.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CampaignService {

    private final int CAMPAIGN_ACTIVE_FOR_DAYS = 10;

    @Autowired
    private ProductService productService;

    @Autowired
    private CampaignRepository campaignRepository;

    public Campaign create(String name, Date startDate, String productIdsToPromote, Long bid){
        Campaign campaign = new Campaign();
        campaign.setName(name);
        campaign.setStartDate(startDate);
        campaign.setProductIds(productIdsToPromote);
        campaign.setBid(bid);

        return campaignRepository.save(campaign);
    }

    public Map<Product,List<String>> findHighestBidCampaignProduct(String category) {
        List<Campaign> campaigns = campaignRepository.findAll();
        if (campaigns == null || campaigns.isEmpty()){
            return null;
        }
        List<Campaign> sortedByBidCampaigns = campaigns.stream() //all campaigns stream
                .filter(this::isActiveCampaign) // filter active campaigns
                .sorted(Comparator.comparingLong(Campaign::getBid).reversed()) // Sort by bid (high to low)
                .collect(Collectors.toList());
        for (int i=0 ; i < sortedByBidCampaigns.size(); i++){
            Product product = productService.getProductByCategory(sortedByBidCampaigns.get(i), category);
            if (product != null) {
                return buildResultMap(sortedByBidCampaigns.get(i), product);
            }
        }
        return buildResultMap(sortedByBidCampaigns.get(0), productService.getAnyProductFromCampaign(sortedByBidCampaigns.get(0).getProductIds()));
    }

    private Map<Product, List<String>> buildResultMap(Campaign campaign, Product product) {
        Map<Product, List<String>> productToCampaignAndBid = new HashMap<>();
        List<String> campaignAndBid = new ArrayList<>();
        campaignAndBid.add(campaign.getName());
        campaignAndBid.add(String.valueOf(campaign.getBid()));
        productToCampaignAndBid.put(product, campaignAndBid);

        return productToCampaignAndBid;
    }

    //I would use some scheduler to make campaign non active (using kafka/rabbitmq etc...)
    private boolean isActiveCampaign(Campaign campaign){
        Date nowDate = Calendar.getInstance().getTime();
        Long timeDifference = nowDate.getTime() - campaign.getStartDate().getTime();
        Long daysDifference = (timeDifference
                / (1000 * 60 * 60 * 24))
                % 365;
        return daysDifference < CAMPAIGN_ACTIVE_FOR_DAYS;
    }
}
