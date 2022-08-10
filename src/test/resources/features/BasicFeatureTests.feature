# Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
# Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
# Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
# Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
# Vestibulum commodo. Ut rhoncus gravida arcu.
# User: Lesia Topol
# Date: 7/10/2022
# All rights reserved

@REQ01 @REQ02
Feature: Basic Feature file (an example how to test APIs)

  #Example of sending GET call with multiple cases
  @regression @positivetest @TC01
  Scenario Outline: Basic positive verification test | GET call
    Given REST itunesLookupGET is parametrized
      | artistId | <artistId> |
    When system triggers endpoint
    Then verify REST response status code is 200
    And verify REST response status line is HTTP/1.1 200 OK
    And verify REST response attributes as expected
      | resultCount                 | 1                  |
      | results[0].artistId         | <artistId>         |
      | results[0].artistName       | <artistName>       |
      | results[0].primaryGenreName | <primaryGenreName> |
    Examples:
      | artistId | artistName   | primaryGenreName |
      | 20540516 | Necro        | Hip-Hop          |
      | 909253   | Jack Johnson | Rock             |

  #Example of DataStorage usage and randomizing input params to GET call, random number generated in BaseTest
  @regression @negativetest @TC02
  Scenario: Basic negative verification test | GET call
    Given REST itunesLookupGET is parametrized
      | artistId | %randomNumber% |
    When system triggers endpoint
    Then verify REST response status code is 200
    And verify REST response status line is HTTP/1.1 200 OK
    And verify REST response attributes as expected
      | resultCount | 0  |
      | results[*]  | [] |

  #Example of sending POST call
  @regression @negativetest @TC03
  Scenario: Basic negative verification test | POST call
    Given REST googleTranslatePOST has default attributes
    When system triggers endpoint
    And verify REST response status line is HTTP/1.1 401 Unauthorized
    And verify REST response attributes as expected
      | error.code              | 401                 |
      | error.status            | UNAUTHENTICATED     |
      | error.errors[0].message | Invalid Credentials |
      | error.errors[0].reason  | authError           |
