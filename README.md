# Spring Boot AI Proof of Concept

This project is a proof-of-concept for using Spring Boot AI.

It uses Ollama to summarize PDF documents.

## Prerequisites

The Ollama model needs a lot of RAM, so make sure to close any unnecessary applications.

## Getting started

You can start the application using the following command:

```shell
mvn spring-boot:run
```

All necessary dependencies will automatically start with the application in the form of Dockerized containers.

Once the application starts, all endpoints will become available at
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

Bear in mind that it can take a very long time for the LLM to generate a response.

## Shortcomings

The current generation of LLMs is prone to hallucinations and therefore the generated summaries cannot be trusted
in a context where their accuracy is paramount.

A relatively tame example of misinterpretation which I came across is the LLM insisting that no information
about education was provided in the document, when fed a CV where this information was displayed prominently.

## Ideas for improvement

The LLM used is a general-purpose LLM.
A version tuned specifically for interpreting documents might yield better results.

## TODO

- Clean up docker-compose file
- Move `ollama/ollama-webui` volume to a folder more isolated from the code
- Add screenshot of the application being use
- The controller should only accept PDF documents
- Extract logic from `SummarizePdfController` into a service
- Add tests
- Ollama `entrypoint.sh` should wait for Ollama to start instead of sleeping 5 seconds
- Ollama healthcheck should wait for llama3 to be downloaded
- Document size limit should not be allowed to exceed what the LLM can interpret