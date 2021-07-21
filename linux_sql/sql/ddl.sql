CREATE TABLE PUBLIC.host_info 
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

CREATE TABLE PUBLIC.host_usage 
  ( 
     timestamp        TIMESTAMP NOT NULL PRIMARY KEY, 
     host_id          SERIAL REFERENCES host_info(id), 
     memory_free      INT NOT NULL,	
     cpu_idle         INT NOT NULL,
     cpu_kernel       INT NOT NULL,
     disk_io          INT NOT NULL,
     disk_available   INT NOT NULL
  ); 
