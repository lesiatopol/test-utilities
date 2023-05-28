# Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
# Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
# Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
# Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
# Vestibulum commodo. Ut rhoncus gravida arcu.
# User: Lesia Topol
# Date: 5/26/2023
# All rights reserved

@REQ03 @REQ04
Feature: Basic Feature file an example how to test AWS

  #Example of working with SNS, SQS, Step Function
  @aws @TC04
  Scenario Outline: Basic positive verification test | SNS, SQS, Step Function
    Given system publishes SNS event newStudent.json to create-student-topic in east region
      | id   | %randomNumber% |
      | name | <StudentName>  |
    Then system receives SQS message with <StudentName> at student-created-queue in east region
    And validate DynamoDB students table in east region has data
      | id   | %randomNumber% |
      | name | <StudentName>  |
    Given system executes Step Function update-student in east region with updateStudent.json input that has data
      | id   | %randomNumber%       |
      | name | <StudentNameUpdated> |
    And validate DynamoDB students table in east region has data
      | id   | %randomNumber%       |
      | name | <StudentNameUpdated> |
    Examples:
      | StudentName | StudentNameUpdated |
      | Alex Test   | Alex M Test        |

      #Example of working with S3, Lambda, PostgresSql
  @aws @TC05
  Scenario Outline: Basic positive verification test | SNS, SQS, Step Function
    Given system triggers Lambda order-create in east region with newOrder.json that has data
      | id   | %randomNumber% |
      | item | <ItemToOrder>  |
    Then validate PostgresSql SELECT * FROM orders where id='%randomNumber%' query return 1 records in east region
    Then system gets File <InvoiceFileName> in s3-orders / New in east region
    Examples:
      | ItemToOrder | InvoiceFileName                     |
      | printer     | invoice_order_id_%randomNumber%.pdf |
