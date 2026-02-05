# Weather Are You App
> A simple API Service that receives a ZIP Code as a request and returns the Weather forecast in that location.
___
### Development
#### Planning
Simple `MVC` app with a `ForecastFromZipController` to receive the `Request`, a `Service` to work the data and `HTTP Clients` to call `external APIs` in order to fetch the information.

- **ForecastFromZipController** | Receives the ZIP Code as the Request
<p></p>

- **forecastFromZipService** | Deals with the information on the request and calls specialized Services to complete the workflow. Cache will be applied for 15 minutes for each same ZIP Code
<p></p>

- **geoCodingService** | takes the ZIP Code and calls a geoCodingClient to fetch the Coordinates according to that ZIP Code.
<p></p>

- **geoCodingClient** | Calls an open source geoCoding API through a HTTP Request to convert the ZIP Code into Coordinates
<p></p>

- **weatherService** | Takes the Coordinates and calls a weatherClient to fetch the Forecast in the area represented by the Coordinates. 
<p></p>

- **weatherClient** | Calls an open source Forecast API through a HTTP Request using the Coordinates and receives the response.
<p></p>

##### Data Flow
- ForecastFromZipController (IN)
> HTTP Request > ForecastFromZipController > RequestObj > zipDTO > forecastFromZipService
- GeoCodingAPI
> zipDTO > geoCodingService > geoCodingRequest > geoCodingClient > geoCodingResponse > geoCodingService > coordinatesDTO > forecastFromZipService
- ForecastAPI
> coordinatesDTO > forecastService > forecastRequest > forecastClient > forecastResponse > forecastService > forecastDTO > forecastFromZipService
- ForecastFromZipController (OUT)
> forecastFromZipService > forecastDTO > forecastFromZipResponse > ForecastFromZipController > External Client

#### APIs Needed
geoCoding: https://www.geonames.org/export/web-services.html
forecast: https://open-meteo.com

