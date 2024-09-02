
mvn -v

mvn package

mvn clean install

java -jar target/spring-pv221.jar

```
docker build -t fonbook-java . 
docker images --all
docker run -it --rm -p 5085:8090 --name fonbook_container fonbook-java
docker run -d --restart=always --name fonbook_container -p 5085:8090 fonbook-java
docker run -d --restart=always -v d:/volumes/spring/uploading:/app/uploading --name fonbook_container -p 5085:8090 fonbook-java
docker run -d --restart=always -v /volumes/spring/uploading:/app/uploading --name fonbook_container -p 5085:8090 fonbook-java
docker ps -a
docker stop fonbook_container
docker rm fonbook_container

docker images --all
docker rmi fonbook-java

docker login
docker tag fonbook-java:latest novakvova/fonbook-java:latest
docker push novakvova/fonbook-java:latest

docker pull novakvova/fonbook-java:latest
docker ps -a
docker run -d --restart=always --name fonbook_container -p 5085:8090 novakvova/fonbook-java


docker pull novakvova/fonbook-java:latest
docker images --all
docker ps -a
docker stop fonbook_container
docker rm fonbook_container
docker run -d --restart=always --name fonbook_container -p 5085:8090 novakvova/fonbook-java

---------------/etc/nginx/sites-available/--------------------------

server {
    server_name   slush.itstep.click *.slush.itstep.click;
    location / {
       proxy_pass         http://localhost:5085;
       proxy_http_version 1.1;
       proxy_set_header   Upgrade $http_upgrade;
       proxy_set_header   Connection keep-alive;
       proxy_set_header   Host $host;
       proxy_cache_bypass $http_upgrade;
       proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
       proxy_set_header   X-Forwarded-Proto $scheme;
    }
}

sudo nginx -t
sudo systemctl restart nginx
sudo systemctl status nginx
```