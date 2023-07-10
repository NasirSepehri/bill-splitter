### Bill Splitter

Bill Splitter is a simple web app that helps you split bills with your friends.
You can enter the total amount, the number of people, and the app will calculate how much each person should pay.

### Requirement

For run application you need to the _Postgres_ database.
You can use below docker-compose.yml.

```yaml
version: '3.9'

services:
  postgres_bill_splitter:
    image: postgres:15.3
    container_name: postgres_bill_splitter
    restart: always
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: postgres
#      POSTGRES_USER: ${POSTGRES_PROD_USER}
      POSTGRES_PASSWORD: postgres
#      POSTGRES_PASSWORD: ${POSTGRES_PROD_PASSWORD}
    volumes:
      - /docker/data/postgres/:/var/lib/postgresql/data
    networks:
      - backend
```

### Build

You can use the `Maven` command to build the project.

```bash
mvn clean package

```

### Test

You can use the `Maven` command to run the tests.

```bash
mvn clean test -Dspring.profiles.active=test
```

FYI: You must set active profile to the `test`.

### Swagger

Swagger is a suite of tools for API developers from SmartBear Software and a former specification upon which the OpenAPI
Specification is based.

For access to the `Swagger` page you can use below URL:

```text
http://localhost:8080/swagger-ui/index.html
```

<p>
<img src=".graphics/bill-splitter.png" width="1537" height="935" alt="swagger">
</p>

### usage


