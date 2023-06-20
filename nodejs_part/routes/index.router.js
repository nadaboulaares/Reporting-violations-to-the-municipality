const express = require('express');
const router = express.Router();

const ctrlAdmin = require('../controllers/admin.controller');

const jwtHelper = require('../config/jwtHelper');

router.post('/register', ctrlAdmin.register);
router.post('/authenticate', ctrlAdmin.authenticate);
router.get('/adminProfile',jwtHelper.verifyJwtToken, ctrlAdmin.adminProfile);

module.exports = router;



