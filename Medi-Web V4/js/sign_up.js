// Import the functions you need from the SDKs you need
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.1.2/firebase-app.js";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries
import { getDatabase, ref, set, child, update, remove } from "https://www.gstatic.com/firebasejs/9.1.2/firebase-database.js";
import { getAuth, createUserWithEmailAndPassword, signInWithEmailAndPassword, signOut } from "https://www.gstatic.com/firebasejs/9.1.2/firebase-auth.js";

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyAeC31E-yHqNRIuRBgncPeX4YP3VYxdCGw",
    authDomain: "basic-registration.firebaseapp.com",
    databaseURL: "https://basic-registration-default-rtdb.firebaseio.com",
    projectId: "basic-registration",
    storageBucket: "basic-registration.appspot.com",
    messagingSenderId: "586720318243",
    appId: "1:586720318243:web:2a032fbfef0365d91db296"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth();
const db = getDatabase();

var first_name = document.getElementById("fname");
var surname = document.getElementById("sname");
var address = document.getElementById("address");
var email = document.getElementById("email");
var phone = document.getElementById("phoneNum");
var pwd = document.getElementById("pwd");

var registerbtn = document.getElementById("registerbtn");

registerbtn.addEventListener('click', function(){
    createUserWithEmailAndPassword(auth, email.value, pwd.value)
    .then((userCredential) => {
        const user = userCredential.user; 

        set(ref(db, 'Medical Professionals/' + user.uid),{
            firstName: first_name.value,
            surname: surname.value,
            address: address.value,
            email: email.value,
            phone: phone.value
        });
    })
    .catch((error) => {
        alert("There was an error when attempting to register. Please ensure your details are correct and try again.");
        const errorCode = error.code; 
        const errorMessage = error.message;
        console.log(errorCode + errorMessage);
    });
});

var clearfields_btn = document.getElementById("clearfieldsbtn");

clearfields_btn.addEventListener('click', function(){
    document.getElementById("register-form").reset();
});
