const crypto = require("crypto");
const User = require("../models/User");
const sendEmail = require("../utils/sendEmail");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");

const generateAccessToken = (id) => {
  return jwt.sign({ id }, process.env.JWT_SECRET, { expiresIn: "7h" });
};

const generateRefreshToken = (id) => {
  return jwt.sign({ id }, process.env.JWT_REFRESH_SECRET, { expiresIn: "30d" });
};

exports.register = async (req, res) => {
  const { name, email, password, phone, dateOfBirth } = req.body;

  try {
    let user = await User.findOne({ email });
    if (user) {
      return res.status(400).json({ msg: "User already exists" });
    }

    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);

    let username =
      name.split(" ").join("").toLowerCase() + Math.floor(Math.random() * 1000);

    let isUsernameUnique = false;
    let attemptCount = 0;

    while (!isUsernameUnique && attemptCount < 10) {
      const existingUser = await User.findOne({ username });
      if (!existingUser) {
        isUsernameUnique = true;
      } else {
        username =
          name.split(" ").join("").toLowerCase() +
          Math.floor(Math.random() * 1000);
      }
      attemptCount++;
    }

    if (!isUsernameUnique) {
      return res.status(400).json({
        msg: "Could not generate a unique username. Please try again.",
      });
    }

    user = new User({
      name,
      email,
      password: hashedPassword,
      username,
      phone,
      dateOfBirth,
    });

    await user.save();

    res.status(201).json({
      msg: "User registered successfully",
      user: {
        id: user._id,
        name: user.name,
        email: user.email,
        username,
        phone: user.phone,
        dateOfBirth: user.dateOfBirth,
      },
    });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.login = async (req, res) => {
  const { email, password, rememberMe } = req.body;

  try {
    const user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({ msg: "Invalid email or password" });
    }

    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      return res.status(400).json({ msg: "Invalid email or password" });
    }

    const accessToken = generateAccessToken(user._id);
    const refreshToken = rememberMe ? generateRefreshToken(user._id) : null;

    if (refreshToken) {
      user.refreshToken = refreshToken;
      user.refreshTokenExpire = rememberMe
        ? Date.now() + 30 * 24 * 60 * 60 * 1000
        : null;
      await user.save();
    }

    res.status(200).json({
      accessToken,
      refreshToken,
      user: {
        id: user._id,
        name: user.name,
        email: user.email,
        phone: user.phone,
        dateOfBirth: user.dateOfBirth,
      },
      msg: "Logged in successfully",
    });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.forgotPassword = async (req, res) => {
  const { email } = req.body;

  try {
    let user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({ msg: "User not found" });
    }

    const otp = Math.floor(100000 + Math.random() * 900000).toString();

    user.resetPasswordToken = crypto
      .createHash("sha256")
      .update(otp)
      .digest("hex");

    user.otpExpire = Date.now() + 60 * 60 * 1000;

    await user.save();

    const message = `We heard that you lost your password. Sorry about that!

But don't worry! You can use the following OTP code to reset your password: \n\n ${otp} \n\n If you don't use this code within 0:59, it will expire. \n\n Thanks`;

    await sendEmail({
      email: user.email,
      subject: "OTP RESET PASSWORD",
      message,
    });

    res.status(200).json({ msg: "OTP sent to your email" });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.verifyOtp = async (req, res) => {
  const { email, otp } = req.body;

  try {
    let user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({ msg: "User not found" });
    }

    const hashedOtp = crypto.createHash("sha256").update(otp).digest("hex");

    if (user.resetPasswordToken !== hashedOtp || user.otpExpire < Date.now()) {
      return res.status(400).json({ msg: "Invalid or expired OTP" });
    }

    res
      .status(200)
      .json({ msg: "OTP verified, you can now reset your password" });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.resetPassword = async (req, res) => {
  const { email, newPassword } = req.body;

  try {
    let user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({ msg: "User not found" });
    }

    if (!user.resetPasswordToken || user.otpExpire < Date.now()) {
      return res.status(400).json({ msg: "Invalid or expired OTP" });
    }

    const salt = await bcrypt.genSalt(10);
    user.password = await bcrypt.hash(newPassword, salt);

    user.resetPasswordToken = undefined;
    user.otpExpire = undefined;

    await user.save();

    res.status(200).json({ msg: "Password reset successfully" });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};

exports.refreshToken = async (req, res) => {
  const { refreshToken } = req.body;

  if (!refreshToken) return res.status(403).json({ msg: "Access Denied" });

  try {
    const decoded = jwt.verify(refreshToken, process.env.JWT_REFRESH_SECRET);
    const accessToken = generateAccessToken(decoded.id);

    res.status(200).json({ accessToken });
  } catch (err) {
    console.error(err);
    res.status(403).json({ msg: "Invalid refresh token" });
  }
};

exports.logout = async (req, res) => {
  const { userId } = req.body;
  try {
    await User.findByIdAndUpdate(userId, { refreshToken: null });
    res.status(200).json({ msg: "Logged out successfully" });
  } catch (err) {
    console.error(err);
    res.status(500).send("Server error");
  }
};
