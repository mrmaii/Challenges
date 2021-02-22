Feature: Sending email
  As an Agent, I want to send an email for another user

  Scenario: Users send an email to another user
    Given A User sends an "hello" message
    When The message is converted by the Adapter
    Then The Message server should contain the "hello" message in the queue
    
    
    
