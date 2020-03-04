# The Avid Video Games review blog

> Quickstart dev env: `screen -d -m -S avid "./gradlew -x webpack" && npm start`

## {A} Development

### Configuring your local dev environment

> Before you can build this project, you must install and configure the following dependencies on your machine:

#### 1. Install dev dependencies:

i. docker and docker-compose

ii. Java Development Kit

iii. [Node.js](https://nodejs.org/en/): Using Node to run the dev web server for the frontend and build the frontend React SPA (npm scripts + webpack)

#### 2. Run setup scripts

Run `npm install` after having installed Node, this command will install some of the project's development tools.

> \_\_**NOTE**: remember to also run this command when dependencies change in [package.json](package.json).

### Running in development

Run the following commands in separate terminal windows to start hot-reloading dev servers for the frontend/backend:

##### FRONTEND: `./gradlew -x webpack`

##### BACKEND: `npm start`

### Appendix: Simplify REST using swagger-editor and openapi-generator

Run the included docker-compose file to spawn [Swagger-Editor](), exposed on post [:7742](http://localhost:7742)

```
docker-compose -f src/main/docker/swagger-editor.yml up -d
```

Edit the `src/main/resources/swagger/api.yml` definition file.

- Code-generate API classes based on new/updated schema by running: `./gradlew openApiGenerate` ([OpenAPI-Generator]())
  - implements delegate classes with `@Service` classes

---

## {B} Building for production

### Building the prod docker image

Build a docker image of the monolith

    ./gradlew bootJar -Pprod jibDockerBuild

### Booting up the prod service

    docker-compose -f src/main/docker/app.yml up -d

---

## {C} Testing and Deployment

\*\***NOTE**: Using the Jib plugin for Gradle to build our docker image

- Learn more:
  - https://cloud.google.com/blog/products/gcp/introducing-jib-build-java-docker-images-better
  - https://www.baeldung.com/jib-dockerizing

### Task 1. API integration tests

Launch backend tests:

    ./gradlew test integrationTest jacocoTestReport

### Task 2. Frontend tests

Unit tests are run by Jest and written with Jasmine. They're located in [src/test/javascript/](src/test/javascript/) and can be run with:

    npm test

### Task 3. Sonar analysis of code quality

Start Sonar server locally (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Once Sonar's up > run a Sonar analysis with either the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the gradle plugin.

```
./gradlew -Pprod clean check jacocoTestReport sonarqube
```

### Appendix: Packaging as jar to run via bash script

To build the final `jar` and optimize the avid application for production, run:

    ./gradlew -Pprod clean bootJar

This will concatenate and minify the client CSS and JavaScript files. It will also modify `index.html` so it references these new files.
To ensure everything worked, run:

    java -jar build/libs/*.jar

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

\*\***NOTE** the project can also be packaged as a `war` file if we'd want to deploy it to an application server.

    ./gradlew -Pprod -Pwar clean bootWar
