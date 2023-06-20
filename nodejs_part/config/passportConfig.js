const passport = require('passport');
const localStrategy = require('passport-local').Strategy;
const mongoose = require('mongoose');

var Admin = mongoose.model('Admin');

passport.use(
    new localStrategy({ usernameField: 'email' },
        (username, password, done) => {
            Admin.findOne({ email: username },
                (err, admin) => {
                    if (err)
                        return done(err);
                   
                    else if (!admin)
                        return done(null, false, { message: 'Email non pas enregistré' });
                   
                    else if (!admin.verifyPassword(password))
                        return done(null, false, { message: 'Mauvais mot de passe.' });
                 
                    else
                        return done(null, admin);
                });
        })
);