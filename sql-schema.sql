-- Enable UUID generation if needed (PostgreSQL example)
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Table for user reviews
CREATE TABLE user_review (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             user_id UUID,
                             ordered_product_id UUID,
                             rating_value INT,
                             comment TEXT,
                             FOREIGN KEY (user_id) REFERENCES site_user(id),
                             FOREIGN KEY (ordered_product_id) REFERENCES order_line(id)
);

-- Table for user payment methods
CREATE TABLE user_payment_method (
                                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                     user_id UUID,
                                     payment_type_id UUID,
                                     provider VARCHAR(255),
                                     account_number VARCHAR(255),
                                     expiry_date DATE,
                                     is_default BOOLEAN,
                                     FOREIGN KEY (user_id) REFERENCES site_user(id),
                                     FOREIGN KEY (payment_type_id) REFERENCES payment_type(id)
);

-- Table for payment types
CREATE TABLE payment_type (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              value VARCHAR(255)
);

-- Table for shopping carts
CREATE TABLE shopping_cart (
                               id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                               user_id UUID,
                               FOREIGN KEY (user_id) REFERENCES site_user(id)
);

-- Table for shopping cart items
CREATE TABLE shopping_cart_item (
                                    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                    cart_id UUID,
                                    product_item_id UUID,
                                    qty INT,
                                    FOREIGN KEY (cart_id) REFERENCES shopping_cart(id),
                                    FOREIGN KEY (product_item_id) REFERENCES product_item(id)
);

-- Table for site users
CREATE TABLE site_user (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           email_address VARCHAR(255),
                           phone_number VARCHAR(20),
                           password VARCHAR(255)
);

-- Table for user addresses
CREATE TABLE user_address (
                              user_id UUID,
                              address_id UUID,
                              is_default BOOLEAN,
                              PRIMARY KEY (user_id, address_id),
                              FOREIGN KEY (user_id) REFERENCES site_user(id),
                              FOREIGN KEY (address_id) REFERENCES address(id)
);

-- Table for addresses
CREATE TABLE address (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         unit_number VARCHAR(10),
                         street_number VARCHAR(10),
                         address_line1 VARCHAR(255),
                         address_line2 VARCHAR(255),
                         city VARCHAR(100),
                         region VARCHAR(100),
                         postal_code VARCHAR(20),
                         country_id UUID,
                         FOREIGN KEY (country_id) REFERENCES country(id)
);

-- Table for countries
CREATE TABLE country (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         country_name VARCHAR(100)
);

-- Table for order lines
CREATE TABLE order_line (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            product_item_id UUID,
                            order_id UUID,
                            qty INT,
                            price DECIMAL(10, 2),
                            FOREIGN KEY (product_item_id) REFERENCES product_item(id),
                            FOREIGN KEY (order_id) REFERENCES shop_order(id)
);

-- Table for shop orders
CREATE TABLE shop_order (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                            user_id UUID,
                            order_date DATE,
                            payment_method_id UUID,
                            shipping_address TEXT,
                            shipping_method_id UUID,
                            order_total DECIMAL(10, 2),
                            order_status_id UUID,
                            FOREIGN KEY (user_id) REFERENCES site_user(id),
                            FOREIGN KEY (payment_method_id) REFERENCES user_payment_method(id),
                            FOREIGN KEY (shipping_method_id) REFERENCES shipping_method(id),
                            FOREIGN KEY (order_status_id) REFERENCES order_status(id)
);

-- Table for shipping methods
CREATE TABLE shipping_method (
                                 id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                 name VARCHAR(255),
                                 price DECIMAL(10, 2)
);

-- Table for order statuses
CREATE TABLE order_status (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              status VARCHAR(100)
);

-- Table for product items
CREATE TABLE product_item (
                              id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              product_id UUID,
                              SKU VARCHAR(50),
                              qty_in_stock INT,
                              product_image VARCHAR(255),
                              price DECIMAL(10, 2),
                              FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Table for products
CREATE TABLE product (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         category_id UUID,
                         name VARCHAR(255),
                         description TEXT,
                         product_image VARCHAR(255),
                         FOREIGN KEY (category_id) REFERENCES product_category(id)
);

-- Table for product configurations
CREATE TABLE product_configuration (
                                       product_item_id UUID,
                                       variation_option_id UUID,
                                       PRIMARY KEY (product_item_id, variation_option_id),
                                       FOREIGN KEY (product_item_id) REFERENCES product_item(id),
                                       FOREIGN KEY (variation_option_id) REFERENCES variation_option(id)
);

-- Table for variations
CREATE TABLE variation (
                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                           category_id UUID,
                           name VARCHAR(255),
                           FOREIGN KEY (category_id) REFERENCES product_category(id)
);

-- Table for variation options
CREATE TABLE variation_option (
                                  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                  variation_id UUID,
                                  value VARCHAR(255),
                                  FOREIGN KEY (variation_id) REFERENCES variation(id)
);

-- Table for product categories
CREATE TABLE product_category (
                                  id UUID PRIMARY KEY DEFAULT uuid_generate_v4
