const mongoose = require("mongoose");

const AccessorySchema = new mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
    },
    price: {
      type: Number,
      required: true,
    },
    sale: {
      type: Number,
      default: 0,
    },
    img: {
      type: String,
      required: true,
    },
    stock: {
      type: Number,
      default: 0,
    },
    dspt: {
      type: String,
      required: true,
    },
    discount: {
      type: Number,
      default: 0,
    },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("Accessory", AccessorySchema);
