# challenge

# Tenpo Challenge API


* [Overview](#overview)
* [Deployment Overview](#deployment-overview)
* [Docker](#docker)

## Overview
Nombre      | URL                              
----------|----------------------------------
Calculate  | POST /calculate                  
Get Request | GET /calculate-request           


Postman collection de la API en [Postman collection](src/test/resources/templates/tenpo-challenge.postman_collection.json)

## Deployment:

La aplicación está desarrollada utilizando Java, Spring, Postgres y Redis
- Postgres es utilizado para mantener la información de los request recibidos para el calculo
- Redis es utilizado para una cache de 30 min que guarda el valor del porcentaje y para guardar el ultimo valor utilizado
- La aplicacion consume un servicio externo de beeceptor para obtener el porcentaje

## Docker

La app está desarrollada para correr sobre un contenedor Docker.
El docker-compose.yml en la raíz del proyecto esta preparado para orquestar un docker network con la imagen
descargada desde el docker-hub.
Para ejecutar la aplicación se debe descargar la docker-compose image

```
docker pull chanochambers/tenpo:v0
```
y luego en un directorio que contenga el docker-compose.yml ejecutar

 ```
  docker-compose up 
 ```

