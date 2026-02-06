```
██     ██ ███████  █████  ████████ ██   ██ ███████ ██████                          
██     ██ ██      ██   ██    ██    ██   ██ ██      ██   ██                         
██  █  ██ █████   ███████    ██    ███████ █████   ██████                          
██ ███ ██ ██      ██   ██    ██    ██   ██ ██      ██   ██                         
 ███ ███  ███████ ██   ██    ██    ██   ██ ███████ ██   ██                         
                                          
                 █████  ██████  ███████     ██    ██  ██████  ██    ██     ██████  
                ██   ██ ██   ██ ██           ██  ██  ██    ██ ██    ██          ██ 
                ███████ ██████  █████         ████   ██    ██ ██    ██       ▄███  
                ██   ██ ██   ██ ██             ██    ██    ██ ██    ██       ▀▀    
                ██   ██ ██   ██ ███████        ██     ██████   ██████        ██    

┏┓ ╻ ╻    ┏━╸┏━┓┏━┓┏━┓╻┏ ╻
┣┻┓┗┳┛╹   ┃╺┓┃ ┃┣┳┛┗━┓┣┻┓┃
┗━┛ ╹ ╹   ┗━┛┗━┛╹┗╸┗━┛╹ ╹╹     
```
This API is responsible for returning a weather forecast based on a **ZIP Code**.

The service performs the following steps:

* **Converts a ZIP Code into geographic coordinates (latitude and longitude)**
* **Fetches a weather forecast based on those coordinates**
* **Uses an in-memory cache to avoid repeated calls to external services**
* **Offers additional information based on simple flags on the request**

The entire application is containerized, allowing it to be executed locally with minimal setup using `Docker`.

___

## Stack

| Technology           | Version / Details         |
| -------------------- | ------------------------- |
| **Java**             | `17`                      |
| **Spring Boot**      | `3.5.x`                   |
| **Gradle**           | `8.14.x (Wrapper)`        |
| **Cache**            | `Caffeine`                |
| **Containerization** | `Docker / Docker Compose` |

---

## **How to Run the Application**

### **Step 1**: Open a `Terminal` in the **Project Folder**

This can be achieved by either opening the `terminal` from a context menu inside the project folder directly, or starting any `terminal` console and getting to the project folder via `commands`

> ⚠️ All the commands below must be executed from this terminal.

---

### **Step 2**: Verify if `Docker` Is **Installed**

In the terminal, run the following command:

```bash
docker --version
```

If `Docker` is installed, you will see a version number (for example, `Docker version 26.x`).

If `Docker` is **not installed**, follow the instructions for your operating system:

* [Windows / macOS](https://www.docker.com/products/docker-desktop)

* [Linux](https://docs.docker.com/engine/install/)

After installing `Docker`, **restart your computer** and run `docker --version` again to confirm the installation.

---

### **Step 3**: `Build` and `Start` the **Application**

Once `Docker` is installed and the terminal is open in the project folder again, run:

```bash
docker compose up --build
```
What this command does:

* **Downloads** the required `Java` image
* **Builds** the application using `Gradle`
* **Creates** a `Docker` image
* **Starts** the application inside a `container`

After the process finishes, the application will be running and available at:

```text
http://localhost:8080
```
> You do **not** need to keep the terminal open for the application to continue running.

---

#### Example API Request (curl)

You can test the `API` by sending a `POST` request to it.
`Copy` and `paste` this `curl command` in your `terminal`, **editing** the fields to send the desired information.

```bash
curl -X POST http://localhost:8080/weather/zip \
  -H "Content-Type: application/json" \
  -d '{
    "zipCode": "14800000",
    "additionalInfo": false,
    "extendedForecast": false
  }'
```

**Request fields:**

* `zipCode` (`string`, required): **ZIP Code** used to determine the geographic location.
* `additionalInfo` (`boolean`, optional): When `true`, includes **additional temperature details**. Defaults to `false`.
* `extendedForecast` (`boolean`, optional): When `true`, returns **additional weather data for multiple days**. Defaults to `false`.

> ⚠️ The ZIP Code to coordinates conversion relies on an open-source geographic database. **Not all ZIP Codes are guaranteed to be available**, and some valid ZIP Codes may not return coordinates.

---

### **Step 4**: `Stop` the Application

When you want to `stop` the application, `run` the following command in the same project folder:

```bash
docker compose down
```

This will `stop` and `remove` the running `container`.

---

#### ⚠️ *Important information about the `Cache` used in this project*
> * The `cache` is **in-memory only**
> * When the `application` or `container` is *stopped*, **all cached data is lost**
> * This behavior is **intentional** and **expected**
> * This only means that `requests` made with the same information may have to be fecthed again, even if inside the Cache window (`24h` for **Coordinates** and `15min` for **Weather Forecast**)

---
#### 🛠️ [Development Details](https://github.com/YanGORSKI/weather-are-you-app/blob/main/Development%20Details.md)
---
