## Multi-Datasource error example using Micronaut 3.4.1

### Problem Statement
When using a non-default datasource, if an entity is fetched
using a Micronaut-Data generated method (i.e. marked with 
`@Executable` annotation) then an 
`org.hibernate.LazyInitializationException` is thrown when 
that entity's Lazy Loaded child entity is attempted to load.

My use case is I want to fetch entities in a read-only
transaction using a read-only instance of the database, 
but accessing Lazy Loaded child entities throws an exception.

I'm assuming this error is caused because the Lazy Loaded 
entity is fetched using the `default` datasource but I'm not 
too sure.

### Env Setup
#### Database
Use a SQL Database but if you don't have one you run the 
commands in `src/main/resources/dbCreation.sh`. Those 
commands will pull a Docker Image of MySQL 5.7 and start 
a container. The username/password for this container are
the same as found in the `application.yml`

### Creating DB Schema
Use the SQL statements found in 
`src/main/resources/script.sql` to create the schema and the
tables.

### The Server and API
If the instructions above were used then the server should 
be able to boot without any errors.

An easy way to call the API is to import 
`src/main/resources/Micronaut_Data.postman_collection.json`
into PostMan.

1. Create a resource using the `POST /entity` endpoint
2. Fetch the entity using the `GET readOnlyExecutable/{id}` endpoint. This will fail.
3. Fetch the entity using the `GET readOnlyEntityManager/{id}` endpoint. This will succeed, but it will fail if the repository is marked with `@TransactionalAdvice`.
4. Fetch the entity using the other endpoints, they will all succeed.

### Implementation Details
Notice in the `application.yml` there are two Data Source;
`default` and `readonly`, and there also to JPA sections.

Also notice `Repository.kt` has a `RepositoryProvider` which
returns an implementation of `ParentRepository` depending 
on if the transaction is read-only or not.