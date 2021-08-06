#! /bin/sh

#Setup and validate arguments 
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5
if [ $# -ne 5 ]; then
                echo 'Invalid number of arguments \nRequires psql_host psql_port db_name psql_user psql_password'
                exit 1
fi

#Save CPU architecture information to variable
lscpu_out=`lscpu`

#Retrieve hardware specification variables
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "^Model name:" | cut -f 3- -d ' ' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "^L2 cache:" | awk '{print $3}' | sed 's/[^0-9]//g' | xargs)
total_mem=$(cat /proc/meminfo | egrep "^MemTotal:" | awk '{print $2}' |xargs)

#Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(vmstat -t | awk '{print $18" "$19}'| tail -n1 | xargs)

#PSQL command: Inserts hardware specification values into host_info table
insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp) VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp');"

PGPASSWORD=$psql_password psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

exit $?
