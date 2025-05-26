# PPixel deme server

This is an example of a simple spring boot service that was created as a result of task described [here (RU)](./TASK_RU.md).

Only standard spring boot stack + postgres is used.

To run server: 
 - access to internet required (resolve dependencies while building)
 - docker must be available with docker-compose

Run next:
```bash
docker-compose build && docker-compose up -d --force-recreate && docker-compose logs -f server
```

For simplicity create alias:
```bash
alias dcup='docker-compose build && docker-compose up -d --force-recreate && docker-compose logs -f server'
dcup
```

To stop just spring boot server:
```bash
docker-compose stop server
```

Swagger available at a [link](http://localhost:8080/swagger-ui/index.html) when the server is running.


#### ⚠️ Why not to use reserved sql word `"user"` as a Table Name:

described [here](./USER_TABLE.md)
