<h1>Simple microservices web shop</h1>

<h2>Running:</h2>
<ol>
<li>From /nfo-shop run <i>mvn package</i> command</li>
<li>From /nfo-client run <i>mvn package</i> command</li>
<li>From /nfo-store run <i>mvn package</i> command</li>
<li>For up whole app run /web-shop run <i>docker-compose -f docker-compose.prod.yaml up
</i> command from /web-shop</li>
<li>For up only dbs run /web-shop run <i>docker-compose -f docker-compose.dev.yaml up
</i> command from /web-shop</li>
</ol>
<h2>Access:</h2>localhost:8080<br/>
<h2>Auth:</h2>Put "username: customer1" in request header<br/>
<h2>Swagger:</h2> <br>http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/</br>
<br>Do not forget about Auth!</br>
