const express = require("express");
const {
  createProduct,
  getProductById,
  getProducts,
  getTopProducts,
  updateProduct,
  deleteProduct,
} = require("../controllers/productController");
const router = express.Router();

router.post("/products", createProduct);
router.get("/products", getProducts);
router.get("/top-products", getTopProducts);
router.get("/products/:id", getProductById);
router.put("/products/:id", updateProduct);
router.delete("/products/:id", deleteProduct);

module.exports = router;
