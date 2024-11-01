const Order = require("../models/Order");
const Product = require("../models/Product");
const User = require("../models/User");

exports.createOrder = async (req, res) => {
  try {
    const { items } = req.body;
    const userId = req.user.id;

    if (!Array.isArray(items) || items.length === 0) {
      return res
        .status(400)
        .json({ message: "Items must be an array and cannot be empty." });
    }

    const orderItems = [];
    let totalPrice = 0;

    for (const item of items) {
      const product = await Product.findById(item.product);
      if (!product) {
        return res
          .status(404)
          .json({ message: `Product ${item.product} not found.` });
      }

      if (product.stock < item.quantity) {
        return res
          .status(400)
          .json({ message: `Insufficient stock for ${product.name}.` });
      }

      product.stock -= item.quantity;
      product.salesCount += item.quantity;
      await product.save();

      const itemTotalPrice = product.price * item.quantity;
      totalPrice += itemTotalPrice;

      orderItems.push({
        product: item.product,
        quantity: item.quantity,
      });
    }

    const order = new Order({
      user: userId,
      items: orderItems,
      totalPrice,
    });

    await order.save();

    res.status(201).json(order);
  } catch (error) {
    console.error("Error creating order:", error);
    res.status(500).json({ message: "Failed to create order." });
  }
};

exports.getOrders = async (req, res) => {
  try {
    const orders = await Order.find({ user: req.user.id }).populate(
      "items.product"
    );
    res.status(200).json(orders);
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.updateOrderStatus = async (req, res) => {
  const { orderId, status } = req.body;

  try {
    const order = await Order.findById(orderId);
    if (!order) {
      return res.status(400).json({ msg: "Order not found" });
    }

    order.status = status;
    await order.save();
    res.status(200).json({ msg: "Order status updated successfully", order });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};
