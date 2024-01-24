# How to start the project?

In order to launch the application, you need to have [Docker](https://www.docker.com/) installed on your machine.

Clone this project on your local machine using the following command in your terminal:  
`git clone https://github.com/yeeeip/Task-Management-System.git`

Now, enter the project directory and type in your cmd:  
`
docker compose up -d
`

This command will start up the containers specified in project's *compose.yml* file in **detached mode**.  
If you want to see containers output in the console, remove **-d** from the command above.

Swagger's index.html should be seen on [this url](http://localhost:8080/swagger-ui/index.html).  If you see it, my congratulations, cause you successfully launched the application with Docker.
