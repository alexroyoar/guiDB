------ ESPAÑOL --------

La siguiente aplicación ha sido desarrollada por Clara Sánchez, María del Mar Rondón y Alejandro Royo (@alexroyoar). 
El objetivo de la misma es prestar un servicio de consulta a una base de datos de artículos científicos de tipo SQL. 

La aplicación, que supone una interfaz gráfica desarrollada en lenguaje Java, permite a usuarios externos hacer consultas 
rápidas (dos de ellas están ya predefinidas) a la base de datos antes mencionada sin tener que utilizar directamente un gestor 
de bases de datos. 

La aplicación está compuesta por tres pestañas que procederán a explicarse a continuación:

1. Pestaña "productividad". Consta, principalmente, de un cuadro para introducir texto en la parte superior y de una tabla para mostrar la salida
de la consulta en la parte inferior. La consulta está ya predefinida, por lo que el usuario solo debe escribir el nombre o apellido del autor que 
le interese, pulsar "enter" y la aplicación le mostrará la salida. 
La consulta consiste en calcular la productividad ponderada de cada autor científico. La consulta devuelve tres columnas: la primera corresponde
a todos los autores cuyo nombre o apellido contiene el carácter o string introducido por el usuario. La segunda, denominada "artículos", contiene 
la cantidad de artículos producidos por cada autor en términos absolutos. Por último, la variable "Factor" contiene el índice de productividad 
correspondiente a cada autor.

2. Pestaña "colaboración". Su funcionamiento y diseño es exactamente igual que la pestaña anterior, con la diferencia de que esta está diseñada 
para mostrar las colaboraciones del autor introducido por el usuario en el cuadro de texto en lugar del índice de productividad. Una vez que el 
usuario ha introducido un autor y pulsa la tecla de retorno, obtendrá en la tabla de la GUI una respuesta en forma de dos columnas: En primer 
lugar, con el campo "Autor" se mostrarán todos los autores que coincidan con el autor que se busca. En segundo lugar, la columna "artículos
publicados" devolverá el nombre del artículo concreto que es fruto de una colaboración. Habrá una instancia para cada artículo donde el autor 
haya colaborado, de tal manera que si un autor ha publicado muchos artículos junto con otros coautores, la aplicación devolverá una instancia 
mostrando cada uno de ellos una línea diferente. 

3. Pestaña "General". Es una pestaña un poco diferente a las otras dos. Contiene un cuadro para introducir texto más grande que los demás, y 
debajo se encuentra la tabla que devolverá la consulta. Esta pestaña permite al usuario introducir cualquier consulta con código SQL, y la 
aplicación devolverá el resultado inmediatamente. Esta pestaña puede ser muy útil, sobre todo en el caso en el que el usuario quiera realizar 
una consulta que no sea de las que están predefinidas. Por ejemplo, se puede introducir:
 
-------
SELECT * FROM authors
WHERE name LIKE '%Gray%';
-------

Y la aplicación devuelve:

-------
Ref_author | name
   
49	   | ASJ. Gray
152	   | DTJ. Gray
252	   | J. Gray
253	   | J. Gray-Consultant
285	   | JM. Gray
399	   | PE. Jim. Gray
447	   | RJ. Gray
-------

--------- ENGLISH ----------

The following application has been developed by Clara Sánchez, María del Mar Rondón, and Alejandro Royo (@alexroyoar). 
Its objective is to provide a query service to a SQL-type database of scientific 
articles.

The application, which represents a graphical interface developed in the Java language, allows external users to make quick 
queries (two of them are already predefined) to the aforementioned database 
without having to directly use a database management system.

The application is composed of three tabs, which will be explained below:

1. "Productivity" Tab: It mainly consists of a text input box at the top and a table to display the query output at the bottom. The query 
is already predefined, so the user only needs to enter the name or last name of the author of interest, press "enter," and the application 
will show the output. The query calculates the weighted productivity of each scientific author. The query returns three columns: the first corresponds 
to all authors whose name or last name contains the character or string entered by the user. The second, named "articles," contains the number of articles produced by each 
author in absolute terms. Finally, the "Factor" variable contains the productivity index corresponding to each author.

2. "Collaboration" Tab: Its operation and design are exactly the same as the previous tab, with the difference that this one is designed to display the 
collaborations of the author entered by the user in the text box instead of the productivity index. Once the user has entered an author and presses the return key, 
they will get a response in the GUI table in the form of two columns: Firstly, with the "Author" field, all authors matching the searched author will be displayed. 
Secondly, the "published articles" column will return the name of the specific article resulting from a collaboration. There will be an instance for each article where 
the author has collaborated, so if an author has published many articles with other co-authors, the application will return an instance showing each of them on 
a different line.

3. "General" Tab: It is a bit different from the other two tabs. It contains a larger text input box, and below it is the table that will return the query. 
This tab allows the user to enter any SQL code query, and the application will immediately return the result. This tab can be very useful, especially in cases 
where the user wants to perform a query that is not predefined. For example, they can 
enter: 

-------
SELECT * FROM authors
WHERE name LIKE '%Gray%';
-------

And the GUI returns:

-------
Ref_author | name
   
49	   | ASJ. Gray
152	   | DTJ. Gray
252	   | J. Gray
253	   | J. Gray-Consultant
285	   | JM. Gray
399	   | PE. Jim. Gray
447	   | RJ. Gray
-------
