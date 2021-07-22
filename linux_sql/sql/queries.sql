--Groups hosts by hardware info
SELECT cpu_number, 
	id, 
	total_mem 
FROM host_info 
ORDER BY cpu_number, 
	total_mem DESC;

--Helper function to round timestamps into 5 minute intervals 
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

--Average memory usage percentage over 5 minute intervals
--Calculated from the ratio of free versus total memory
--Ordered by ascending timestamps, and host id  
SELECT i.id,
       i.hostname,
       round5(u.timestamp) AS ts,
       AVG((i.total_mem::float - (u.memory_free * 1000)) / i.total_mem * 100) AS avg_used_mem_percentage
FROM host_usage AS u
JOIN host_info AS i ON u.host_id = i.id
GROUP BY ts,
         i.id,
         i.hostname
ORDER BY ts,
         i.id;

--Detects host failures
--Filters for non-updating host_id in the host_usage table
--If less than 3 table entries by the host in the 5 minute interval 
SELECT host_id,
       round5(timestamp) AS ts,
       COUNT(*) AS num_data_points
FROM host_usage
GROUP BY ts,
         host_id
HAVING COUNT(*) < 3
ORDER BY ts,
         host_id;
