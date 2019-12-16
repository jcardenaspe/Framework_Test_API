# APIFramework_SERVEX

Proyecto Java creado para la automatización de pruebas api de los proyectos desarrollados para el BOT de SERVEX, este proyecto fue desarrollado con las siguientes tecnologias:
 - Cucumber
 - JUnit
 - Log4j
 - Java
 - Maven
 
Usar el siguiente comando para ejecutar el feature demo creado: "mvn test -Dcucumber.options="--tags @joseTest"" (sin las primeras comillas)

El framework tiene la capicidad de obtener el resultado de las pruebas ejecutadas, cuando las pruebas finalizan ok imprimiendo en consola un mensaje como el siguiente:

@alpha_corpus @randomPhrase
Feature: Random phrase
  Should be returner a random phrase.

  @alpha_corpus @randomPhrase @joseTest
  Scenario: Get random phrases                                                                                   # src/test/java/features/alpha_corpus/securityCrendentials.feature:8
    Given I set the "https://qa.alphacorpus.servex.com.pe/data/api" base URL                                     # givenGeneralSteps.setBaseURL(String)
    When I perform a GET request to the "/frases/locutor" path                                                   # requestGeneralWhenSteps.performGet(String)
    Then The response should contain the 200 status code                                                         # requestGeneralThenSteps.validateStatusCodeResponse(int)
    And The response should contain the application/json; charset=utf-8 content type                             # requestGeneralThenSteps.validateContentType(String)
    And The schema of the response should be equal to the "/alpha_corpus/schemes/phrases/randomPhrase.json" file # requestGeneralThenSteps.validateSchemaResponse(String)

1 Scenarios (1 passed)
5 Steps (5 passed)
0m3.334s

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.607 sec - in RunCukesTest
Good Job :D

Cuando la prueba finaliza con errores, imprimira en consola el paso que fallo junto con el error message generado por Cucumber, falta implementar la integración con Testlink para que todos los resultados se envien y tener un reporte, actualmente me encuentro trabajando en dicha integración.

Para detalles tecnicos del proyecto no duden en preguntarme :D
