# packer-app-api (Compacked)

### Webapp live @ https://compacked.herokuapp.com/
### Webapp repo @ https://github.com/obstrom/packer-app

## About

API for calculating and finding the optimal packing solution from a range of packages/boxes and products.
Uses the [LAFF (Largest Area Fit First) bin packing algorithm](https://www.parkbeachsystems.com/images/usps/An_Efficient_Algorithm_for_3D_Rectangular_Box_Packing.pdf) to optimize packing solution.

This is part of my thesis project at Stockholms Tekniska Insitut.

Built using Java 17 and Spring Boot.
Relies on a bin packing implementation from [3d-bin-container-packing](https://github.com/skjolber/3d-bin-container-packing).

## Instructions

**If you wish to clone or fork and run this project yourself, this is what you need to know.**

This project is ment to serve as a private api for [this webb app](https://github.com/obstrom/packer-app). Thus it takes in a few enviroment variables to set up default API keys.

### Enviroment variables
| Key                                             | Value                             | Description |
| ----------------------------------------------- | --------------------------------- | ---- |
| application.http.auth-api-key                   | X-API-KEY                         | HTTP Header for API key, recommended to use "X-API-KEY" |
| application.http.auth-api-value                 | some-password-like-value          | Unique API key value |
| application.http.cors.allowed-origin-primary    | http://localhost:3000             | URL to allow CORS |
| application.http.cors.allowed-origin-secondary  | https://api-is-deployed-here.com  | Secondary URL to allow CORS |

These variables can be set in your [IDE as Enivorment Variables for the run configuration](https://education.launchcode.org/gis-devops/configurations/02-environment-variables-intellij/index.html), or by adding them to `application.yml` (or a new local application properties file).

### API endpoints

When using the main API endpoints you'll need to supply the API key in the header of the request. 
In Postman this can be done by selecting `API Key` under the tab `Authorization` for the request and adding the `Key` and `Value` you setup in the enviroment variables.

| Method | Endpoint | Security | Body | Description |
| ------ | -------- | -------- | ---- | ----------- |
| `GET` | `/actuator/health` | None | | Monitor API health status |
| `POST`| `/api/v1/packer/pack` | X-API-KEY | *See request body details below* | Run packing job on request |

#### Packer request body:


```
{
  "lengthUnitType": (enum) "metric_millimeter" | "meteric_centimeter" | "metric_meter",
  "weightUnitType": (enum) "metric_gram" | "metric_kilogram"
  "boxes": [
    {
      "id": number,
      "description": string,
      "width": number (integer),
      "depth": number (integer),
      "height": number (integer),
      "weight": number (integer),
      "maxLoad": number (integer)
    },
    ...
   ],
   "products": [
    {
      "id": number,
      "description": string,
      "width": number (integer),
      "depth": number (integer),
      "height": number (integer),
      "weight": number (integer),
      "allowRotation": boolean,
      "quantity": number (integer)
    },
    ...
   ]
} 
```

### Running local install

1. Git clone or download the repo
2. Make sure you have Java 17 setup and installed (either on your machine or in your IDE)
3. Set up the Enviroment Varibles (as seen above)
4. Run in your either, or build and run by using `./gradlew build` from the terminal in the root of the project
5. Test that it works by doing a GET request or in your browser navigating to `http://localhost:8080/actuator/health`
6. Ready to use the API locally

