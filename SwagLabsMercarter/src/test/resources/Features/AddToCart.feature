@tag
Feature: feature to add highest price item to cart

Scenario: User should be able to add highest price item to cart
Given User is on the login page
And User logs on to the website
When User added the highest price item to cart
Then User should see the highest price item in cart

