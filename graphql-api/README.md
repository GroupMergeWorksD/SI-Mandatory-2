
### Running application in Docker for development
Commands for building the image and running the container. 

Open a terminal in the folder `graphql-api` and run the following commands:
1. #### Build image: `docker build -t graphql-api-image .` 
2. #### Run container: `docker run --rm --name graphql-api -p 8000:8000 graphql-api-image`
Then access the application     at `http://localhost:8083/graphql` in a browser or a tool like Postman.