
@DataInsightsInlandMarine
Feature: Validate All Data Insights widgets API responses for Inland Marine application

  @BuildingData
  Scenario: Validate the Building Data widget
    Given BuildingDataRequest Payload for building data
    When user calls BuildingDataAPI with post http request
    Then BuildingDataAPI call got success with status code 200
    And check if the response body and UI of Building data matches
    
  @FinancialRatios
  Scenario: Validate the Financial Ratios widget
    Given RequestHeaders for FinancialRatios API call
    When user calls FinancialRatiosAPI with Get http request
    Then FinancialRatiosAPI call got success with status code 200
    And check if the response body and UI of Financial Ratio matches
    
  @Financials
  Scenario: Validate the Financials widget
    Given RequestHeaders for Financials API call
    When user calls FinancialsAPI with Get http request
    Then FinancialsAPI call got success with status code 200
    And check if the response body and UI of Financials matches
    
  @CompanyFinancials
  Scenario: Validate the Company Financials widget
    Given RequestHeaders for Company Financials API call
    When user calls CompanyFinancialsAPI with Get http request
    Then CompanyFinancialsAPI call got success with status code 200
    And check if the response body and UI of Company Financials matches  
         
  @VehicleChangeExposure
   Scenario: Validate the Changes In exposure vehicle type widget
    Given RequestHeaders for Changes In exposure vehicle type API call
    When user calls ChangesInExposureVehicletypeAPI with Get http request
    Then ChangesInExposureVehicletypeAPI call got success with status code 200
    And check if the response body and UI of Changes In exposure vehicle type matches  
    
   @LocationChangeExposure
   Scenario: Validate the Changes In exposure location widget
    Given RequestHeaders for Changes In exposure location API call
    When user calls ChangesInExposureLocationAPI with Get http request
    Then ChangesInExposureLocationAPI call got success with status code 200
    And check if the response body and UI of Changes In exposure location matches
    
   @FacilityLocation
   Scenario: Validate the Facility Location widget
    Given RequestHeaders for Facility Location API call
    When user calls FacilityLocationAPI with Get http request
    Then FacilityLocationAPI call got success with status code 200
    And check if the response body and UI of Facility Location matches
    
   @VehicleDetails
   Scenario: Validate the Vehicle Details widget
    Given RequestHeaders for Vehicle Details API call
    When user calls VehicleDetailsAPI with Get http request
    Then VehicleDetailsAPI call got success with status code 200
    And check if the response body and UI of Vehicle Details matches  
    
   @FacilityLocationPhotoReferencce
    Scenario: Validate the Facility Location Photo Reference widget
    Given RequestHeaders for Facility Location Photo Reference API call
    When user calls FacilityLocationPhotoReferenceAPI with Post http request
    Then FacilityLocationPhotoReferenceAPI call got success with status code 200
    
    @KeyKnowledgeLink
    Scenario: Validate the Knowledge Link widget
    Given RequestHeaders for Knowledge Link API call
    When user calls KnowledgeLinkAPI of queryparams "application_id" and "business_rule_type" with Get http request
    Then KnowledgeLinkAPI call got success with status code 200
    And API Response "status" is "SUCCESS"
    And check if response matches with UI of Key Knowledge Link
     
   
   @OperatorDetails
   Scenario: Validate the Operator Details widget
    Given RequestHeaders for Operator Details API call
    When user calls OperatorDetailsAPI with Get http request
    Then OperatorDetailsAPI call got success with status code 200
    And check if the response body and UI of Operator Details matches
    
   @HistoricalLossRun
   Scenario: Validate the Historical Loss Run table
    Given RequestHeaders for Historical Loss Run API call
    When user calls HistoricalLossRunAPI with Get http request
    Then HistoricalLossRunAPI call got success with status code 200
    And check if the response body and UI of Historical Loss Run table matches
    
   @OccupancyExposureChange
   Scenario: Validate the Change In Exposure Occupancy table
    Given RequestHeaders for OccupancyExposureChange API call
    When user calls OccupancyExposureChangeAPI with Get http request
    Then OccupancyExposureChangeAPI call got success with status code 200
    And check if the response body and UI of OccupancyExposureChange table matches
    
   @ChangeInDeductibleOrLimit
   Scenario: Validate the Change In Exposure Occupancy table
    Given RequestHeaders for ChangeInDeductibleOrLimit API call
    When user calls ChangeInDeductibleOrLimit with Get http request
    Then ChangeInDeductibleOrLimit call got success with status code 200
    
   @HistoricalLossRatioPremium
   Scenario: Validate the Historical Loss Ratio table
    Given RequestHeaders for Historical Loss Ratio API call
    When user calls HistoricalLossRatioAPI with Get http request
    Then HistoricalLossRatioAPI call got success with status code 200
    And check if the response body and UI of Historical Loss Ratio table matches
    
   @AccountInfoChange
   Scenario: Validate the AccountInfoChange table
    Given RequestHeaders for AccountInfoChange API call
    When user calls AccountInfoChangeAPI with Get http request
    Then AccountInfoChangeAPI call got success with status code 200
    And check if the response body and UI of AccountInfoChange table matches
 
   @ChangeInExposureClassCode
   Scenario: Validate the ChangeInExposureClassCode table
    Given RequestHeaders for ChangeInExposureClassCode API call
    When user calls ChangeInExposureClassCode API with Get http request
    Then ChangeInExposureClassCode API call got success with status code 200
    And check if the response body and UI of ChangeInExposureClassCode widget data matches
    
   @ChangeInExposureState
   Scenario: Validate the ChangeInExposureState table
    Given RequestHeaders for ChangeInExposureState API call
    When user calls ChangeInExposureState API with Get http request
    Then ChangeInExposureState API call got success with status code 200
    And check if the response body and UI of ChangeInExposureState widget data matches
    
   
   @FilingAndEvents
   Scenario: Validate the FilingAndEvents table
    Given RequestHeaders for FilingAndEvents API call
    When user calls FilingAndEvents API with Get http request
    Then FilingAndEvents API call got success with status code 200
    And check if the response body and UI of FilingAndEvents widget data matches
    
   @DnBRating
   Scenario: Validate the DnBRating table
    Given RequestHeaders for DnBRating API call
    When user calls DnBRating API with Get http request
    Then DnBRating API call got success with status code 200
    And check if the response body and UI of DnBRating widget data matches
    
   @DnBCompanyProfile
   Scenario: Validate the DnBCompanyProfile table
    Given RequestHeaders for DnBCompanyProfile API call
    When user calls DnBCompanyProfile API with Get http request
    Then DnBCompanyProfile API call got success with status code 200
    And check if the response body and UI of DnBCompanyProfile widget data matches
    
   @DnBBusinessActivity
   Scenario: Validate the DnBBusinessActivity table
    Given RequestHeaders for DnBBusinessActivity API call
    When user calls DnBBusinessActivity API with Get http request
    Then DnBBusinessActivity API call got success with status code 200
    And check if the response body and UI of DnBBusinessActivity widget data matches
    
   @DnBSIC
   Scenario: Validate the DnBSIC table
    Given RequestHeaders for DnBSIC API call
    When user calls DnBSIC API with Get http request
    Then DnBSIC API call got success with status code 200
    And check if the response body and UI of DnBSIC widget data matches
    
   @PropertyAttributes
   Scenario: Validate the PropertyAttributes table
    Given PropertyAttributes Payload for property Attributes
    When user calls PropertyAttributes API with Post http request
    Then PropertyAttributes API call got success with status code 200
    And check if the response body and UI of PropertyAttributes widget data matches
    
   @FamilyTree
   Scenario: Validate the FamilyTree widget
    Given RequestHeaders for FamilyTree API call
    When user calls FamilyTree API with Get http request
    Then FamilyTree API call got success with status code 200
    And check if the response body and UI of FamilyTree widget data matches 
    
   @FinancialStrengthInsights
   Scenario: Validate the FinancialStrengthInsights widget
    Given RequestHeaders for FinancialStrengthInsights API call
    When user calls FinancialStrengthInsights API with Get http request
    Then FinancialStrengthInsights API call got success with status code 200
    And check if the response body and UI of FinancialStrengthInsights widget data matches  
    
   @ViabilityRating
   Scenario: Validate the ViabilityRating widget
    Given RequestHeaders for ViabilityRating API call
    When user calls ViabilityRating API with Get http request
    Then ViabilityRating API call got success with status code 200
    And check if the response body and UI of ViabilityRating widget data matches
    
   @ViolationsAndCitations
   Scenario: Validate the ViolationsAndCitations widget
    Given RequestHeaders for ViolationsAndCitations API call
    When user calls ViolationsAndCitations API with Get http request
    Then ViolationsAndCitations API call got success with status code 200
    And check if the response body and UI of ViolationsAndCitations widget data matches
    
   @DescriptiveInformation
   Scenario: Validate the DescriptiveInformation widget
    Given RequestHeaders for DescriptiveInformation API call
    When user calls DescriptiveInformation API with Get http request
    Then DescriptiveInformation API call got success with status code 200
    And check if the response body and UI of DescriptiveInformation widget data matches
    
    
   @HistoricalClaims
   Scenario: Validate the HistoricalClaims widget
    Given RequestHeaders for HistoricalClaims API call
    When user calls HistoricalClaims API with Get http request
    Then HistoricalClaims API call got success with status code 200
    And check if the response body and UI of HistoricalClaims widget data matches    
    
 
       
    
    
    
    
    
    
    
    
    

   