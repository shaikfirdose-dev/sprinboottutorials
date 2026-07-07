/*
console.log("Basic Calculator");
const a = 100;
const b = 15;

let operator = "^";

if(operator=="+"){
    console.log(a+b);
}
else if(operator=="-"){
    console.log(a-b);
}
else if(operator=="*"){
    console.log(a*b);
}
else if(operator=="/"){
    console.log(a/b);
}
else{
    console.log("Not a Valid operator");
}*/

/*
//average grade marks
let marks = [80,86,82,75,90]

let totalSum = 0;

let length = marks.length;

for(let i=0;i<length;i++){
    totalSum = totalSum+marks[i];
}

console.log(totalSum);
console.log(length);
console.log(totalSum/length);*/
/*
let marks = 8;

switch(true){
    case marks>=80:
        console.log("A Grade");
        break;
    case marks>=70:
        console.log("B Grade");
        break;
    case marks>=60:
        console.log("C Grade");
        break;
    case marks>=50:
        console.log("D Grade");
        break;
    default:
        console.log("F grade");            
    
}
*/

// function greet() {
//     console.log("Hello Javascript");
// }

// greet();

/*
//arrow function
let multiply = function(a,b) {
    return a*b;
}

let res = multiply(2,3);
console.log(res);

let add = (a,b) => a+b;

let res1 = add(2,4);
console.log(res1);

let sub = (a,b) => {
    let ans = a-b;
    return ans;
}

let res2 = sub(6,3);
console.log(res2);


function sum(x, y=1){
    console.log(x+y);
}

sum(10);

console.log("First Citizen of Class")

let greet = name => `Hello,${name}`;

let logGreeting = (greetingFunction, name) => {
    console.log(greetingFunction(name));
};

logGreeting(greet, "Alice");


let applyOperation = (a,b,operation) => operation(a,b);

let res3 = applyOperation(2,3,add);

console.log(res3);*/


//Error Handling
// const sum = 2+3;
// throw new Error("Syntax Error");
// console.log(sum);
/*
try{
    // console.log(a);
    b  // reference error
}catch(ex){
    console.log(ex);
}*/

/*
try{
    let x = "hello";
    let b = x*3;
    throw new Error("Something went wrong");
}catch(ex){
    console.log(ex);
}finally{
    console.log("Always executed");
}*/
/*
const person = {
    age:25,
    firstName:"Shaik",
    address: {
        city:"Rayachotu",
        zip: 516269
    },
    isActive: true,
    books: ["Book 1", "Book 2"],
    walk: function() {
        console.log("Person is walking",this.firstName);
    }
};


console.log(person);
console.log(person.address);
console.log(person.address.zip);
console.log(person["address"].city);  //bracket notation
person.walk();
*/
/*
import {greet, PI as PI_VALUE} from "./greet.js";
import addFn from "./greet.js";   // default items import syntax

greet();
console.log(PI_VALUE);
console.log(addFn(2,3));*/

//Home work
/*
import {add} from "./greet.js";
const person = {
    name:"Firdose",
    age:25,
    greet: () => console.log("Greetings")
}

person.greet();

let res = add(5,10);
console.log(res);

//spread operator --> (...) 3 dots
let arr1 = [1,2,3];
let arr2 = [4,5,6];

let ans = [...arr1,...arr2];
console.log(ans);   
*/

/*
const sum = (a,b,...c) => {
    let res = a;
    res += b;
    console.log(c);

    for(let i=0;i<c.length;i++) res += c[i];
    console.log(res);

    
}

sum(1,2,3,4)*/
/*
const person = {
    name:"Firdose",
    age:25
}

const address = {
    city:"Rayachotu",
    zip:516269
}


person.address = address;

console.log(person);

const newPerson = {...person, ...address};
console.log(newPerson);


let arr1 = [1,2,3];
let arr2 = [4,5,6];
arr1.push(...arr2);
console.log(arr1);

//object destructuring


const {name, age, ...allother} = person;
console.log(name);
console.log(age);

console.log(allother);

const {x,y,...rest} = arr1;
console.log(x,y);
console.log(rest);
*/

/*
//template literals

const person = {
    name:"Firdose",
    age:25,
}

const sentence = `My name is ${person.name} 
and I am ${person.age} years old.`;
console.log(sentence);

*/
//arrays

/*
const arr = [6,8,9,2,3,2];

console.log(arr);

arr.push(10);
console.log(arr);
arr.pop();
console.log(arr);

arr.forEach((value,index) => {
    console.log(`Index: ${index}, Value: ${value}`);
});

arr.sort((a,b) => b-a);
console.log(arr);

arr.includes(2)? console.log("2 is present") : console.log("2 is not present");


let i = arr.indexOf(3);
console.log(i);

arr.splice(2,2);
console.log(arr);*/

/*

const arr = [6,8,9,2,3,2];
const b = arr.filter((value) => value%2!=1)
.map((value) => 2*value);
console.log(b);
console.log(arr);

const a = b.map((value) =>{
    return {
        name: "Shaik", value,
        age: value
    }
});

console.log(a);


const c = a.find((value) => value.age>=4);
console.log(c);*/

/*
console.log("Starting...");

const id = setTimeout(() => {
    console.log("Inside setTimeout");
}, 2000);


console.log(id);

// clearTimeout(id);

console.log("Ending...");

let i=0;
function showTime(){
    const time = new Date();
    console.log(time.toLocaleTimeString());
    i++;

    if(i==5){
        clearInterval(id1);
    }
}

const id1 = setInterval(showTime, 2000);
*/

/*
console.log("Calling the API...");

const response = fetch("https://jsonplaceholder.typicode.com/posts");

console.log(response);

const res = response.then((data) => {
    console.log("Got the data ",data);

    const result = data.json();
    result.then((users) => {
        // console.log("Users Information",users);
        
        for(let i=0;i<users.length;i++){
            console.log(`${users[i].id} : ${users[i].title}`);
        }
    }).catch((error) =>{
        console.log("Error occurred ",error);
    });

}).catch((err) => {
    console.log("Error occurred ",err);
})*/

//promise creation
/*
const promise = new Promise((resolve, reject) => {
    const allGood = true;
    setTimeout(() => {
        if(allGood){
            resolve("Everything is good");
        }
        else{
            reject("Something went wrong");
        }
    }, 5000);    
});

promise.then((res) => {
    console.log(res);
}).catch((err) => {
    console.log(err);
});
*/

/*
//promise chaining
const res = fetch("https://jsonplaceholder.typicode.com/posts")
.then((data) => {
    console.log("Got the data ",data);
    return data.json();
}).then((users) => {
    for(let i of users){
        console.log(`${i.id} : ${i.title}`);
    }
}).catch((error) => {
    console.log("Error occurred ",error);
}).finally(() =>{
    console.log("API call completed");
})*/

//initialte booking
//add guest
//payment processing

function initiateBooking(name){
    return new Promise((resolve, reject) =>{
        setTimeout(() => {
            console.log("Booking Initialted");
            resolve({
                bookingId:"adsarqwe55wrw",
                name
            })
        }, 2000)
    });
}


function addGuest(booking, guest) {
    return new Promise((resolve, reject) => {
        setTimeout(() =>{
            console.log("Guest Added");
            booking["guest"] = guest;
            resolve(booking);
        },2000)
    });
}


function processPayment(booking, payment){
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            console.log("Payment Processed");
            booking["payment"] = payment;
            resolve(booking);
        }, 2000)
    });
}

/*
initiateBooking("Shaik")
.then((booking) => {
    console.log("Booking Details: ",booking);
    return addGuest(booking, {
        name:"Shaik",
        age: 25,
        gender: "Male"
    });
}).then((booking) => {
    console.log("Booking Details: ",booking);
    return processPayment(booking, {
        paymentId:"asdasd123",
        paymentMethod:"Credit Card",
        amount: 1000
    })
}).then((booking) => {
    console.log("Got the booking ",booking);
}).catch((error) => {
    console.log("Error occurred ",error);
}).finally(() => {
    console.log("Booking Process Completed");
});*/


async function bookingFlow(){
    try{
        let booking = await initiateBooking("Shaik");
    booking = await addGuest(booking, {
        name:"Shaik",
        age: 25,
        gender: "Male"
    });

    booking = await processPayment(booking, {
        paymentId:"asdasd123",
        paymentMethod:"Credit Card",
        amount: 1000
    });
console.log("Booking Details: ",booking); 
    }catch(error){
        console.log("Error occurred ",error);
    }  
}    

bookingFlow();