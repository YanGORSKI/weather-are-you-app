# Development
## **Stage 1**: Planning
Simple `MVC` app with a `ForecastFromZipController` to receive the `Request`, a `Service` to work the data and `HTTP Clients` to call `external APIs` in order to fetch the information.

#### Controller
> Receives the ZIP Code as the Request

#### ForecastFromZipService
> Deals with the information on the request and calls specialized Services to complete the workflow. `Cache` will be applied for `15 minutes` for each same ZIP Code

#### GeoCodingClient
> Calls an [open source geoCoding API](https://www.geonames.org/export/web-services.html) through a `HTTP Request` to convert the `ZIP Code` into `Coordinates`

#### WeatherClient
> Calls an [open source Forecast API](https://open-meteo.com) through a `HTTP Request` using the `Coordinates` and receives the `Response` with weather forecast information.

#### APIs Needed
geoCoding: https://www.geonames.org/export/web-services.html
forecast: https://open-meteo.com

> In this first stage I took an overview on what would be needed for the project and did some initial research on topics I wasn't too familiar with...
My main concerns were the `Cache` configuration and the `Containerization` in order guarantee the app to run correctly anywhere...
But what consumed the most time out of this stage was finding suitable open-source APIs that ***don't use an API Key***...
I found out that this is somewhat rare nowadays... I could see in forum threads from 6+ years ago some recomendations, but usually they **now** require the use of an `API Key`...
I did find several APIs that were open-source, but most of them made use of `API Keys`
I also initialized the project with [**__Sprig Initializr__**](https://start.spring.io), but on the next stage I had to do it again, because I didn't pay much attention to the compatibility between the chosen `Java Version` and the chosen `Spring Boot Version`, and the project wasn't building correctly...

## **Stage 2**: **Project** `Base` and `GeoNamesClient`
### `Package` naming
> I tried to follow a very simple naming structure, separating classes by their type/function rather then by context...

```
...app/controllers/ControllerA.java
...app/controllers/ControllerB.java
...app/services/ServiceA.java
...app/services/ServiceB.java
...and so on
``` 

> if this would be an application with *multiple contexts* or *functions*, I believe it would be better to name things from context and then function:

```
...app/weatherFromZip/services/ServiceA.java
...app/weatherFromZip/services/ServiceB.java
...app/otherService/services/ServiceA.java
...app/otherService/services/ServiceB.java
...and so on
```

### Single Responsability Classes
> Despite being a fairly simple `dataflow`, I tried my best to separate `classes` and respect the `Single Responsability Principle`, having in mind `readability`, `scalability` and `maintainability`...

- `Controllers` deal with the ***http requests***
- `Services` deal with ***business rules***
- `Clients` deal with ***external api calls***
- `Data Classes` specific to what each ***context*** need

> Clear purpose classes and function-directed names makes reading the code almost it's own documentation... very clear understanding of `what` the code is doing, `where` it's doing and `how` it's doing it.

### Extracting Configs
> Again, thinking about the project growing bigger, with other purposes, Web Clients and different configuration, I tried to extract re-usable properties with a `application.yml` (more used to the `yaml` style then the `properties` one)
Extracted the `baseUrl` and `username` needed to use the `GeoNames API`

### Client-Connecting Tests
> ❌ ***Not recommended to be used on live Apps***
>
> Used only for ***developing purposes*** and testing of the `Client Class` and its `Configuration` during development... Later I ***disabled*** the `ClientTests` so that the application doesn't always call `external APIs` during `build`
>
> With more time to complete the task I would have done the correct `Unit Tests` for each of the `Classes` of the `dataflow` (`Services`, `Clients`, `Mapper`, `Assembler`) and the `Integration Test` starting at the `Controller`, ensuring the correct behavior of the complete `dataflow`

## **Stage 3**: 