@DataInsights
Feature: Validate All Data Insights widgets for Inland Marine application

  @InlandMarineWidgets
  Scenario: Validate the InlandMarineWidgets
    Given Login to UW portal and navigate to data insights of Inland Marine Submission
  #DescriptiveInformation  
    When user calls DescriptiveInformation API with Get http request
    Then DescriptiveInformation API call got success with status code 200
    And check if the response body and UI of DescriptiveInformation widget data matches
  #KnowledgeLink
    When user calls KnowledgeLinkAPI of queryparams "application_id" and "business_rule_type" with Get http request
    Then KnowledgeLinkAPI call got success with status code 200
    And API Response "status" is "SUCCESS"
    And check if response matches with UI of Key Knowledge Link
  #OperatorDetails
    When user calls OperatorDetailsAPI with Get http request
    Then OperatorDetailsAPI call got success with status code 200
    And check if the response body and UI of Operator Details matches
  #HistoricalLossRun
    When user calls HistoricalLossRunAPI with Get http request
    Then HistoricalLossRunAPI call got success with status code 200
    And check if the response body and UI of Historical Loss Run table matches
  #HistoricalClaims
    When user calls HistoricalClaims API with Get http request
    Then HistoricalClaims API call got success with status code 200
    And check if the response body and UI of HistoricalClaims widget data matches      
  #OccupancyExposureChange  
    When user calls OccupancyExposureChangeAPI with Get http request
    Then OccupancyExposureChangeAPI call got success with status code 200
    And check if the response body and UI of OccupancyExposureChange table matches
  #ChangeInDeductibleOrLimit   
    When user calls ChangeInDeductibleOrLimit with Get http request
    Then ChangeInDeductibleOrLimit call got success with status code 200
  #ChangesInExposureVehicletype  
    When user calls ChangesInExposureVehicletypeAPI with Get http request
    Then ChangesInExposureVehicletypeAPI call got success with status code 200
    And check if the response body and UI of Changes In exposure vehicle type matches  
  #HistoricalLossRatio  
    When user calls HistoricalLossRatioAPI with Get http request
    Then HistoricalLossRatioAPI call got success with status code 200
    And check if the response body and UI of Historical Loss Ratio table matches
  #ChangesInExposureLocation  
    When user calls ChangesInExposureLocationAPI with Get http request
    Then ChangesInExposureLocationAPI call got success with status code 200
    And check if the response body and UI of Changes In exposure location matches
  #VehicleDetails  
    When user calls VehicleDetailsAPI with Get http request
    Then VehicleDetailsAPI call got success with status code 200
    And check if the response body and UI of Vehicle Details matches  
  #AccountInfoChange  
    When user calls AccountInfoChangeAPI with Get http request
    Then AccountInfoChangeAPI call got success with status code 200
    And check if the response body and UI of AccountInfoChange table matches
  #ChangeInExposureClassCode  
    When user calls ChangeInExposureClassCode API with Get http request
    Then ChangeInExposureClassCode API call got success with status code 200
    And check if the response body and UI of ChangeInExposureClassCode widget data matches
  #ChangeInExposureState  
    When user calls ChangeInExposureState API with Get http request
    Then ChangeInExposureState API call got success with status code 200
    And check if the response body and UI of ChangeInExposureState widget data matches
  #FacilityLocation  
    When user calls FacilityLocationAPI with Get http request
    Then FacilityLocationAPI call got success with status code 200
    And check if the response body and UI of Facility Location matches   
  #CompanyFinancials  
    When user calls CompanyFinancialsAPI with Get http request
    Then CompanyFinancialsAPI call got success with status code 200
    And check if the response body and UI of Company Financials matches    
  #FilingAndEvents  
    When user calls FilingAndEvents API with Get http request
    Then FilingAndEvents API call got success with status code 200
    And check if the response body and UI of FilingAndEvents widget data matches
  #FamilyTree
    When user calls FamilyTree API with Get http request
    Then FamilyTree API call got success with status code 200
    And check if the response body and UI of FamilyTree widget data matches
  #Financials  
    When user calls FinancialsAPI with Get http request
    Then FinancialsAPI call got success with status code 200
    And check if the response body and UI of Financials matches     
  #DnBRating  
    When user calls DnBRating API with Get http request
    Then DnBRating API call got success with status code 200
    And check if the response body and UI of DnBRating widget data matches
  #DnBCompanyProfile  
    When user calls DnBCompanyProfile API with Get http request
    Then DnBCompanyProfile API call got success with status code 200
    And check if the response body and UI of DnBCompanyProfile widget data matches 
  #DnBBusinessActivity   
    When user calls DnBBusinessActivity API with Get http request 
    Then DnBBusinessActivity API call got success with status code 200
    And check if the response body and UI of DnBBusinessActivity widget data matches 
  #DnBSIC   
    When user calls DnBSIC API with Get http request
    Then DnBSIC API call got success with status code 200
    And check if the response body and UI of DnBSIC widget data matches
  #ViolationsAndCitations
    When user calls ViolationsAndCitations API with Get http request
    Then ViolationsAndCitations API call got success with status code 200
    And check if the response body and UI of ViolationsAndCitations widget data matches  
  #FinancialStrengthInsights
    When user calls FinancialStrengthInsights API with Get http request
    Then FinancialStrengthInsights API call got success with status code 200
    And check if the response body and UI of FinancialStrengthInsights widget data matches   
  #PropertyAttributes
  #  When user calls PropertyAttributes API with Post http request
  #  Then PropertyAttributes API call got success with status code 200
  #  And check if the response body and UI of PropertyAttributes widget data matches     
  #BuildingData  
    When user calls BuildingDataAPI with post http request
    Then BuildingDataAPI call got success with status code 200
    And check if the response body and UI of Building data matches
  #ViabilityRating
    When user calls ViabilityRating API with Get http request
    Then ViabilityRating API call got success with status code 200
    And check if the response body and UI of ViabilityRating widget data matches    
  #FinancialRatios  
    When user calls FinancialRatiosAPI with Get http request
    Then FinancialRatiosAPI call got success with status code 200
    And check if the response body and UI of Financial Ratio matches

