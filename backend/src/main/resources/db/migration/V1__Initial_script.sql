
CREATE TABLE locations (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at timestamptz NOT NULL ,
    updated_at timestamptz NOT NULL ,

    house VARCHAR(255) NOT NULL ,
    bookcase VARCHAR(255) NOT NULL
) WITH (
    OIDS=FALSE
);

CREATE TABLE books (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at timestamptz NOT NULL ,
    updated_at timestamptz NOT NULL ,

    title VARCHAR(255) NOT NULL ,
    author VARCHAR(255) NOT NULL ,
    total_pages INTEGER CHECK ( total_pages > 0 ) ,
    current_page INTEGER CHECK ( current_page >= 0 ) ,
    status VARCHAR(31) NOT NULL ,
    start_date DATE ,
    finish_date DATE ,
    notes TEXT ,
    location_id uuid ,
    FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE SET NULL
) WITH (
    OIDS=FALSE
);
