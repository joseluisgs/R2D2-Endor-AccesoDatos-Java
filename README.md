# R2D2 y las Lunas de Endor - AccesoDatos-2021-2022
Ejemplo del ejerccio de R2-D2 usado en clase.

[![Java](https://img.shields.io/badge/Code-Java%20v11-blue)](https://www.java.com/es/)
[![LISENCE](https://img.shields.io/badge/Lisence-MIT-green)]()
![GitHub](https://img.shields.io/github/last-commit/joseluisgs/R2D2-Endor-AccesoDatos)


![imagen](https://cdnb.artstation.com/p/assets/images/images/016/922/017/large/serhii-sirenko-r2-art-3.jpg)

- [R2D2 y las Lunas de Endor - AccesoDatos-2021-2022](#r2d2-y-las-lunas-de-endor---accesodatos-2021-2022)
  - [Enunciado](#enunciado)
  - [Autor](#autor)
    - [Contacto](#contacto)
  - [Licencia](#licencia)

## Enunciado
Nuestro amigo R2-D2 ha sido enviado junto a Luke Skywalker debido a una serie de problemas relacionados con la contaminación en la Luna de Endor. Es por ello que mientras Luke se queda investigando, nuestro gran “Arturito” ha usado todos sus sensores para obtener datos del entorno y enviarlos al Halcón Milenario para ser procesados.

Para ello R2-D2 ha tomado muestras en dos ficheros. Data03.csv y Data03.xml. Pero se ha dado cuenta que el primero de ellos debido a una tormenta eléctrica ha quedado corrupto, por lo que debe completar las mediciones antes de procesarlas con el segundo, solo si esa muestra no existe en base a su identificador.

De las muestras nos interesan solamente:

- Identifier: identificador de la muestra
- Modified: Fecha de la medición
- NO2: concentración de dióxido de azufre
- Temperature: temperatura
- CO: concentración de carbono
- Ozone: concentración de ozono

Todas estas mediciones las meterá en una lista de memoria para poder procesarlas. Para leer el XML se recomienda JDOM

R2-D2, inicia el procesado de los datos para obtener unos resultados. Para ello nos interesa:
- Nivel máximo, mínimo y media de NO2 y la fecha en la que se registro.
- Nivel máximo, mínimo y media de  Temperatura y la fecha en la que se registro.
- Nivel máximo, mínimo y media de Ozono y la fecha en la que se registro

Una vez que ha procesado los datos usando API STREAM (¡¡¡R2-D2 es la leche para eso!!!), pero los vamos a procesar de 25 en 25, y finalmente los que sobren. realizando los cálculos necesarios en esos 25 registros y salvándolos en una base de datos de registros. 
Así hasta que los procesemos todos en grupos de 25, aunque el último puede que tenga menos.

Cada vez que R2-D2 termine con un grupo de 25 mediciones obteniendo su Resumen, debemos añadirlos a nuestro registros de resuenes o mediciones, teniendo en cuenta que debemos añadirle a cada grupo su identificador y fecha. Esta base de datos de será JAXB.

Además, vamos a crear los esquemas de las clases y resultados tratados para facilitar su tratamiento desde el Halcón Milenario.


Finalmente, con el XML de nuestras base de datos de resúmenes y usando XPATH, R2-D2 nos mostrará los datos de NO2 y Ozono por pantalla y en fichero tipo Markdown llamado informe.md pues parece ser que son los causantes de los problemas detectados.

## Autor

Codificado con :sparkling_heart: por [José Luis González Sánchez](https://twitter.com/joseluisgonsan)

[![Twitter](https://img.shields.io/twitter/follow/joseluisgonsan?style=social)](https://twitter.com/joseluisgonsan)
[![GitHub](https://img.shields.io/github/followers/joseluisgs?style=social)](https://github.com/joseluisgs)

### Contacto
<p>
  Cualquier cosa que necesites házmelo saber por si puedo ayudarte 💬.
</p>
<p>
    <a href="https://twitter.com/joseluisgonsan" target="_blank">
        <img src="https://i.imgur.com/U4Uiaef.png" 
    height="30">
    </a> &nbsp;&nbsp;
    <a href="https://github.com/joseluisgs" target="_blank">
        <img src="https://cdn.iconscout.com/icon/free/png-256/github-153-675523.png" 
    height="30">
    </a> &nbsp;&nbsp;
    <a href="https://www.linkedin.com/in/joseluisgonsan" target="_blank">
        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/LinkedIn_logo_initials.png/768px-LinkedIn_logo_initials.png" 
    height="30">
    </a>  &nbsp;&nbsp;
    <a href="https://joseluisgs.github.io/" target="_blank">
        <img src="https://joseluisgs.github.io/favicon.png" 
    height="30">
    </a>
</p>


## Licencia

Este proyecto está licenciado bajo licencia **MIT**, si desea saber más, visite el fichero [LICENSE](./LICENSE) para su uso docente y educativo.
