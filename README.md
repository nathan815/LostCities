# Lost Cities

[![TravisCI Status](https://travis-ci.org/nathan815/LostCities.svg?branch=master)](https://travis-ci.org/nathan815/LostCities)
[![Codecov](https://codecov.io/gh/nathan815/LostCities/branch/master/graph/badge.svg)](https://codecov.io/gh/nathan815/LostCities)
[![Codacy](https://api.codacy.com/project/badge/Grade/410d3531a0ee4e7eb87a19b071f34f29)](https://www.codacy.com/manual/nathan815/LostCities?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nathan815/LostCities&amp;utm_campaign=Badge_Grade)

Online version of the Lost Cities game built with Java, Spring Boot, and VueJS.

## Set Up
First, clone the repository: `git clone https://github.com/nathan815/LostCities`

### Backend
Requires JDK 11+ (`javac -version`). Either OpenJDK or Oracle JDK.

`cd backend`

🛠Compile: `./gradlew build`

▶️Run: `./gradlew bootRun`

### Frontend
Requires [NodeJS 12+](https://nodejs.org/en/download/) and npm (node package manager, included with NodeJS).

`cd frontend`

🛠Install dependencies: `npm install`

▶️Run: `npm run serve`

⚡️Production Build: `npm run build`

After running the serve command, frontend Webpack Dev Server should be up and running on http://localhost:8088. It is configured to automatically proxy API requests to `localhost:8088/api/*` to the backend server running on port 8089.

## History
My summer 2018 internship mentor Derek and I started this back in August 2018 as a side-project, but we only worked on it for a few weeks. In December 2019, while reading some articles about software design, I regained interest in this project and decided to apply some things that I've learned since then. 

Some recent changes include adopting a domain driven structure/design, improving or redesigning various classes and components, adding a bunch of unit tests, adding JaCoCo code coverage reports, setting up Codacy and Codecov, and building the frontend with TypeScript 🚀

## Technical Details

### Backend
The backend is written in Java, utilizing Spring Boot for the REST API and websocket connections. The domain/business logic is decoupled from Spring &mdash; my goal with this was to learn how to write more testable code decoupled from framework code. The overall structure is inspired by my basic understanding of Domain Driven Design, but it's lacking a lot of the principles. Improvements will be made over time. Feel free to submit an issue or PR if you see something to be improved!

#### High Level Overview

```
+----------------------+     +---------------------+     +----------------+
|                      |     |                     |     |                |
|  Presentation Layer  +---->|  Application Layer  +---->|  Domain Layer  |
|                      |     |                     |     |                |
+----------------------+     +---------------------+     +----------------+
                                                                 ^
                                                                 |
                                                    +------------+--------+
                                                    |                     |
                                                    |  Persistence Layer  |
                                                    |                     |
                                                    +---------------------+
```
*An arrow denotes a dependency: a layer may only reference objects from the layer(s) it depends on. Diagram created with [asciiflow](http://asciiflow.com/).*

- **Presentation Layer** (web package): This is where all the web API and websocket controllers are. The "presentation" is in the form of JSON returned from the API, and STOMP messages sent to subscribed websocket clients via Spring Messaging. Controllers in the presentation layer interact with services in the application layer to complete their tasks.

- **Application Layer** (application package): Application services live here. Application services primarily take in as input and return updated Data Transfer Objects (DTOs) - representations of the domain objects appropriate for 'public' consumption. Application services interact with the domain layer. Typically, the application services call some method from a repository (interface, defined in domain layer) to obtain an instance of a domain object hydrated with data.

- **Domain Layer** (domain package): This is where the core game logic lives. It is standard Java code with no dependencies on other layers or Spring. This layer contains domain entities modeled after the domain (Lost Cities), and the entities contain all the logic necessary for game play. The majority of this project's unit tests test the code here. Repository interfaces are also defined here to allow depending layers to call repositories. (Generally, only application services call the repositories.)
    - <sub><sup>Some entities contain JPA (Java Persistence API) annotations to enable the persistence layer to persist them. Additionally, in some domain entities, there are private methods annotated with 'PostLoad', 'PrePersist' and 'PreUpdate' JPA lifecycle annotations to enable restoration of their state.</sup></sub>

- **Persistence Layer** (persistence package): Implementations of repositories are here, where actual interactions with the database or other persistent data stores happen. (The repository interfaces are defined in the domain layer.) This is considered "infrastructure". No other layers depend on this layer.

### Frontend

The frontend is written in TypeScript and unitizes the Vue.js library. The Vuex stores are written using the vuex wrapper [vuex-typex](https://github.com/mrcrowl/vuex-typex) to enable TypeScript compile-time type safety. Some state is stored in the Vuex store, but the game state of the game currently being viewed is stored in the `GamePlay` component. No other pages need access to the entire game state, so Vuex would be overkill for storing it.

Frontend uses [rx-stomp](https://www.npmjs.com/package/@stomp/rx-stomp) for STOMP over Websocket communication for sending game commands and receiving game state from backend in realtime. rx-stomp uses RxJS so observables can be subscribed to for STOMP queues/topics.
