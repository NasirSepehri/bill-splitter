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

Create a client by `/auth/sing-up` URL:
<p>
<img src=".graphics/sign-up.png" width="1537" height="935" alt="swagger">
</p>

This API return a Token ID that you must send it with evey request. Add this token with `Bearer ` prefix and add it to
http header with `Authorization` key.

```text
Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJOYXNpclNlcGVocmkiLCJpc3MiOiJhcHAiLCJpYXQiOjE2ODg5OTAwNzQsImV4cCI6MTY4ODk5MTI3NH0.X3moZDbym6K6XObxQ_wnGXHvc-aAGe-cvpoQj1Scq1QExVwkxmtc9I0nUyVElzVkWwicWaYcnv8X8HTQAjmX0A
```

In Swagger page you must add this token to without `Bearer` like below:
<p>
<img src=".graphics/auth.png" width="1537" height="935" alt="swagger">
</p>

After sign up you can use `/auth/sign-in` URL to get Token ID by your `username` and `password`.

Then add an Event by Post request with the `/event` URL.
Then you can add costs by Post request with the `/cost` URL.

At last by Get request with the `/cost/payments` URL and `evnetId` application calculate how much each person should
pay. 
