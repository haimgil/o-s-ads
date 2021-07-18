# o-s-ads
Online sponsered ads

PreRequisites:
- Java 8
- maven 3.6.3 (might work with lower version)

Steps to run:
 >> Go to https://github.com/haimgil/o-s-ads/ and clone project 
 
 >> mvn package
 
 >> mvn spring-boot:run
 
Retrive initialized products:
  >> POST localhost:8080/api/v0/retrieveProducts

Create Campaign:
  >> POST localhost:8080/api/v0/advertise/create
  
  >> {"name":"String", "startDate":Date (yyyy-MM-dd'T'HH:mm:ss format}, "productIds":"String" (comma separated ids), "bid":Long}

ServeAd:
  >> POST localhost:8080/api/v0/advertise/serveAd?category=tv
  * Response headers contains campaign name and bid for tracking result.
 
 NOTES:
 * BE written in java with Spring-boot
 * csv file containes initialized products can be found in /src/main/resources and can be edited with more/less products. 
