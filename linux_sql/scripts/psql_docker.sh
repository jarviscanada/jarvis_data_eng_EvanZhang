#! /bin/sh

#Commands: start|stop|create
cmd=$1

#Ensures docker daemon is running
sudo systemctl status docker > /dev/null || systemctl start docker 

case $cmd in 
  create) #Creates a local container from the remote PostgreSQL library image through TCP port connection 5432
	#Checks if container already exists; takes no action if so 
	docker container inspect jrvs-psql &> /dev/null
	if [ $? -eq 0 ]; then
		echo 'Container already exists'
		exit 1	
	fi
        if [ $# -ne 3 ]; then
                echo 'Create requires username and password'
                exit 1
        fi

	db_username=$2
	db_password=$3
	docker volume create pgdata
	docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
	exit $?
	;;

  start|stop)
	#Test if jrvs-psql has been created successfuly 
	docker container inspect jrvs-psql &> /dev/null
        if [ $? -ne 0 ]; then
                echo 'Container does not exist'
                exit 1
        fi

	docker container $cmd jrvs-psql
	exit $?
	;;	
  
  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac
