const express = require("express");
const router = express.Router();
const {
  addToCart,
  getCart,
  updateCartItem,
  deleteCart,
} = require("../controllers/cartController");
const authMiddleware = require("../middleware/authMiddleware");

router.use(authMiddleware);

router.post("/", addToCart);
router.get("/", getCart);
router.patch("/", updateCartItem);
router.delete("/", deleteCart);

module.exports = router;
