Postgres: sudo docker run --name camunda-postgres -p 5432:5432  -e POSTGRES_PASSWORD=1511 -e POSTGRES_DB=camunda -d postgres
ImgProxy:sudo docker run -p 8090:8080 -it darthsim/imgproxy