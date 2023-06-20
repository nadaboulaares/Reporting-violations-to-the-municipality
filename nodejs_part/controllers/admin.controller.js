const mongoose = require('mongoose');
const passport = require('passport');
const _ = require('lodash');

const Admin = mongoose.model('Admin');

module.exports.register = (req, res, next) => {
    var admin = new Admin();
    admin.Name = req.body.Name;
    admin.email = req.body.email;
    admin.password = req.body.password;
    admin.save((err, doc) => {
        if (!err)
            res.send(doc);
        else {
            if (err.code == 11000)
                res.status(422).send(['Adresse e-mail en double trouvée.']);
            else
                return next(err);
        }

    });
}

module.exports.authenticate = (req, res, next) => {
  
    passport.authenticate('local', (err, admin, info) => {       
   
        if (err) return res.status(400).json(err);
    
        else if (admin) return res.status(200).json({ "token": admin.generateJwt() });
  
        else return res.status(404).json(info);
    })(req, res);
}

module.exports.adminProfile = (req, res, next) =>{
    Admin.findOne({ _id: req._id },
        (err, admin) => {
            if (!admin)
                return res.status(404).json({ status: false, message: ' Admin non trouvé.' });
            else
                return res.status(200).json({ status: true, admin : _.pick(admin,['Name','email']) });
        }
    );
}