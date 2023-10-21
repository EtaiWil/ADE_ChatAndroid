const User = require('../models/User').User;
const UserPassName = require('../models/User').UserPassName;
const UserPass = require('../models/User').UserPass;

const createUser = async (username, password, displayName, profilePic) => {
    try{
        const existingUser = await User.findOne({ username });
        if (existingUser) {
            return { error: 'User already exists' };
        }
    }
    catch {
        return "not";
    }
    const newUser = new User({ username, displayName, profilePic });
    const newUserPassSchema = new UserPass({ username, password });
    const newUserPassName = new UserPassName({ username, password, displayName, profilePic });

    try {
        const savedUser = await newUser.save();
        await newUserPassSchema.save();
        await newUserPassName.save();
        const saved_user_json = {
            "_id" : savedUser._id.toString(),
            "username" : savedUser.username,
            "displayName" : savedUser.displayName,
            "profilePic" : savedUser.profilePic

        }
        return saved_user_json;
    } catch (error) {
        return "not"
    }
};
const getUser = async (username) => {
    try {
        //  console.log('in getUser function');
        const user = await User.findOne({ username: username });

        //console.log('the user is');
        //  console.log(user);

        const to_return = {
            "username": user.username,
            "displayName" : user.displayName,
            "profilePic" : user.profilePic
        }

        return to_return;
    } catch (error) {

        return "not"
    }
};

// onst createUser = async (username, password, displayName, profilePic) => {
//     const existingUser = await User.findOne({ username });
//     if (existingUser) {
//         return { error: 'User already exists' };
//     }
// 
//     const newUser = new User({ username, displayName, profilePic });
//     const newUserPassSchema= new UserPass({username,password});
//     const newUserPassName=new UserPassName({ username, password, displayName, profilePic })
// 
//     try {
//         const savedUser = await newUser.save();
//         await newUserPassSchema.save();
//         await newUserPassName.save();
//         console.log('User created:', savedUser);
//         return savedUser;
//     } catch (error) {
//         console.error('Error creating user:', error);
//         return { error: 'Failed to create user' };
//     }
// };

module.exports = {
    createUser: createUser,
    getUser: getUser
};
