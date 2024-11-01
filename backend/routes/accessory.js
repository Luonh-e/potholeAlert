const express = require("express");
const {
  createAccessory,
  getAllAccessories,
  getAccessoryById,
  updateAccessory,
  deleteAccessory,
} = require("../controllers/accessoryController");

const router = express.Router();

router.post("/", createAccessory);
router.get("/", getAllAccessories);
router.get("/:id", getAccessoryById);
router.put("/:id", updateAccessory);
router.delete("/:id", deleteAccessory);

module.exports = router;
