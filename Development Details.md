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

> My main concerns were the `Cache` configuration and the `Containerization` in order guarantee the app to run correctly anywhere...
But what consumed the most time out of this stage was finding suitable open-source APIs that ***don't use an API Key***...

> I found out that this is somewhat rare nowadays... I could see in forum threads from 6+ years ago some recomendations, but usually they **now** require the use of an `API Key`...
I did find several APIs that were open-source, but most of them made use of `API Keys`

> I also initialized the project with [**__Sprig Initializr__**](https://start.spring.io), but on the next stage I had to do it again, because I didn't pay much attention to the compatibility between the chosen `Java Version` and the chosen `Spring Boot Version`, and the project wasn't building correctly...

---

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

> Extracted the `baseUrl` and `username` needed to use the `GeoNames API`

### Client-Connecting Tests
> ❌ ***Not recommended to be used on live Apps***
>
> Used only for ***developing purposes*** and testing of the `Client Class` and its `Configuration` during development... Later I ***disabled*** the `ClientTests` so that the application doesn't always call `external APIs` during `build`

> With more time to complete the task I would have done the correct `Unit Tests` for each of the `Classes` of the `dataflow` (`Services`, `Clients`, `Mapper`, `Assembler`) and the `Integration Test` starting at the `Controller`, ensuring the correct behavior of the complete `dataflow`

#### ✅ `GeoNamesClient` DONE

---

## **Stage 3**: `OpenMeteoClient` and dealing with **2** `RestClients`
### Implementing the second Client
> This part was very similar to the last `Client`, the classes were similar and also there was no real distinct business rule involved in this Client's `Service`

### Dealing with 2 `RestClients`
> Something I didn't know and learning from mistakes was that you need to have a unique configuration on each of the `RestClients`... I first assumed that the name would specify the differences, but the way I was doing before it was actually the same `RestClient` class that was being instantiated on each `APIClient`...
> So I learned to specify which `RestClient` was being called, configuring a specific `name` in the `ClientConfig` and specifying that in a `build method` for the `Client` Class, passing the `@Qualifier` annotation with the correct `name`...
This solved the issue and I proceeded to test the `Client` connection...

### Client-Connecting Tests
> ❌ Again, ***Not recommended to be used on live Apps***
> but for the same reason, I made a live connection test class to see if the api request and response were as expected

#### ✅ `OpenMeteoClient` DONE

---

## **Stage 4**: Main `dataflow` and `Cache`
### Main `dataflow`
> With both `Clients` and the surrounding `Data Classes` done, each being applied to the correct context, the main `dataflow` was very straightforward and was finished without too much trouble...
> The main point in this stage was creating the `Assembler` to assemble the final `Response`... I wanted to make something modular and unique to create all the possible `Responses` in a single class, according to the parameters received in the `Request`...
> I tested it by running it locally and sending requests to the ***WeatherAreYouApp*** `Controller` via `curl command` with the different parameters to ensure all the different outcomes.

#### ✅ Main `dataflow` DONE

### Implementing `Cache`
> As I mentioned, I wasn't familiar with `Cache` configuration, so I had to look this up and from the differnt options I found I decided to go with `Caffeine` as the `CacheManager`, as it's an `in-memory Cache` that for the scope and complexity of the project would suffice...
> I also learned the difference of the `Cache-Control` that could be applied to the `Controller` and how that would not result in a satisfactory behavior...
> The `Cache-Control` only allows the `Client` calling our application to ***store*** `cache` from our `Responses`, but that would be decided `Client-side` and depending on what tool would be used to make the call... making the calls from `curl commands`, for example, don't store the cache, so it won't work...
> So I realized I would have to implement 2 different `Cache Configurations`, similar to the `ClientConfigs` it would be needed to explicitly name and call which `CacheManager` was dealing with the information on that specific `Service`...
> I made the `CacheConfig Class` and instantiated 2 possible `CacheManagers`, one for each service, for the `GeoNamesService` I defined the TTL (*Time To Live*) as `24h` according to the idea that usually the same `ZIP Code` would result in the same `Coordinates`, so it would be necessary to actually call the `GeoNamesClient` again so soon if the same `ZIP Code` was provided... I wouldn't go so far as to say it would ***NEVER*** change the result of the call, as there might be some cases of wrong `Coordinates` to a `ZIP Code`, or maybe inexistant `Coordinates` to a `ZIP Code` that could later be included... so it would be wise to call it again once in a while to see if anything changed...

#### ✅ `Cache` DONE

### The `isFromCache` flag
> After looking things up for a while I learned that when data is retrieved from `Caffeine Cache` it is not flagged or sinalized in any way... so fetching the information of wether the data was acquired from `cache` or not would require some workarounds...
> The most common solution I found online was to search the `Cache` before actually fetching the data to see if it's there and, subsequentially, would be fetched from the `Cache` and setting the flag on that moment...
> I didn't like this approach as it would basically search the `Cache` twice, once to set the `isFromCache` and a second time to actually fetch the results... So I tried to come up with another solution...
> At first I thought of a `Singleton Class` that would store the information of the flag as `true` and if the `dataflow` ever went into the `Client` that flag would be switched to `false` and the `ResponseAssembler` would call that `Singleton` to check the flag and include it in the final `Response`...
> But, a `Singleton` is a unique `Class` in the whole ***Application Context***, meaning that if 2 `requests` were ever made at the same moment, they would share the same state of the `Singleton Class`, sharing the same flag state, despite them actually having the same behavior (fetching from `cache` or `source`), so it wasn't an option...
> I was about to give up and not complete this `isFromCache` part, when I learned of the `@RequestScope` annotation... it instantiates the marked `Class` ***once per request*** and its content is shared between your classes, during the `dataflow` of the `request`, destroying it afterwards and instantiating again once a new `request` is received... That was exactly what I was needing...
> Once the `request` arrives, the `WeatherCacheContext` is instantiated with the parameter `isFromCache = true`... during the `dataflow`, the `OpenMeteoService` marks `isFromCache = false` because if the `dataflow` ever reached the inside of the `OpenMeteoService` method it means that the information is going to be fetched from the `source`... in the end the `ResponseAssembler` calls the `WeatherCacheContext` and sends the `isFromCache` flag in the final response...
> This worked as expected and I went to sleep because it was already very late!

#### ✅ `isFromCache` on the final `Response` DONE

---

## **Stage 5**: Final touches and `Containerization`
### Customized `Exceptions`
> As I have already worked with customized `Exceptions` this wasn't too much trouble and I wanted to include this becuase I believe a good `Error Response` is key to a good ***User Experience***...
> Place 3 Customized `Exceptions` from possible recurring errors in the correct classes according to their ***responsabilities***.

### `Containerizations`
> I only ever used `Docker` as a `user`, so setting it up was a new experience for me... I searched around trying to find the best way to make running my app the easiest possible, and what I found was to completely `containeralize` the application... so I set up the `DockerFile` to initialize the process already using the `Java 17 image` and run the `gradle` commands to install dependencies and run the application at the end... this was the easiest way to set up the project to run basically anywhere, and wrote the `README.md` with instructions to check if `gradle` is installed and how to install if it's not...

### Finishing up the `README.md` and `Development Details.md`
> The last part was to finish writing down the information on the `README.md` and this `Development Details.md` with `Stack` information, instructions and details on how to call the application after it's running and the last details to register the development decisions, difficulties as to make it clearer my thought process and decision making...

### Sign the Project
> A `Signature Class` to print in the console the App name and Signature

#### ✅ `WeatherAreYouApp` DONE

---

## **What would I do with more time?**
### Full Coverage Tests
> `Unit tests` for every `dataflow` class and a full `Integration Test` so that every scenario can be tested and ensured to behavior as expected

### Input / Output Formatter
> Make sure the `ZIP Code` is always in the same format, as this would mean a more efficient `Cache` and avoid allowing bad formatting to not retrieve informations when they would be available if the entry was better written

### Own `ZIP Code` ➜ `Coordinates` Database
> Maybe instead of storing this information on `Cache` it could be store in a local `Database` the reason being that it would depend less on the `GeoNamesClient` that we use to provide a conversion that basically always results in the same output...
> As it would not work in `Cache` the information wouldn't be lost upon shutting down the app / closing the `container`