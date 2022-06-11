# Documentación del proyecto

### Composición del proyecto
- El proyecto hace uso de varias librerías orientadas a las respuestas HTTP. Para subir archivos vía API se usa la librería: net.gotev:uploadservice:4.7.0. Para descargar archivos y enviar peticiones GET y POST a la API se usa: com.android.volley:volley:1.2.1

### Persistencia de datos
- Cada dato personal del usuario es almacenado en memoria RAM durante la ejecución de la aplicación. La aplicación no almacenará ningún documento en la
memoria interna del dispositivo o SD. Pero los documentos que el usuario desee abrir, se descargarán de manera temporal para su posterior visualización por parte del usuario. Al visualizar un archivo, el usuario podrá incluso indicar con que aplicación desea abrirlo. En función del
tipo de MIME se le indicará la aplicación mas recomendable a usar.

### Desarrollo de la API
- La API es desarrollada bajo código PHP con la ayuda de una librería que ya estaban incorporada en el proyecto de Laravel actual. En concreto se ha usado la librería “Laravel Sanctum” la cual nos otorga una generación de tokens para cada usuario de manera automática, con posibilidad de identificar los datos del usuario con solo tener su token.

# Desarrollo de la APP
A continuación se mostrará las clases mas importantes de este proyecto y sus funciones.

### Clase App
- Esta es la clase inicial de la aplicación, en ella se encuentra una constante llamada “IP” la cual es la que alberga la dirección del host donde esta alojado actualmente el sitio web en el que se encuentra precisamente las funciones desarrolladas de la API. De esta forma en el caso de que el servidor se trasladara a otro dominio o por cualquier otra cosa. Solo hay que indicarle la nueva dirección del servidor para que la aplicación siga funcionando.

### Clase User
- Esta clase es la que representa al usuario como objeto. Sus propiedades como puede ser de esperar serían sus datos personales (Nombre, Apellidos, Email, etc). Después de iniciar sesión, la aplicación aprovecha la misma llamada de validación para recoger los datos personales del usuario para posteriormente crear una instancia de esta misma clase “User.java” en donde se almacenará en forma de objeto el conjunto de datos del usuario que acaba de iniciar sesión. Gracias al uso de la librería JSONArray y JSONObject de la API de Java es posible recoger la respuesta que la API nos manda en formato JSON y de una forma sencilla poder extraer sus datos.

### Clase Document
- Esta clase se encarga de representar cada uno de los documentos del usuario. Es utilizada unicamente junto con el RecyclerView para crear el listado de documentos.

### Clase Actions
- Esta clase representa los casos de uso por parte del usuario. Es decir es la clase que contiene todos los métodos relacionados con las acciones que el usuario hará disparar durante su actividad en la aplicación, normalmente pulsar botones, recargar una vista, o pulsar en algún item de la aplicación.
