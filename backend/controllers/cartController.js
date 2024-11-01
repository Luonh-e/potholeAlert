const Cart = require("../models/Cart");
const Product = require("../models/Product");

const calculateTotalPrice = async (items) => {
  let total = 0;

  for (const item of items) {
    const product = await Product.findById(item.product);
    total += product.price * item.quantity;
  }

  return total;
};

exports.addToCart = async (req, res) => {
  const { productId, quantity } = req.body;
  const userId = req.user.id;

  try {
    let cart = await Cart.findOne({ user: userId });

    if (!cart) {
      cart = new Cart({ user: userId, items: [] });
    }

    const itemIndex = cart.items.findIndex(
      (item) => item.product.toString() === productId
    );

    if (itemIndex > -1) {
      cart.items[itemIndex].quantity += quantity;
    } else {
      cart.items.push({ product: productId, quantity });
    }

    const updatedTotalPrice = await calculateTotalPrice(cart.items);
    cart.totalPrice = updatedTotalPrice;

    await cart.save();
    res.status(200).json(cart);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Failed to add to cart." });
  }
};

exports.getCart = async (req, res) => {
  try {
    const cart = await Cart.findOne({ user: req.user.id }).populate(
      "items.product"
    );
    res.status(200).json(cart || { items: [] });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Failed to get cart." });
  }
};

exports.updateCartItem = async (req, res) => {
  const { productId, change } = req.body;
  const userId = req.user.id;

  try {
    const cart = await Cart.findOne({ user: userId });
    if (!cart) {
      return res.status(404).json({ message: "Cart not found." });
    }

    const itemIndex = cart.items.findIndex(
      (item) => item.product.toString() === productId
    );
    if (itemIndex === -1) {
      return res.status(404).json({ message: "Product not found in cart." });
    }

    cart.items[itemIndex].quantity += change;

    if (cart.items[itemIndex].quantity < 1) {
      cart.items.splice(itemIndex, 1);
    }

    if (cart.items.length === 0) {
      await Cart.findByIdAndDelete(cart._id);
      return res
        .status(200)
        .json({ message: "Cart deleted as no items left." });
    }

    await cart.save();
    res.status(200).json(cart);
  } catch (error) {
    console.error("Error updating cart item quantity:", error);
    res.status(500).json({ message: "Failed to update cart item." });
  }
};

exports.deleteCart = async (req, res) => {
  const userId = req.user.id;

  try {
    await Cart.findOneAndDelete({ user: userId });
    res.status(200).json({ message: "Cart deleted." });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: "Failed to delete cart." });
  }
};
