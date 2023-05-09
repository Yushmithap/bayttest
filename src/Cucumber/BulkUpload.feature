@BulkUpload
Feature: Bulk Upload the Rules from Rules Engine page
  I want to use this template for my feature file


  @BulkUploadCreation
  Scenario Outline: Positive Test for bulk uploading the rules
    Given Logged in with <username> and <password> into UW Portal
    When I navigate to Rules Management
    And clicked on Bulk Upload button
    And uploaded the bulkupload file
    Then I validate the toaster message displayed as 'Business rule is created successfully'
  
    Examples: 
      | username                         | password  |
      | yushmitha.pitchika@accenture.com |           |

  
