const Banner = require("../models/Banner");

exports.createBanner = async (req, res) => {
  try {
    const { title, imageUrl, link, order } = req.body;

    const banner = new Banner({
      title,
      imageUrl,
      link,
      order,
    });

    await banner.save();
    res.status(201).json(banner);
  } catch (error) {
    res.status(500).json({ message: "Failed to create banner." });
  }
};

exports.getBanners = async (req, res) => {
  try {
    const banners = await Banner.find().sort({ order: 1 });
    res.status(200).json(banners);
  } catch (error) {
    res.status(500).json({ message: "Failed to fetch banners." });
  }
};

exports.updateBanner = async (req, res) => {
  const { id } = req.params;
  const updates = req.body;

  try {
    const banner = await Banner.findByIdAndUpdate(id, updates, {
      new: true,
      runValidators: true,
    });

    if (!banner) {
      return res.status(404).json({ message: "Banner not found." });
    }

    res.status(200).json(banner);
  } catch (error) {
    res.status(500).json({ message: "Failed to update banner." });
  }
};

exports.deleteBanner = async (req, res) => {
  const { id } = req.params;

  try {
    const banner = await Banner.findByIdAndDelete(id);
    if (!banner) {
      return res.status(404).json({ message: "Banner not found." });
    }

    res.status(200).json({ message: "Banner deleted successfully." });
  } catch (error) {
    res.status(500).json({ message: "Failed to delete banner." });
  }
};
