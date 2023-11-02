docker pull mysql
docker run -p 2306:3306 --name spring-batch-mysql -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=batch_database -d mysql