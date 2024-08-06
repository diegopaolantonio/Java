# Proyecto Paolantonio

## Entities
  >Modelos de datos creados para el proyecto

- Client
    - id: Type Long, Autogenerado
    - Name: Type String
    - lastmane: Type String
    - docnumber: Type Integer (nullable = false, unique = true)

- Product
    - id: Type Long, Autogenerado
    - code: Type Integer (nullable = false, unique = true)
    - description: Type String
    - stock: Type Integer (nullable = false)
    - price: Type double (nullable = false)

- Cart
    - id: Type Long, Autogenerado
    - amount: Type Integer (nullable = false)
    - price: Type double (nullable = false)
    - executed: Type boolean (nullable = false)
    - client: Type Client
    - product: Type Product

- Invoice
    - id: Type Long, Autogenerado
    - total: Type double (nullable = false)
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

### Clients
**Ruta**: /api/v1/auth

- POST: "/register"
    : Para crear un nuevo client.
    : Body de la peticion: se debe enviar un json con los datos del client necesarios: "name", "lastname" y "docnumber"
    : "docnumber" no puede ser null y tiene que ser null 
    : example:

    ```
        {
            "name": "Ignacio",
            "lastname": "Rivera",
            "docnumber": 565555
        }
    ```
  
    - Respuestas
        : 201: Client created successfully. Client
        : 400: Bad request: typing error, 'docnumber' cannot be null or 'docnumber' already exists. void
        : 500: Internal server error. void

- GET: "/"
    : Para leer todos los clients de la base de datos.
    : Body de la peticion: nada
    
    - Respuestas
        : 200: Client retrives successfully. List<Client>
        : 500: Internal server error. void

- GET: "/id"
    : Para leer un client especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

    - Respuestas
        : 200: Client retrive successfully. Client
        : 404: Client not found. void
        : 500: Internal server error. void

- PUT: "/me"
    : Para actualizar un client especificado por el 'id' enviado en el body.
    : Body de la peticion: se debe enviar un json con los datos del client que se quieran actualizar: "id", "name", "lastname" y "docnumber"

    ```
        {
            "id": 15,
            "name": "Ignacio",
            "lastname": "Rivera",
            "docnumber": 565555
        }
    ```

    - Respuestas
        : 200: Client updated successfully. Client
        : 400: Bad request: typing error, 'docnumber' already exists. void
        : 404: Client not found. void
        : 500: Internal server error. void

- DELETE: "/:id"
    : Para eliminar un client especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

    - Respuestas
        : 200: Client deleted successfully. void
        : 404: Client not found. void
        : 500: Internal server error. void

### Products
**Ruta**: /api/v1/products

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

    - Respuestas
        : 201: Product created successfully. Product
        : 400: Bad request: typing error, 'code' cannot be null or 'code' already exists. void
        : 500: Internal server error. void

- GET: "/"
    : Para leer todos los products de la base de datos.
    : Body de la peticion: nada

    - Respuestas
        : 200: Products retrives successfully. List<Product>
        : 500: Internal server error. void

- GET: "/:id"
    : Para leer un product especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

    - Respuestas
        : 200: Product retrive successfully. Product
        : 404: Product not found. void
        : 500: Internal server error. void

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

    - Respuestas
        : 200: Product updated successfully. Product
        : 400: Bad request: typing error, 'code' already exists. void
        : 404: Product not found. void
        : 500: Internal server error. void

- DELETE: "/:id"
    : Para eliminar un product especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

    - Respuestas
        : 200: Product deleted successfully. void
        : 404: Product not found. void
        : 500: Internal server error. void

### Carts
**Ruta**: /api/v1/carts

- POST: "/:clid/:pid/:amount"
    : Para crear un nuevo cart, se deben enviar los datos: "client id", "product id" y "amount".
    : Body de la peticion: nada"
    : El "price" se toma automaticamente del "price" del "product" agregado.

    - Respuestas
        : 201: Cart created successfully. Cart
        : 404: Client or Product not found. void
        : 409: Conflict: Not enough stock available for the requested product. void
        : 500: Internal server error. void

- GET: "/"
    : Para leer todos los carts de la base de datos.
    : Body de la peticion: nada

    - Respuestas
        : 200: Carts retrives successfully. List<Cart>
        : 500: Internal server error. void

- GET: "/:id"
  : Para leer los cart de un cliente especificado por el 'id' enviado en la ruta.
  : Body de la peticion: nada

    - Respuestas
      : 200: Cart retrives successfully. List<Cart>
      : 404: Client not found. void
      : 500: Internal server error. void

- GET: "/:id/cart"
    : Para leer un cart especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

    - Respuestas
        : 200: Cart retrives successfully. Cart
        : 404: Cart not found. void
        : 500: Internal server error. void

- PUT: "/:id"
    : Para actializar un cart especificado por el 'id' enviado en la ruta.
    : Body de la peticion: se debe enviar un json con los datos del cart que se quieran actualizar: "amount", "client: {id}" y "product: {id}"
    : El "price" se actualizara con el "price" del "product" que tenga el cart o que se haya actualizado.

    ```
        {
            "amount": 24,
        }
    ```

    - Respuestas
        : 200: Cart updated successfully. Cart
        : 400: Bad request: typing error. void
        : 404: Cart not found. void
        : 409: Conflict: Cart already executed o not enough stock available for the requested product. void
        : 500: Internal server error. void

- DELETE: "/:id"
    : Para eliminar un cart especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

    - Respuestas
        : 200: Cart deleted successfully. void
        : 404: Cart not found. void
        : 500: Internal server error. void

### Invoice
**Ruta**: /api/v1/invoices

- POST: "/"
    : Para crear un nuevo invoice.
    : Body de la peticion: se debe enviar un json con los datos del invoice necesarios: "client: {id}"
    : El "total" se toma automaticamente de la multiplicacion entre el "amount" y "price" de cada cart que tenga el "client" especificado y la suma de todas estas multiplicaciones.
    : El "created_at" se toma automaticamente cuando se crea el invoice.

    ```
        {
            "id": 1
        }
    ```
  
    - Respuestas
        : 201: Invoice created successfully. Invoice
        : 400: Bad request: typing error. void
        : 404: Client not found. void
        : 409: Conflict: Carts not found for this Client or all are already executed. void
        : 500: Internal server error. void

- GET: "/"
    : Para leer todos los invoices de la base de datos
    : Body de la peticion: nada

    - Respuestas
        : 201: Invoice retrives successfully. List<Invoice>
        : 500: Internal server error. void
    
- GET: "/:id/invoice"
    : Para leer un invoice especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

    - Respuestas
        : 200: Invoice retrives successfully. Invoice
        : 404: Invoice not found. void
        : 500: Internal server error. void

- GET: "/:id"
  : Para leer un invoice de un client especificado por el 'id' enviado en la ruta.
  : Body de la peticion: nada

    - Respuestas
        : 200: Invoice retrives successfully. Invoice
        : 404: Client not found. void
        : 409: Conflict: no 'Invoice' found for this 'Client'. void
        : 500: Internal server error. void

- GET: "/:id/all"
  : Para leer todos los invoices de un client especificado por el 'id' enviado en la ruta.
  : Body de la peticion: nada

    - Respuestas
      : 200: Invoice retrives successfully. List<Invoice>
      : 404: Client not found. void
      : 500: Internal server error. void

- PUT: "/:id"
    : Para actualizar un invoice especificado por el 'id' enviado en la ruta.
    : Body de la peticion: se debe enviar un json con los datos del invoice que se quiera actualizar: "client: {id}"
    : El "total" se actualiza automaticamente de la multiplicacion entre el "amount" y "price" de cada cart que tenga el "client" especificado y la suma de todas estas multiplicaciones.
    : El "created_at" se actualiza automaticamente cuando se llama este metodo.

    ```
        {
            "total": 1000
        }
    ```

    - Respuestas
        : 200: Invoice updated successfully. Invoice
        : 400: Bad request: typing error. void
        : 404: Invoice not found. void
        : 500: Internal server error. void

- DELETE: "/:id"
    : Para eliminar un invoice especificado por el 'id' enviado en la ruta.
    : Body de la peticion: nada

    - Respuestas
        : 200: Invoice deleted successfully. void
        : 404: Invoice not found. void
        : 500: Internal server error. void
