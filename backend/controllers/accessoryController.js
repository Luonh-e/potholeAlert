const Accessory = require("../models/Accessory");

exports.createAccessory = async (req, res) => {
  const { name, price, sale, img, stock, dspt, discount } = req.body;

  try {
    const accessory = new Accessory({
      name,
      price,
      sale,
      img,
      stock,
      dspt,
      discount,
    });

    await accessory.save();
    res.status(201).json({ msg: "Accessory created successfully", accessory });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.getAllAccessories = async (req, res) => {
  try {
    const accessories = await Accessory.find();
    res.status(200).json(accessories);
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.getAccessoryById = async (req, res) => {
  const { id } = req.params;

  try {
    const accessory = await Accessory.findById(id);
    if (!accessory) {
      return res.status(404).json({ msg: "Accessory not found" });
    }
    res.status(200).json(accessory);
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.updateAccessory = async (req, res) => {
  const { id } = req.params;
  const { name, price, sale, img, stock, dspt, discount } = req.body;

  try {
    const accessory = await Accessory.findByIdAndUpdate(
      id,
      {
        name,
        price,
        sale,
        img,
        stock,
        dspt,
        discount,
      },
      { new: true }
    );

    if (!accessory) {
      return res.status(404).json({ msg: "Accessory not found" });
    }

    res.status(200).json({ msg: "Accessory updated successfully", accessory });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.deleteAccessory = async (req, res) => {
  const { id } = req.params;

  try {
    const accessory = await Accessory.findByIdAndDelete(id);
    if (!accessory) {
      return res.status(404).json({ msg: "Accessory not found" });
    }
    res.status(200).json({ msg: "Accessory deleted successfully" });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};
