const Product = require("../models/Product");

exports.createProduct = async (req, res) => {
  const { name, price, sale, img, stock, dspt, discount } = req.body;

  try {
    const product = new Product({
      name,
      price,
      sale,
      img,
      stock,
      dspt,
      discount,
    });

    await product.save();

    res.status(201).json({
      msg: "Product created successfully",
      productId: product._id,
      product,
    });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.getProducts = async (req, res) => {
  try {
    const products = await Product.find();
    res.status(200).json(products);
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.getTopProducts = async (req, res) => {
  try {
    const topProducts = await Product.find().sort({ salesCount: -1 }).limit(5);

    res.status(200).json(topProducts);
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.getProductById = async (req, res) => {
  const { id } = req.params;

  try {
    const product = await Product.findById(id);
    if (!product) {
      return res.status(404).json({ msg: "Product not found" });
    }
    res.status(200).json(product);
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.updateProduct = async (req, res) => {
  const { id } = req.params;
  const updates = req.body;

  try {
    const product = await Product.findByIdAndUpdate(id, updates, {
      new: true,
      runValidators: true,
    });
    if (!product) {
      return res.status(404).json({ msg: "Product not found" });
    }
    res.status(200).json({ msg: "Product updated successfully", product });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.deleteProduct = async (req, res) => {
  const { id } = req.params;

  try {
    const product = await Product.findByIdAndDelete(id);
    if (!product) {
      return res.status(404).json({ msg: "Product not found" });
    }
    res.status(200).json({ msg: "Product deleted successfully" });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};
