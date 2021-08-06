--switch to 'host_agent' database
\c host_agent

--Table of host machine specifications
--One fixed entry per new machine/node
CREATE TABLE IF NOT EXISTS PUBLIC.host_info  
  ( 
     id               SERIAL PRIMARY KEY, 
     hostname         VARCHAR NOT NULL, 
     cpu_number       INT NOT NULL,
     cpu_architecture VARCHAR NOT NULL,
     cpu_model        VARCHAR NOT NULL,
     cpu_mhz          NUMERIC NOT NULL,
     L2_cache         INT NOT NULL,
     total_mem        INT NOT NULL,
     timestamp        TIMESTAMP NOT NULL  
  );

--Table of host machines' resource usage
--Grouped by host machine id, ordered by collecion time timestamp 
CREATE TABLE IF NOT EXISTS PUBLIC.host_usage 
  ( 
     timestamp        TIMESTAMP NOT NULL PRIMARY KEY, 
     host_id          INT NOT NULL REFERENCES host_info(id), 
     memory_free      INT NOT NULL,	
     cpu_idle         INT NOT NULL,
     cpu_kernel       INT NOT NULL,
     disk_io          INT NOT NULL,
     disk_available   INT NOT NULL
  ); 
