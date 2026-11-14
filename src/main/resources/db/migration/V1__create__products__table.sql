
CREATE TABLE products (
    id UUID PRIMARY KEY,

    name VARCHAR(150) NOT NULL,

    description TEXT,

    sku VARCHAR(64) NOT NULL UNIQUE,

    price NUMERIC(12, 2) NOT NULL
        CONSTRAINT chk_products_price_non_negative
        CHECK (price >= 0),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
