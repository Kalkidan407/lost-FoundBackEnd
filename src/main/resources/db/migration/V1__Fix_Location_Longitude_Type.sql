

BEGIN TRANSACTION;

-- Add temporary column with correct type
ALTER TABLE location
ADD COLUMN longitude_temp DOUBLE PRECISION;

-- Copy and cast existing data
UPDATE location
SET longitude_temp = CAST(COALESCE(longitude, '0') AS DOUBLE PRECISION)
WHERE longitude IS NOT NULL OR longitude IS NULL;

-- Drop old column
ALTER TABLE location
DROP COLUMN longitude;

-- Rename temporary column
ALTER TABLE location
RENAME COLUMN longitude_temp TO longitude;

-- Add NOT NULL constraint if needed
ALTER TABLE location
ALTER COLUMN longitude SET DEFAULT 0;

COMMIT TRANSACTION;
