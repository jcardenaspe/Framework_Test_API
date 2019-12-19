@alpha_corpus
@randomPhrase

Feature: Random phrase
  Should be returner a random phrase.

  @joseTest
  Scenario: Get random phrases
    Given I set the "https://qa.alphacorpus.servex.com.pe/data/api" base URL
     When I perform a GET request to the "/frases/locutor" path
     Then The response should contain the 200 status code
      And The response should contain the application/json; charset=utf-8 content type
      And The schema of the response should be equal to the "/alpha_corpus/schemes/phrases/randomPhrase.json" file
