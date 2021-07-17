package com.maba.osads.services;

import com.maba.osads.helper.IdsHelper;
import com.maba.osads.persistence.Campaign;
import com.maba.osads.persistence.Product;
import com.maba.osads.persistence.ProductRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private List<Product> products = new ArrayList<>();

    @Autowired
    private ProductRepository repository;

    @PostConstruct
    public void initData(){
        readCsvData();
        repository.saveAll(products);
    }

    private void readCsvData() {
        Reader fr = null;
        try {
            fr = new FileReader("src/main/resources/products.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
        Iterable<CSVRecord> records = null;
        try {
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(fr);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

        for (CSVRecord record : records) {
            Product product = new Product(
                    record.get("productSerialNumber"),
                    record.get("title"),
                    record.get("category"),
                    "false",
                    Long.parseLong(record.get("price"))
                    );
            products.add(product);
        }
    }

    public Product getProductByCategory(Campaign campaign, String category) {
        List<String> productIds = IdsHelper.mapCommaSeparatedIdsToList(campaign.getProductIds());
        List<Product> productsByIds = repository.findAllById(productIds);

        return productsByIds.stream()
                .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                .findAny()
                .orElse(null);
    }

    public Product getAnyProductFromCampaign(String productIds) {
        String id = productIds.split(",")[0];
        return repository.getById(id);
    }
}
