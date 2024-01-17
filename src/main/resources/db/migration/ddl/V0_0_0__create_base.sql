CREATE TABLE demo_db.customers_flyway (
                                   customer_number BIGINT NOT NULL PRIMARY KEY auto_increment,
                                   customer_name VARCHAR(50) NULL,
                                   customer_last_name VARCHAR(50) NULL,
                                   customer_first_name VARCHAR(50) NULL,
                                   phone VARCHAR(50) NULL,
                                   address_line1 VARCHAR(50) NULL,
                                   address_line2 VARCHAR(50) NULL,
                                   city VARCHAR(50) NULL,
                                   state VARCHAR(50) NULL,
                                   postal_code VARCHAR(15) NULL,
                                   country VARCHAR(50) NULL,
                                   sales_rep_employee_number BIGINT NOT NULL,
                                   credit_limit DOUBLE NOT NULL
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;