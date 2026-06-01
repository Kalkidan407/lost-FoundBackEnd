-- BEGIN;


-- ALTER TABLE location ADD COLUMN longitude_temp DOUBLE PRECISION;
-- UPDATE location SET longitude_temp = CAST(COALESCE(longitude, '0') AS DOUBLE PRECISION);
-- ALTER TABLE location DROP COLUMN longitude;
-- ALTER TABLE location RENAME COLUMN longitude_temp TO longitude;
-- ALTER TABLE location ALTER COLUMN longitude SET NOT NULL;
-- ALTER TABLE location ALTER COLUMN longitude SET DEFAULT 0;


-- ALTER TABLE location ADD COLUMN latitude_temp DOUBLE PRECISION;
-- UPDATE location SET latitude_temp = CAST(CAST(COALESCE(latitude, '0') AS DOUBLE PRECISION) AS DOUBLE PRECISION);
-- ALTER TABLE location DROP COLUMN latitude;
-- ALTER TABLE location RENAME COLUMN latitude_temp TO latitude;
-- ALTER TABLE location ALTER COLUMN latitude SET NOT NULL;
-- ALTER TABLE location ALTER COLUMN latitude SET DEFAULT 0;

-- COMMIT;