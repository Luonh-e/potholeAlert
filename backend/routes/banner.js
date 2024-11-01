const express = require("express");
const router = express.Router();
const {
  createBanner,
  getBanners,
  updateBanner,
  deleteBanner,
} = require("../controllers/bannerController");

router.post("/", createBanner);
router.get("/", getBanners);
router.patch("/:id", updateBanner);
router.delete("/:id", deleteBanner);

module.exports = router;
