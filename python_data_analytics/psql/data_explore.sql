-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT 
  * 
FROM retail limit 10;

-- Check # of records
SELECT
  count(*)
FROM retail;

-- number of clients (e.g. unique client ID)
SELECT 
  count(DISTINCT customer_id)
FROM retail; 

-- invoice date range (e.g. max/min dates)
SELECT
  max(invoice_date),
  min(invoice_date)
FROM retail;

-- number of SKU/merchants (e.g. unique stock code)
SELECT
  count(DISTINCT stock_code)
FROM retail;

-- calculate average invoice amount excluding invoices with a negative amount (e.g. canceled orders have negative amount)
SELECT
  avg(invoice.total)
FROM
  (
    SELECT 
      sum(quantity*unit_price) AS total
    FROM retail
    GROUP BY invoice_no
  ) AS invoice
WHERE invoice.total > 0;

-- calculate total revenue (e.g. sum of unit_price * quantity)
SELECT
  sum(quantity*unit_price)
FROM retail;

-- calculate total revenue by YYYYMM 
SELECT
  date_part('year', invoice_date)*100 + date_part('month', invoice_date) as YYYYMM,
  sum(quantity*unit_price)
FROM retail
GROUP BY YYYYMM
ORDER BY YYYYMM; 
