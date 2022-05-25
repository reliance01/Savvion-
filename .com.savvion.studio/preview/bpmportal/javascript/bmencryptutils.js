//Namespace Definition starts
var Bm;
if (Bm == null || typeof(Bm) != "object") {
    Bm = new Object();
}
if (Bm.BmEncryptUtils == null || typeof(Bm.BmEncryptUtils) != "object") {
    Bm.BmEncryptUtils = new Object();
}

Bm.BmEncryptUtils = {
    encrypt : function(userId,password,separator) {
        return encrypt(userId,password,separator);
    }
};
//Namespace Definition ends

//Messages -- To be taken from appropriate msgs.js file for corresponding locale
var _exp_invalid_input="Invalid Input Parameters";
var _exp_invalid_userId="UserID cannot be Null/Empty";
var _exp_invalid_password="Password cannot be Null/Empty";
var _exp_invalid_separator="Invalid Separator";

/**
 * Function to encrypt a String.
 * @param userId  Login/UserId String. Must not be a null/Empty String
 * @param password  Password String. Must not be a null/Empty String
 * @param separator Must be a non-numeric value. Preferably a single character "-"
 * @throws Error If the input Parameters are invalid
 */
function encrypt(userId, password, separator) {
    try{
        validateInputValues(userId,password,separator);
        var output = "";
        var encodedChar=0;
        var hashed_key=getHash(userId.toLowerCase());
        for (var idx = 0; idx < password.length; idx++) {
            encodedChar=password.charCodeAt(idx) + idx + hashed_key;
            output = output + encodedChar + separator;
        }
        if (typeof(output) != 'undefined' && null != output && output.length > 0) {
            output = output.slice(0, output.length - 1);
        }else{
            output = password;
        }
        return output;
    }catch(th){
        var exceptionStr="Failed in encrypt(u,p,s) for the userid <" + userId + ">, passwd <" + password +
                         ">,  and separator <" + separator + ">."
                         + th;
        throw exceptionStr;
    }
}

function validateInputValues(userId,password,separator) {
    if(userId.length < 1) {
        throw _exp_invalid_userId;
    }
    if(password.length < 1){
        throw _exp_invalid_password;
    }
    if(null==separator|| separator.length<1){
        throw _exp_invalid_separator;
    }
    //Check for Non-Numeric Separator
    var reg=new RegExp("[0-9]");
    if(reg.test(separator)){
        throw  _exp_invalid_separator;
    }
}



function getHash(key) {
    var hash = 0;
    for (var idx = 0; idx < key.length; idx++) {
        hash = hash + (key.charCodeAt(idx));
    }
    return hash;
}