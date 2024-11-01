# Basic E-commerce Backend

This backend provides API functionality for an e-commerce platform, covering essential features such as order management, cart handling, user authentication, and product retrieval.

## Tech Stack

- **Node.js** with **Express** for the server and routing
- **MongoDB** for data storage and management

## Features

1. **Authentication**

   - User registration
   - Login with token-based authentication
   - Password recovery
   - Remember me functionality

2. **Product Management**

   - Retrieve all products and product details

3. **Cart Management**

   - Add items to the cart
   - Update item quantity (increment/decrement)
   - Remove items from the cart when quantity reaches zero
   - Delete cart if empty

4. **Order Management**
   - Create new orders
   - View user orders
   - Update order status
   - Automatically adjust stock and total price based on items ordered
