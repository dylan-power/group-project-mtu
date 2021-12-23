// Import the functions you need from the SDKs you need
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.1.2/firebase-app.js";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries
import { getDatabase, ref, set, child, update, remove, get } from "https://www.gstatic.com/firebasejs/9.1.2/firebase-database.js";
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

