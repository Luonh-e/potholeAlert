const express = require("express");
const router = express.Router();
const {
  createOrder,
  getOrders,
  updateOrderStatus,
} = require("../controllers/orderController");
const authMiddleware = require("../middleware/authMiddleware");

router.post("/", authMiddleware, createOrder);

router.get("/", authMiddleware, getOrders);

router.patch("/status", authMiddleware, updateOrderStatus);

module.exports = router;
