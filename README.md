# Lost Cities

[![TravisCI Status](https://travis-ci.org/nathan815/LostCities.svg?branch=master)](https://travis-ci.org/nathan815/LostCities)
[![Codecov](https://codecov.io/gh/nathan815/LostCities/branch/master/graph/badge.svg)](https://codecov.io/gh/nathan815/LostCities)
[![Codacy](https://api.codacy.com/project/badge/Grade/410d3531a0ee4e7eb87a19b071f34f29)](https://www.codacy.com/manual/nathan815/LostCities?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nathan815/LostCities&amp;utm_campaign=Badge_Grade)

Online version of the Lost Cities game built with Java, Spring Boot, and VueJS.

## Run Locally
First and foremost, clone the repository `git clone https://github.com/nathan815/LostCities`

### Backend
Ensure you have JDK 13 installed (`java -v`). Either OpenJDK or Oracle JDK should work.

Then compile the backend code and start the application.
```
cd backend
./gradlew build
./gradlew bootRun
```

### Frontend
Ensure you have nodejs 12 installed. npm is included with nodejs. 

Then install dependencies and start the dev server.
```
cd frontend
npm install
npm start
```

Frontend should now be up and running on port 8088. The Webpack Dev Server is configured to automatically proxy requests to `localhost:8088/api/*` to the backend server running on port 8089.

## History
My summer 2018 internship mentor Derek and I started this back in August 2018 as a side-project, but we only worked on it for a month or so. In December 2019, while reading some articles about software design, I regained interest in this project and decided to apply some things that I've learned since then. 

Some recent changes include adopting a more domain driven structure/design, improving or redesigning various classes and components, adding many more unit tests, adding JaCoCo code coverage reports, setting up Codacy and Codecov, and rewriting the frontend with TypeScript.

## Technical Details
[WIP]

The backend is written with Java and uses Spring Boot. But, the domain/business logic is completely decoupled from Spring. It's just plain Java code that could be hooked up to any number of interfaces. This is the goal.

The frontend is built with Vue. I recently converted the frontend code from JavaScript to TypeScript and adopted a type safe way to write Vuex store modules using the awesome [vuex-typex](https://github.com/mrcrowl/vuex-typex) wrapper. It allows one to easily write functions that call vuex actions/mutations/getters to enable compile time type safety via TypeScript, IDE autocompletion, and more. See `frontend/store/modules`.
