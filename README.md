# Proyecto Paolantonio

## Entities
  >Modelos de datos creados para el proyecto

- Client
    - id: Type Long, Autogenerado
    - Name: Type String
    - lastmane: Type String
    - docnumber: Type Integer

- Product
    - id: Type Long, Autogenerado
    - code: Type Integer
    - description: Type String
    - stock: Type Integer
    - price: Type double

- Cart
    - id: Type Long, Autogenerado
    - amount: Type Integer
    - price: Type double
    - client: Type Client
    - product: Type Product

- Invoice
    - id: Type Long, Autogenerado
    - total: Type double
    - created_at: Type LocalDateTime
    - client: Type Client

## Repositories
  >Se definieron las interfaces con JPA para cada entidad.

## Services
  >Se definieron los servicios para el CRUD de cada repositorio:

- **save**
: Para crear o actualizar.
: Input: una variable del tipo de la entidad correspondiente.
: Output: una variable del tipo de la entidad correspondiente.

- **readAll**
: Para leer la base de datos.
: Input: No recibe nada
: Output: devuelve una List<Entidad> de la entidad correspondiente.

- **readById**
: Para leer un dato especificado por el 'id'.
: Input: una variable tipo Long correspondiente al 'id' del dato requerido.
: Output: una variable Optional<Entidad> del tipo de la entidad correspondiente, y sera null si no se encuentra el dato, y sera del tipo de la entidad correspondiente si el dato existe.

- **delete** 
: Para elminiar un dato especificado por el 'id'.
: Input: una variable tipo Long correspondiente al 'id' del dato requerido.
: Output: No devuelve nada.

## Controllers
  >Se definieron las siguientes rutas para cada servicios:

### Client
**Ruta**: /api/v1/clients

- POST: "/"
    : Para crear un nuevo client.
    : Body de la peticion: se debe enviar un json con los datos del client necesarios: "name", "lastname" y "docnumber"
    : example:

    ```
        {
            "name": "Ignacio",
            "lastname": "Rivera",
            "docnumber": 565555
        }
    ```

- GET: "/"
    : Para leer todos los clients de la base de datos.
    : Body de la peticion: nada

- GET: "/id"
    : Para leer un client especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

- PUT: "/:id"
    : Para actualizar un client especificado por el 'id' enviado en la ruta.
    : Body de la peticion: se debe enviar un json con los datos del client que se quieran actualizar: "name", "lastname" y "docnumber"

    ```
        {
            "name": "Ignacio",
            "lastname": "Rivera",
            "docnumber": 565555
        }
    ```

- DELETE: "/:id"
    : Para eliminar un client especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

### Product
**Ruta**: /api/v1/product

- POST: "/"
    : Para crear un nuevo product.
    : Body de la peticion: se debe enviar un json con los datos del product necesarios: "code", "description", "stock" y "price"

    ```
        {
            "code": 123456,
            "description": "Producto nuevo",
            "stock": 9,
            "price": 90.0
        }
    ```

- GET: "/"
    : Para leer todos los products de la base de datos.
    : Body de la peticion: nada

- GET: "/:id"
    : Para leer un product especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

- PUT: "/:id"
    : Para actualizar un product especificado por el 'id' enviado en la ruta.
    : Body de la peticion: se debe enviar un json con los datos del product que se quieran actualizar: "code", "description", "stock" y "price"

    ```
        {
            "code": 123456,
            "description": "Producto nuevo",
            "stock": 9,
            "price": 90.0
        }
    ```

- DELETE: "/:id"
    : Para eliminar un product especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

### Cart
**Ruta**: /api/v1/carts

- POST: "/"
    : Para crear un nuevo cart.
    : Body de la peticion: se debe enviar un json con los datos del cart necesarios: "amount", "client: {id}" y "product: {id}"
    : El "price" se toma automaticamente del "price" del "product" agregado.

    ```
        {
            "amount": 1,
            "client": {
                "id": 120
            },
            "product": {
                "id": 6
            }
        }
    ```

- GET: "/"
    : Para leer todos los carts de la base de datos.
    : Body de la peticion: nada

- GET: "/:id"
    : Para leer un cart especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

- PUT: "/:id"
    : Para actializar un cart especificado por el 'id' enviado en la ruta.
    : Body de la peticion: se debe enviar un json con los datos del cart que se quieran actualizar: "amount", "client: {id}" y "product: {id}"
    : El "price" se actualizara con el "price" del "product" que tenga el cart o que se haya actualizado.

    ```
        {
            "amount": 24,
            "client": {
                "id": 10
            },
            "product": {
                "id": 1
            }
        }
    ```

- DELETE: "/:id"
    : Para eliminar un cart especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

### Invoice
**Ruta**: /api/v1/invoices

- POST: "/"
    : Para crear un nuevo invoice.
    : Body de la peticion: se debe enviar un json con los datos del invoice necesarios: "client: {id}"
    : El "total" se toma automaticamente de la multiplicacion entre el "amount" y "price" de cada cart que tenga el "client" especificado y la suma de todas estas multiplicaciones.
    : El "created_at" se toma automaticamente cuando se crea el invoice.

    ```
        {
            "client": {
                "id": 1
            }
        }
    ```

- GET: "/"
    : Para leer todos los invoices de la base de datos
    : Body de la peticion: nada

- GET: "/:id"
    : Para leer un invoice especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

- PUT: "/:id"
    : Para actualizar un invoice especificado por el 'id' enviado en la ruta.
    : Body de la peticion: se debe enviar un json con los datos del invoice que se quiera actualizar: "client: {id}"
    : El "total" se actualiza automaticamente de la multiplicacion entre el "amount" y "price" de cada cart que tenga el "client" especificado y la suma de todas estas multiplicaciones.
    : El "created_at" se actualiza automaticamente cuando se llama este metodo.

    ```
        {
            "client": {
                "id": 1
            }
        }
    ```

- DELETE: "/:id"
    : Para eliminar un invoice especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada
